package com.gjht.addresslist

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * 联系人的主页面
 */
class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //临时列表
        var userList =ArrayList<User>()
        //输入框
        val mEtName = findViewById<View>(R.id.et_name) as EditText
        val mEtPhone = findViewById<View>(R.id.et_phone) as EditText
        //展示列表
        val mTvShow = findViewById<View>(R.id.tv_show) as RecyclerView
        //按钮
        val mBtnAdd = findViewById<View>(R.id.btn_add) as Button
        val mBtnQuery = findViewById<View>(R.id.btn_query) as Button
        val mBtnUpdate = findViewById<View>(R.id.btn_update) as Button
        val mBtnDelete = findViewById<View>(R.id.btn_delete) as Button
        //滚动条
        val scrollView = findViewById<View>(R.id.m_ScrollView) as ScrollView
        //得到数据库对象
        val dbHelper = SQLLite(this)
        userList = dbHelper.query(this,User(0,"","",""))
        //更新视图
        updateView(userList)

        //绑定添加事件
        mBtnAdd.setOnClickListener{
            val insertNumber = dbHelper.add(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //insertNumber<0表示添加了0条数据，表示添加失败
            if (insertNumber<0){
                //结束方法
                return@setOnClickListener
            }
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新视图
            updateView(userList)
        }

        //绑定删除事件
        mBtnDelete.setOnClickListener{
            dbHelper.delete(this,mEtName.text.toString())
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新视图
            updateView(userList)
        }

        //绑定更新事件
        mBtnUpdate.setOnClickListener {
            dbHelper.update(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新视图
            updateView(userList)
        }

        //绑定查询事件
        mBtnQuery.setOnClickListener {
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新视图
            updateView(userList)
        }
    }
    //更新联系人展示的视图，
    fun updateView(userList: ArrayList<User> ){
        //测试代码，可以删除

        //获得展示联系人视图文本域
        val textView = findViewById<TextView>(R.id.textView)

        if (userList.size>0){
            //更新视图展示联系人视图文本域
            textView.setText("")
            for (item in userList) {
                textView.append("\nname:" +item.name+", phone:"+item.phone)
            }
        }
        //以上代码可以注释，更换成RecycleView



    }


}