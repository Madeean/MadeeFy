package com.madeean.madeefy.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.adapter.AdminHomeAdapter
import com.madeean.madeefy.model.ModelDataMusik
import org.w3c.dom.Text

class AdapterUserHome(var text: ArrayList<ModelDataMusik>,
                      var context: Context
) : RecyclerView.Adapter<AdapterUserHome.UserHomeViewHolder>() {

    class UserHomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_home)
        var deskripsi:TextView= itemView.findViewById(R.id.tv_deskripsi_item_data_admin_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHomeViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_home,parent,false)

        return AdapterUserHome.UserHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserHomeViewHolder, position: Int) {
        holder.judul.text = text[position].judul
        holder.deskripsi.text = text[position].deskripsi
    }

    override fun getItemCount(): Int {
        if (text.size > 0) {
            return text.size
        } else {
            return 0
        }
    }


}