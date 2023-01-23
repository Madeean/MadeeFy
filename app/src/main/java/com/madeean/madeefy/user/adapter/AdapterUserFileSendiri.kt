package com.madeean.madeefy.user.adapter

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

class AdapterUserFileSendiri(var text: ArrayList<ModelDataMusik>,
                             var context: Context,
                             val namaSP:String
) : RecyclerView.Adapter<AdapterUserFileSendiri.UserFileSendiriViewHolder>() {

    class UserFileSendiriViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var judul: TextView = itemView.findViewById(R.id.tv_judul_item_data_admin_file_sendiri)
        var deskripsi: TextView = itemView.findViewById(R.id.tv_deskripsi_item_data_admin_home)
        var nama: TextView = itemView.findViewById(R.id.tv_pemilik_item_data_admin_file_sendiri)
        var Rl:RelativeLayout = itemView.findViewById(R.id.RL_item_data_admin_home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFileSendiriViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_data_admin_file_sendiri,parent,false)

        return AdapterUserFileSendiri.UserFileSendiriViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFileSendiriViewHolder, position: Int) {
        holder.judul.text = text[position].judul
        holder.deskripsi.text = text[position].deskripsi
        holder.nama.text = namaSP

        if (MyMediaPlayer.currentIndex === position) {
            holder.judul.setTextColor(Color.parseColor("#FF0000"))
        } else {
            holder.judul.setTextColor(Color.parseColor("#000000"))
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
            intent.putExtra("file",text[position].file)
            intent.putExtra("judul",text[position].judul)
            dialog.hide()
//            startactivity
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        if (text.size > 0) {
            return text.size
        } else {
            return 0
        }
    }

}