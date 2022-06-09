package com.gjht.addresslist

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
        val mTvShow = findViewById<View>(R.id.recyclerView) as RecyclerView
        //按钮
        val mBtnAdd = findViewById<View>(R.id.btn_add) as Button
        val mBtnQuery = findViewById<View>(R.id.btn_query) as Button
        val mBtnUpdate = findViewById<View>(R.id.btn_update) as Button
        val mBtnDelete = findViewById<View>(R.id.btn_delete) as Button
        val mBtnClear = findViewById<View>(R.id.btn_clear) as Button
        //滚动条
//        val scrollView = findViewById<View>(R.id.m_ScrollView) as ScrollView
        //得到数据库对象
        val dbHelper = SQLLite(this)
        userList = dbHelper.query(this,User(0,"","", R.drawable.user.toString()))
        //更新视图
       updateView(userList,mEtName,mEtPhone)

        //绑定添加事件
        mBtnAdd.setOnClickListener{
            val insertNumber = dbHelper.add(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
            //insertNumber<0表示添加了0条数据，表示添加失败
            if (insertNumber<0){
                //结束方法
                return@setOnClickListener
            }
            //清空表单
            mEtName.setText("")
            mEtPhone.setText("")
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))

        //更新视图
           updateView(userList,mEtName,mEtPhone)
        }

        //绑定删除事件
        mBtnDelete.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("你确定要删除"+mEtName.text.toString()+"吗?")
            builder.setPositiveButton("确定",
                DialogInterface.OnClickListener { dialog, which -> // TODO Auto-generated method stub
                    dbHelper.delete(this,mEtName.text.toString())
                    //清空表单
                    mEtName.setText("")
                    mEtPhone.setText("")
                    //更新列表
                    userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
                    //更新视图
                    updateView(userList,mEtName,mEtPhone)
                })

            builder.setNegativeButton("取消",
                DialogInterface.OnClickListener { dialog, which -> // TODO Auto-generated method stub
                    return@OnClickListener
                })
            builder.show()

        }

        //绑定更新事件
        mBtnUpdate.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("你确定要更新"+mEtName.text.toString()+"吗?")
            builder.setPositiveButton(
                "确定"
            ) { dialog, which -> // TODO Auto-generated method stub
                dbHelper.update(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
                //清空表单
                mEtName.setText("")
                mEtPhone.setText("")
                //更新列表
                userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
                //更新视图
                updateView(userList,mEtName,mEtPhone)
            }
            builder.setNegativeButton("取消",
                DialogInterface.OnClickListener { dialog, which -> // TODO Auto-generated method stub
                    return@OnClickListener
                })

            builder.show()

        }

        //绑定查询事件
        mBtnQuery.setOnClickListener {
            //更新列表
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
            //更新视图
            updateView(userList,mEtName,mEtPhone)
        }

        // 清空输入框并显示完整的列表
        mBtnClear.setOnClickListener{
            //如果表单没有任何内容
            if ("" == mEtName.text.toString() && "" == mEtPhone.text.toString()) {
                return@setOnClickListener
            }
//            清空表单
            mEtName.setText("")
            mEtPhone.setText("")
//            全表查询
            userList = dbHelper.query(this,User(0,mEtName.text.toString(),mEtPhone.text.toString(), R.drawable.user.toString()))
            //更新视图
            updateView(userList,mEtName,mEtPhone)
        }

    }
    //更新联系人展示的视图，
    fun updateView(userList: ArrayList<User>,mEtName:TextView,mEtPhone:TextView ){
        // 适配器初始化
        val userAdapter = UserAdapter(userList,mEtName,mEtPhone,this)
//        获取recyclerView容器
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        装载适配器
        recyclerView.adapter = userAdapter
        //        线性布局管理器，管理子项布局
        val linearLayoutManager = LinearLayoutManager(this)
//        将子项布局交付给recyclerView
        recyclerView.layoutManager = linearLayoutManager



    }

//    申请拨打电话的权限
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        when (requestCode) {
            //请求码为“1”的权限申请结果处理
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意授权，则启动拨打电话
                val mEtPhone2 = findViewById<View>(R.id.et_phone) as EditText
                call(mEtPhone2.text.toString())
                }
                else {
                //用户拒绝授权
                Toast.makeText(this, "You denied the permission",Toast.LENGTH_SHORT).show()
            }
        }
    }
    //拨打电话
    fun call(phone: String){
        try {
            val intent = Intent(Intent.ACTION_CALL)
//            设置电话号码
            intent.data = Uri.parse("tel:"+phone)
//            启动拨打电话
            this.startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }



}