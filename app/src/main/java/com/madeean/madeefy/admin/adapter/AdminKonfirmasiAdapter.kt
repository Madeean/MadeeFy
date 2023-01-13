package com.madeean.madeefy.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R

class AdminKonfirmasiAdapter( var listData: ArrayList<String>,
                              var context: Context
) : RecyclerView.Adapter<AdminKonfirmasiAdapter.AdminKonfirmasiViewHolder>(){

    class AdminKonfirmasiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.tv_nama_admin_konfirmasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminKonfirmasiViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_konfirmasi,parent,false)

        return AdminKonfirmasiAdapter.AdminKonfirmasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminKonfirmasiViewHolder, position: Int) {
        holder.nama.text = listData[position]
    }

    override fun getItemCount(): Int {
        if(listData != null){
            return listData.size
        }else{
            return 0
        }
    }


}