package com.madeean.madeefy.admin.adapter

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madeean.madeefy.R
import com.madeean.madeefy.detail.DetailMusikBerbagi
import com.madeean.madeefy.model.ModelDataMusik
import com.madeean.madeefy.musik.MyMediaPlayer

class AdminFileSendiriAdapter(var listData: ArrayList<ModelDataMusik>,
                              var context: Context,
                              val namaSP:String
) : RecyclerView.Adapter<AdminFileSendiriAdapter.AdminFileSendiriViewHolder>() {

    class AdminFileSendiriViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView) {
     var tvJudulLagu : TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_file_sendiri)
     var nama : TextView = itemView.findViewById(R.id.tv_pemilik_item_data_admin_file_sendiri)
     var tvDeskripsi : TextView = itemView.findViewById(R.id.tv_deskripsi_item_data_admin_home)
        var Rl:RelativeLayout = itemView.findViewById(R.id.RL_item_data_admin_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminFileSendiriViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_file_sendiri,parent,false)

        return AdminFileSendiriAdapter.AdminFileSendiriViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminFileSendiriViewHolder, position: Int) {
        holder.tvJudulLagu.text = listData[position].judul
        holder.nama.text = namaSP
        holder.tvDeskripsi.text = listData[position].deskripsi

        if (MyMediaPlayer.currentIndex === position) {
            holder.tvJudulLagu.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.tvJudulLagu.setTextColor(Color.parseColor("#000000"))
        }

        holder.Rl.setOnClickListener(View.OnClickListener {
            val dialog = ProgressDialog(context)
            dialog.setMessage("Waiting")
            dialog.setCancelable(false)
            dialog.setInverseBackgroundForced(false)
            dialog.show()
            //navigate to another acitivty
            MyMediaPlayer.getInstance()?.reset()
            MyMediaPlayer.currentIndex = position

            val intent: Intent = Intent(context, DetailMusikBerbagi::class.java)
            intent.putExtra("file",listData[position].file)
            intent.putExtra("judul",listData[position].judul)
            dialog.hide()
//            startactivity
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        if(listData.size > 0){
            return listData.size
        }else{
            return 0
        }
    }


}