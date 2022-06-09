package com.gjht.addresslist

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


open class UserAdapter(val userList: List<User>, val name: TextView?, val phone: TextView,val activity:Activity) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


//    定义视图，将数据放入视图展示
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
//    读取控件的数据
        var userImg:ImageView = itemView.findViewById(R.id.userImg)
        var username:TextView = itemView.findViewById(R.id.username)
        var phone:TextView = itemView.findViewById(R.id.phone)
    }
//   子项布局加载到主布局上
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
//        inflate 传入子项布局名，父亲，false
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//        创建viewHolder
        val viewHolder = ViewHolder(view)
//        给子项添加侦听事件，使得子项回显到输入框，并添加拨打电话的功能。
//        拨打电话的功能必须在AndroidManifest.xml定义权限
        view.setOnClickListener{
//        获取子项索引
        val position = viewHolder.adapterPosition
//        获取子项信息
            name?.setText(userList.get(position).name)
            phone.setText(userList.get(position).phone)
            //不具有权限，则发出申请
            val permission = Manifest.permission.CALL_PHONE
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
//          requestPermissions方法最后一个参数是自定义的请求码，该请求的结果将在Home类的onRequestPermissionsResult展现
                ActivityCompat.requestPermissions(activity,arrayOf(permission), 1)
            } else {
                //已具有权限，则启动拨打电话
               call(phone.text.toString())
            }
    }
    return viewHolder
    }
//  数据绑定，绑定查询得到的结果到视图
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
//   获取当前对象
        val user:User = userList.get(position)
//    将实体类的数据注入视图
        holder.userImg.setImageResource(user.url.toInt())
        holder.username.setText(user.name)
        holder.phone.setText(user.phone)


    }
//  返回子项的长度
    override fun getItemCount(): Int = userList.size

    fun call(phone: String){
        try {
            //拨打电话
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:"+phone)
            activity.startActivity(intent)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

}


