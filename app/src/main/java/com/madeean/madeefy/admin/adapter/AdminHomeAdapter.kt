package com.madeean.madeefy.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R
import com.madeean.madeefy.model.ModelDataMusik

class AdminHomeAdapter(
    var text: ArrayList<ModelDataMusik>,
    var context: Context):
    RecyclerView.Adapter<AdminHomeAdapter.AdminHomeViewHolder>() {

    class AdminHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_home)
        var deskripsi:TextView = itemView.findViewById(R.id.tv_deskripsi_item_data_admin_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHomeViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_home,parent,false)

        return AdminHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminHomeViewHolder, position: Int) {
        holder.judul.setText(text[position].judul)
        holder.deskripsi.text = text[position].deskripsi
    }

    override fun getItemCount(): Int {
        if(text.size > 0){
            return text.size
        }else{
            return 0
        }
    }


}