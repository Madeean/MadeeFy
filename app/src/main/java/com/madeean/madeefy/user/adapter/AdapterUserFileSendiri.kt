package com.madeean.madeefy.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R

class AdapterUserFileSendiri(var text: ArrayList<String>,
                             var context: Context
) : RecyclerView.Adapter<AdapterUserFileSendiri.UserFileSendiriViewHolder>() {

    class UserFileSendiriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_file_sendiri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFileSendiriViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_file_sendiri,parent,false)

        return AdapterUserFileSendiri.UserFileSendiriViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFileSendiriViewHolder, position: Int) {
        holder.judul.text = text[position]
    }

    override fun getItemCount(): Int {
        if (text.size > 0) {
            return text.size
        } else {
            return 0
        }
    }

}