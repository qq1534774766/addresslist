package com.gjht.addresslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //用户的名称与图片与电话号码
//    private val names = arrayOf(
//        "Apple", "Banana", "Orange",
//        "Watermelon", "Pear", "Grape", "Pineapple",
//        "Strawberry", "Cherry", "Mango"
//    )
//    private val phones = arrayOf(
//        "123", "124", "125",
//        "126", "Pear", "Grape", "Pineapple",
//        "Strawberry", "Cherry", "Mango"
//    )
//
//    private val userImageUrls = intArrayOf(
//        R.drawable.user
//    )
//    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //git成功
        //跳转到联系人主页面
        val intent = Intent(this,Home::class.java)
        startActivity(intent)
        // 数据初始化
        //  初始化userList
//        initUser()
        // 适配器初始化
////        val userAdapter = UserAdapter(userList)

//        获取recyclerView容器
//        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
////        装载适配器
//        recyclerView.adapter = userAdapter
// //        线性布局管理器，管理子项布局
//        val linearLayoutManager = LinearLayoutManager(this)
////        将子项布局交付给recyclerView
//        recyclerView.layoutManager = linearLayoutManager

    }
//  初始化userList
//    fun initUser(){
//        for (i in 0..9){
//            userList.add(User(i,names[i],phones[i],userImageUrls[0].toString()))
//        }
//    }
}