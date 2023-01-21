package com.madeean.madeefy.user

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.madeean.madeefy.R
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.auth.MainActivity
import com.madeean.madeefy.model.ModelListMusik
import com.madeean.madeefy.model.ModelLogout
import com.madeean.madeefy.user.adapter.AdapterUserHome
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback

class UserSetting : AppCompatActivity() {
    lateinit var emailSP:String
    lateinit var namaSP:String
    lateinit var tv_nama_user_person:TextView
    lateinit var tv_email_user_person:TextView
    lateinit var btn_logout_user:Button

    lateinit var tokenSP:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)

        tv_email_user_person = findViewById(R.id.tv_email_user_person)
        tv_nama_user_person = findViewById(R.id.tv_nama_user_person)
        btn_logout_user =  findViewById(R.id.btn_logout_user)



        val sh = getSharedPreferences("MadeeFy", MODE_PRIVATE)
        emailSP = sh.getString("email", "")!!
        namaSP = sh.getString("name", "")!!
        tokenSP = sh.getString("token","")!!

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

        btn_logout_user.setOnClickListener {
            val dialog = ProgressDialog(this@UserSetting)
            dialog.setMessage("Waiting")
            dialog.setCancelable(false)
            dialog.setInverseBackgroundForced(false)
            dialog.show()
            val api: ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

            val tampilData: Call<ModelLogout> = api.logout(tokenSP);
            tampilData.enqueue(object : Callback<ModelLogout> {
                override fun onResponse(call: Call<ModelLogout>, response: retrofit2.Response<ModelLogout>) {
                    dialog.hide()
                    if(response.isSuccessful){

                        Toast.makeText(this@UserSetting, "Berhasil Logout",Toast.LENGTH_SHORT)
                        val intent:Intent = Intent(this@UserSetting,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish();
                    }else{
                        Toast.makeText(this@UserSetting, "Gagal Logout",Toast.LENGTH_SHORT)
                    }
                }

                override fun onFailure(call: Call<ModelLogout>, t: Throwable) {
                    dialog.hide()
                    Toast.makeText(this@UserSetting,"Gagal login, tidak dapat menghubungi server", Toast.LENGTH_SHORT)
                    t.printStackTrace()
                }
            })
        }


    }
}