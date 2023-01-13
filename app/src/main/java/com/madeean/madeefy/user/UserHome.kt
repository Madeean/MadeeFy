package com.madeean.madeefy.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.AdminAdd
import com.madeean.madeefy.admin.AdminFileSendiri
import com.madeean.madeefy.admin.AdminKonfirmasi
import com.madeean.madeefy.admin.AdminSetting
import com.madeean.madeefy.user.adapter.AdapterUserHome

class UserHome : AppCompatActivity() {
    lateinit var rv:RecyclerView
    var listData = ArrayList<String>()
    lateinit var adapter:AdapterUserHome
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        rv = findViewById(R.id.rv_user_home)
        rv.layoutManager = LinearLayoutManager(this)
        listData.clear()
        for (i in 1..10){
            listData.add("Data ke $i")
        }
        adapter = AdapterUserHome(listData,this)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()



        val botttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_user)

        botttomNavigationView.selectedItemId = R.id.home

        botttomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    return@OnNavigationItemSelectedListener true;
                }
                R.id.plus -> {
                    val intent = Intent(this, UserAdd::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {
                    val intent = Intent(this, UserSetting::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.file_sendiri -> {
                    val intent = Intent(this, UserFileSendiri::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
            }
            false
        })
    }
}