package com.madeean.madeefy.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.AdminHome

class MainActivity : AppCompatActivity() {

    lateinit var btn_register : Button
    lateinit var btn_login : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register = findViewById(R.id.btn_register)
        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener {
            val intent = Intent(this, AdminHome::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}