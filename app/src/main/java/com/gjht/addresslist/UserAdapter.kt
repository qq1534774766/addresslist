package com.gjht.addresslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapter(val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var userImg:ImageView = itemView.findViewById(R.id.userImg)
        var username:TextView = itemView.findViewById(R.id.username)
        var phone:TextView = itemView.findViewById(R.id.phone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
//        inflate 传入子项布局名，父亲，false
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        var user:User = userList.get(position)
        holder.userImg.setImageResource(user.url.toInt())
        holder.username.setText(user.name)
        holder.phone.setText(user.phone)

    }

    override fun getItemCount(): Int = userList.size
}


