package com.madeean.madeefy.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R
import com.madeean.madeefy.model.ModelDataMusik

class AdapterUserFileSendiri(var text: ArrayList<ModelDataMusik>,
                             var context: Context,
                             val namaSP:String
) : RecyclerView.Adapter<AdapterUserFileSendiri.UserFileSendiriViewHolder>() {

    class UserFileSendiriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_file_sendiri)
        var deskripsi: TextView = itemView.findViewById(R.id.tv_deskripsi_item_data_admin_home)
        var nama: TextView = itemView.findViewById(R.id.tv_pemilik_item_data_admin_file_sendiri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFileSendiriViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_file_sendiri,parent,false)

        return AdapterUserFileSendiri.UserFileSendiriViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFileSendiriViewHolder, position: Int) {
        holder.judul.text = text[position].judul
        holder.deskripsi.text = text[position].deskripsi
        holder.nama.text = namaSP
    }

    override fun getItemCount(): Int {
        if (text.size > 0) {
            return text.size
        } else {
            return 0
        }
    }

}