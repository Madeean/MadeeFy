package com.madeean.madeefy.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.adapter.AdminKonfirmasiAdapter

class AdminKonfirmasi : AppCompatActivity() {
    lateinit var rv:RecyclerView
    var listData = ArrayList<String>()
    lateinit var adapter: AdminKonfirmasiAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_konfirmasi)

        rv = findViewById(R.id.rv_admin_konfirmasi)
        rv.layoutManager = LinearLayoutManager(this)
        listData.clear()
        for (i in 1..10){
            listData.add("Data ke $i")
        }
        adapter = AdminKonfirmasiAdapter(listData,this)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()




        val botttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_admin)

        botttomNavigationView.selectedItemId = R.id.konfirm

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
}