package com.gjht.addresslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/**
 * 这是SQLLite轻量级数据库，属于安卓自带的数据库，与普通数据库操作一样，建表增删改查的操作与Mysql类似的。
 */
class SQLLite(val context: Context):
    SQLiteOpenHelper(context, "NoteStore.db", null, 1) {
    //创建数据库SQL语句
    private val createNote = "CREATE TABLE information(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name VARCHAR(20)," +
            " phone VARCHAR(20))"
    override fun onCreate(db: SQLiteDatabase?) {
        //仅在数据库创建时，执行一次，创建数据表
        db?.execSQL(createNote)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onCreate(db)
    }
    //以下4个方法，都是对数据库联系人的增删改查操作

    //添加联系人
    fun add(context: Context,user: User): Long {
        if ("" == user.name || "" == user.phone) {
            Toast.makeText(context, "信息不可为空", Toast.LENGTH_LONG).show()
            return -1;
        }
        //获取数据库
        val db = this.writableDatabase
        var cursor = db.query("information", null, "name = ?", arrayOf(user.name), null, null, null) as Cursor
        if (cursor.getCount()>0){
            Toast.makeText(context, "联系人已经存在！", Toast.LENGTH_LONG).show()
            return -1
        }
        //数据组装
        val anote= ContentValues().apply {
            put("name",user.name)
            put("phone",user.phone)
        }
        //插入联系人数据
        return db.insert("information",null,anote)

    }
    //查找联系人
    fun query(context: Context,user: User):ArrayList<User>{
        val userList = ArrayList<User>()
        //获取数据库
        val db = this.writableDatabase
        // select * from information where name like %name%
        val args = arrayOf("%"+user.name+"%")
        //结果集存放的地方
        var cursor: Cursor? = null
        cursor = if ("" == user.name) {
            //如果name内容为空，则查全部数据。
            db.query("information", null, null, null, null, null, null)
        } else {
            //name输入框不为空，模糊搜索name
            db.query("information", null, "name like ?", args, null, null, null)
        }
        //对cursor(从数据库得到的结果)进行遍历，逐个添加到ArrayList集合当中
        while (cursor.moveToNext()){
            val id = cursor.getString(0)
            val name = cursor.getString(1)
            val phone = cursor.getString(2)
            val url =  R.drawable.user.toString()
            userList.add(User(id.toInt(),name,phone,url))
        }
        return userList
    }
    //更新联系人
    fun update(context: Context, user: User){
        if ("" == user.name || "" == user.phone) {
            Toast.makeText(context, "姓名和电话都不可为空", Toast.LENGTH_LONG).show()
            return
        }
        //获取数据库
        val db = this.writableDatabase
        //组成参数集合对象
        val values = ContentValues()
        values.put("phone",user.phone)
        //更新指定name的联系人
        db.update("information", values, "name=?", arrayOf(user.name))
        Toast.makeText(context, "联系人电话已更新", Toast.LENGTH_LONG).show()
    }
    //删除联系人
    fun delete(context: Context, name: String){
        if ("" == name ) {
            Toast.makeText(context, "姓名不可为空", Toast.LENGTH_LONG).show()
            return
        }
        val db = this.writableDatabase
        db.delete("information","name=?", arrayOf(name))
        Toast.makeText(context, name+"已被删除", Toast.LENGTH_LONG).show()
    }
}