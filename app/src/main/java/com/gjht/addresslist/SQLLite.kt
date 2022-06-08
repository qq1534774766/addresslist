package com.gjht.addresslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLLite(val context: Context):
    SQLiteOpenHelper(context, "NoteStore.db", null, 1) {
    //创建数据库SQL语句
    private val createNote = "CREATE TABLE information(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name VARCHAR(20)," +
            " phone VARCHAR(20))"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createNote)//仅在数据库创建时，执行一次，创建数据表
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(db)
    }
    //添加联系人
    fun add(name:String,phone:String){
        //获取数据库
        val db = this.writableDatabase
        //数据组装
        val anote= ContentValues().apply {
            put("name",name)
            put("phone",phone)
        }
        db.insert("note",null,anote)

    }
    //查找联系人
    fun query(context: Context,name: String):ArrayList<User>{
        val userList = ArrayList<User>()
        val db = this.writableDatabase
        val args = arrayOf(name)
        var cursor: Cursor? = null
        cursor = if ("" == name) {
            //如果name内容为空。
            db.query("information", null, null, null, null, null, null)
        } else {
            //name输入框不为空
            db.query("information", null, "name = ?", args, null, null, null)
        }
        while (cursor.moveToNext()){
            val name = cursor.getString(0)
            val phone = cursor.getString(1)
            val url = "aguo"
            userList.add(User(name,phone,url))
        }

        return userList
    }
    //更新联系人
    fun update(context: Context, name: String,phone: String){
        if ("" == name || "" == phone) {
            Toast.makeText(context, "姓名和电话都不可为空", Toast.LENGTH_LONG).show()
            return
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("phone",phone)
        db.update("information", values, "name=?", arrayOf(name))
        Toast.makeText(context, "信息已更新", Toast.LENGTH_LONG).show()
    }
    //删除联系人
    fun delete(context: Context, name: String){
        if ("" == name ) {
            Toast.makeText(context, "姓名不可为空", Toast.LENGTH_LONG).show()
            return
        }
        val db = this.writableDatabase
        db.delete("information","name=?", arrayOf(name))
        Toast.makeText(context, "name已被删除", Toast.LENGTH_LONG).show()
    }
}