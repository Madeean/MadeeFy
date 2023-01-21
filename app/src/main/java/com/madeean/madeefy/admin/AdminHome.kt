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
import com.madeean.madeefy.admin.adapter.AdminHomeAdapter
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.model.ModelDataMusik
import com.madeean.madeefy.model.ModelListMusik
import com.madeean.madeefy.user.adapter.AdapterUserHome
import retrofit2.Call
import retrofit2.Callback

class AdminHome : AppCompatActivity() {
    lateinit var recycleView: RecyclerView
    var listData = ArrayList<ModelDataMusik>()
    lateinit var adapter: AdminHomeAdapter
    lateinit var tokenSP:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val sh = getSharedPreferences("MadeeFy", MODE_PRIVATE)
        tokenSP = sh.getString("token", "")!!

        recycleView = findViewById(R.id.rv_admin_home)

        recycleView.layoutManager = LinearLayoutManager(this)

//        listData.clear()
//        for (i in 1..10){
//            listData.add("Data ke $i")
//        }
//
//        adapter = AdminHomeAdapter(listData,this)
//        recycleView.adapter = adapter
//        adapter.notifyDataSetChanged()





        val botttomNavigationView:BottomNavigationView = findViewById(R.id.bottom_nav_admin)

        botttomNavigationView.selectedItemId = R.id.home

        botttomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
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
                    val intent = Intent(this, AdminFileSendiri::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
            }
            false
        })

    }

    override fun onResume() {
        super.onResume()
        getData();
    }


    private fun getData() {
        val dialog = ProgressDialog(this@AdminHome)
        dialog.setMessage("Waiting")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()

        val api: ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

        val tampilData: Call<ModelListMusik> = api.getAllMusik(tokenSP);

        tampilData.enqueue(object : Callback<ModelListMusik> {
            override fun onResponse(call: Call<ModelListMusik>, response: retrofit2.Response<ModelListMusik>) {
                dialog.hide()
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    listData.clear()
                    for (i in data!!.indices) {
                        listData.add(data[i])
                    }
                    adapter = AdminHomeAdapter(listData, this@AdminHome)
                    recycleView.adapter = adapter
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@AdminHome, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelListMusik>, t: Throwable) {
                dialog.hide()
                t.printStackTrace()
            }
        })
    }
}