package com.gjht.addresslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //git成功
        //跳转到联系人主页面
        val intent = Intent(this,Home::class.java)
        startActivity(intent)

    }
}