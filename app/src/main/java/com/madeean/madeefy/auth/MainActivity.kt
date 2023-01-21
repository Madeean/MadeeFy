package com.madeean.madeefy.auth

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.madeean.madeefy.R
import com.madeean.madeefy.admin.AdminHome
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.model.ModelAuthLogin
import com.madeean.madeefy.model.ModelLogin
import com.madeean.madeefy.model.ModelRegister
import com.madeean.madeefy.user.UserHome
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    lateinit var btn_register : Button
    lateinit var btn_login : Button
    lateinit var email:TextInputEditText
    lateinit var password:TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register = findViewById(R.id.btn_register_login)
        btn_login = findViewById(R.id.btn_login)

        email = findViewById(R.id.email_input_login)
        password = findViewById(R.id.password_input_login)



        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            if(email.text.toString().equals("") || password.text.toString().equals("")){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }else{
                loginHandle()
            }
        }
    }

    private fun loginHandle() {
        val dialog = ProgressDialog(this@MainActivity)
        dialog.setMessage("Waiting")
        dialog.setCancelable(false)
        dialog.setInverseBackgroundForced(false)
        dialog.show()
        val email = email.text.toString()
        val password = password.text.toString()

        val api: ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

        val tampilData: Call<ModelAuthLogin> = api.login(email,password);

        tampilData.enqueue(object : retrofit2.Callback<ModelAuthLogin>{
            override fun onResponse(
                call: Call<ModelAuthLogin>,
                response: retrofit2.Response<ModelAuthLogin>
            ) {
                dialog.hide()
                if(response.isSuccessful){
                    val data:ModelLogin = response.body()?.data!!
                    val SP: SharedPreferences =  getSharedPreferences("MadeeFy", MODE_PRIVATE)
                    val myEdit: SharedPreferences.Editor = SP.edit()
                    myEdit.putString("token", data.token)
                    myEdit.putString("name", data.name)
                    myEdit.putString("email", data.email)
                    myEdit.apply()

                    if(data.admin == 1){
                        val intent:Intent = Intent(this@MainActivity, AdminHome::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }else if(data.admin == 0){
                        val intent:Intent = Intent(this@MainActivity, UserHome::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }


                }
                else{
                    Toast.makeText(this@MainActivity,"Gagal register",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelAuthLogin>, t: Throwable) {
                dialog.hide()
                Toast.makeText(this@MainActivity,"Gagal menghubungi server",Toast.LENGTH_SHORT).show()
                println("ERROR "+t.message)
            }

        })
    }
}