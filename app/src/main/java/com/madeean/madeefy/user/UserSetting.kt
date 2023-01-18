package com.madeean.madeefy.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import org.w3c.dom.Text

class UserSetting : AppCompatActivity() {
    lateinit var emailSP:String
    lateinit var namaSP:String
    lateinit var tv_nama_user_person:TextView
    lateinit var tv_email_user_person:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)

        tv_email_user_person = findViewById(R.id.tv_email_user_person)
        tv_nama_user_person = findViewById(R.id.tv_nama_user_person)


        val sh = getSharedPreferences("MadeeFy", MODE_PRIVATE)
        emailSP = sh.getString("email", "")!!
        namaSP = sh.getString("name", "")!!

        tv_email_user_person.text = emailSP
        tv_nama_user_person.text = namaSP



        val botttomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav_user)

        botttomNavigationView.selectedItemId = R.id.setting

        botttomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, UserHome::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true;
                }
                R.id.plus -> {
                    val intent = Intent(this, UserAdd::class.java)
                    startActivity(intent)
                    this.overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {

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