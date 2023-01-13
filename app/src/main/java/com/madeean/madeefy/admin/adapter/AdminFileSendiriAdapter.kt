package com.madeean.madeefy.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R

class AdminFileSendiriAdapter(var listData: ArrayList<String>,
                              var context: Context
) : RecyclerView.Adapter<AdminFileSendiriAdapter.AdminFileSendiriViewHolder>() {

    class AdminFileSendiriViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView) {
     var tvJudulLagu : TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_file_sendiri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminFileSendiriViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_file_sendiri,parent,false)

        return AdminFileSendiriAdapter.AdminFileSendiriViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminFileSendiriViewHolder, position: Int) {
        holder.tvJudulLagu.text = listData[position]
    }

    override fun getItemCount(): Int {
        if(listData.size > 0){
            return listData.size
        }else{
            return 0
        }
    }


}