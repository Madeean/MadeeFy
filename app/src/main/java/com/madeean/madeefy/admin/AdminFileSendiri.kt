package com.madeean.madeefy.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.adapter.AdminFileSendiriAdapter
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.model.ModelDataMusik
import com.madeean.madeefy.model.ModelListMusik
import com.madeean.madeefy.user.adapter.AdapterUserFileSendiri
import retrofit2.Call
import retrofit2.Callback

class AdminFileSendiri : AppCompatActivity() {
    lateinit var rv: RecyclerView
    var listData = ArrayList<ModelDataMusik>()
    lateinit var adapter: AdminFileSendiriAdapter

    lateinit var tokenSP:String
    lateinit var namaSP:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_file_sendiri)

        val sh = getSharedPreferences("MadeeFy", MODE_PRIVATE)
        tokenSP = sh.getString("token", "")!!
        namaSP = sh.getString("name", "")!!

        rv = findViewById(R.id.rv_admin_file_sendiri)
        rv.layoutManager = LinearLayoutManager(this)
//        listData.clear()
//        for(i in 1..10){
//            listData.add("Data ke $i")
//        }
//        adapter = AdminFileSendiriAdapter(listData,this)
//        rv.adapter = adapter
//        adapter.notifyDataSetChanged()


        val botttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_admin)

        botttomNavigationView.selectedItemId = R.id.file_sendiri

        botttomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, AdminHome::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
                R.id.plus -> {
                    val intent = Intent(this, AdminAdd::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {
                    val intent = Intent(this, AdminSetting::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.konfirm -> {
                    val intent = Intent(this, AdminKonfirmasi::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
                R.id.file_sendiri -> {

                    return@OnNavigationItemSelectedListener true;
                }
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        val dialog = ProgressDialog(this@AdminFileSendiri)
        dialog.setMessage("Waiting")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()
        val api: ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

        val tampilData: Call<ModelListMusik> = api.getMusikSendiri(tokenSP);

        tampilData.enqueue(object : Callback<ModelListMusik> {
            override fun onResponse(call: Call<ModelListMusik>, response: retrofit2.Response<ModelListMusik>) {
                dialog.hide()
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    listData.clear()
                    for (i in data!!.indices) {
                        listData.add(data[i])
                    }
                    adapter = AdminFileSendiriAdapter(listData, this@AdminFileSendiri,namaSP)
                    rv.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@AdminFileSendiri, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelListMusik>, t: Throwable) {
                dialog.hide()
                t.printStackTrace()
            }
        })
    }
}