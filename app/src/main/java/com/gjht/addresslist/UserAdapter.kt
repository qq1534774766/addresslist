package com.gjht.addresslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(val userList: List<User>,val name:TextView,val phone : TextView) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
//    定义视图，将数据放入视图展示
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var userImg:ImageView = itemView.findViewById(R.id.userImg)
        var username:TextView = itemView.findViewById(R.id.username)
        var phone:TextView = itemView.findViewById(R.id.phone)
    }
//   子项布局加载到主布局上
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
//        inflate 传入子项布局名，父亲，false
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
//  创建viewHolder
    val viewHolder = ViewHolder(view)
//    给子项添加侦听事件，使得子项回显
    view.setOnClickListener(){
//        获取子项索引
        val position = viewHolder.adapterPosition
//        获取子项信息
        name.setText(userList.get(position).name)
        phone.setText(userList.get(position).phone)

    }

    return viewHolder
    }
//  数据绑定，将实体类的数据注入视图
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        var user:User = userList.get(position)
        holder.userImg.setImageResource(user.url.toInt())
        holder.username.setText(user.name)
        holder.phone.setText(user.phone)

    }
//  返回子项的长度
    override fun getItemCount(): Int = userList.size
}


