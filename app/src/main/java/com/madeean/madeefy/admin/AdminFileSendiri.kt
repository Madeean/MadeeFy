package com.madeean.madeefy.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.adapter.AdminFileSendiriAdapter

class AdminFileSendiri : AppCompatActivity() {
    lateinit var rv: RecyclerView
    var listData = ArrayList<String>()
    lateinit var adapter: AdminFileSendiriAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_file_sendiri)

        rv = findViewById(R.id.rv_admin_file_sendiri)
        rv.layoutManager = LinearLayoutManager(this)
        listData.clear()
        for(i in 1..10){
            listData.add("Data ke $i")
        }
        adapter = AdminFileSendiriAdapter(listData,this)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()


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
}