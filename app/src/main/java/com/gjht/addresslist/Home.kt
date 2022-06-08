package com.gjht.addresslist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
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
        userList = dbHelper.query(this,User("","",""))

        //绑定添加事件
        mBtnAdd.setOnClickListener{
            dbHelper.add(User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
        }

        //绑定删除事件
        mBtnDelete.setOnClickListener{
            dbHelper.delete(this,mEtName.text.toString())
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
        }

        //绑定更新事件
        mBtnUpdate.setOnClickListener {
            dbHelper.update(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
        }

        //绑定查询事件
        mBtnQuery.setOnClickListener {
            userList = dbHelper.query(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新列表
            userList = dbHelper.query(this,User(mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
        }
    }
    fun init(){
        //设置组件的点击事件


    }


}