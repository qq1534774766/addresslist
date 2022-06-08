package com.gjht.addresslist

import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 联系人的主页面
 */
class Home : AppCompatActivity() {
    //用户的名称与图片与电话号码
    private val names = arrayOf(
        "Apple", "Banana", "Orange",
        "Watermelon", "Pear", "Grape", "Pineapple",
        "Strawberry", "Cherry", "Mango"
    )
    private val phones = arrayOf(
        "123", "124", "125",
        "126", "Pear", "Grape", "Pineapple",
        "Strawberry", "Cherry", "Mango"
    )

    private val userImageUrls = intArrayOf(
        R.drawable.user
    )
    var userList1 = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //临时列表
        var userList =ArrayList<User>()
        //输入框
        val mEtName = findViewById<View>(R.id.et_name) as EditText
        val mEtPhone = findViewById<View>(R.id.et_phone) as EditText
        //展示列表
        val mTvShow = findViewById<View>(R.id.recyclerView) as RecyclerView
        //按钮
        val mBtnAdd = findViewById<View>(R.id.btn_add) as Button
        val mBtnQuery = findViewById<View>(R.id.btn_query) as Button
        val mBtnUpdate = findViewById<View>(R.id.btn_update) as Button
        val mBtnDelete = findViewById<View>(R.id.btn_delete) as Button
        //滚动条
//        val scrollView = findViewById<View>(R.id.m_ScrollView) as ScrollView
        //得到数据库对象
        val dbHelper = SQLLite(this)
//        userList = dbHelper.query(this,User(0,"","",""))
        //更新视图
//        updateView(userList)

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
//            updateView(userList)
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
//            updateView(userList)
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
//            updateView(userList)
        }

        //绑定查询事件
        mBtnQuery.setOnClickListener {
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(),"aguo"))
            //更新视图
//            updateView(userList)
        }

        // 数据初始化
        //  初始化userList
        initUser()
        // 适配器初始化
        val userAdapter = UserAdapter(userList1,mEtName,mEtPhone)

//        获取recyclerView容器
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        装载适配器
        recyclerView.adapter = userAdapter
        //        线性布局管理器，管理子项布局
        val linearLayoutManager = LinearLayoutManager(this)
//        将子项布局交付给recyclerView
        recyclerView.layoutManager = linearLayoutManager

    }
    //更新联系人展示的视图，
//    fun updateView(userList: ArrayList<User> ){
//        //测试代码，可以删除
//
//        //获得展示联系人视图文本域
//        val textView = findViewById<RecyclerView>(R.id.recyclerView)

//        if (userList.size>0){
//            //更新视图展示联系人视图文本域
//            textView.setText("")
//            for (item in userList) {
//                textView.append("\nname:" +item.name+", phone:"+item.phone)
//            }
//        }
        //以上代码可以注释，更换成RecycleView

//    }
    //  初始化userList
    fun initUser(){
        for (i in 0..3){
            userList1.add(User(i,names[i],phones[i],userImageUrls[0].toString()))
        }
    }
//    获取子项回显信息
//    fun reviewForm(name:String,phone: String){
//    //清空表单
//        mEtName.setText("")
//        mEtPhone.setText("")
//
//    }

}