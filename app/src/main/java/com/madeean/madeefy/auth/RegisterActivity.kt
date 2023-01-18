package com.madeean.madeefy.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.messaging.FirebaseMessaging
import com.madeean.madeefy.R
import com.madeean.madeefy.api.ApiRequest
import com.madeean.madeefy.api.Server
import com.madeean.madeefy.model.ModelRegister
import retrofit2.Call

class RegisterActivity : AppCompatActivity() {
    lateinit var email:TextInputEditText
    lateinit var password:TextInputEditText
    lateinit var name:TextInputEditText
    lateinit var btn_register:Button

    lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email_input_register)
        password = findViewById(R.id.password_input_register)
        name = findViewById(R.id.name_input_register)

        btn_back = findViewById(R.id.iv_back_register)

        btn_back.setOnClickListener {
            val intent:Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        btn_register = findViewById(R.id.btn_register)

        btn_register.setOnClickListener {
            if(email.text.toString().equals("") || name.text.toString().equals("") || password.text.toString().equals("")){
                Toast.makeText(this,"Isi semuanya dulu",Toast.LENGTH_SHORT);
            }
            else{
                registerHandle()
            }

        }


    }

    private fun registerHandle() {
        val email:String = email.text.toString()
        val password:String = password.text.toString()
        val name:String = name.text.toString()

        val api:ApiRequest = Server.konekRetrofit()?.create(ApiRequest::class.java)!!

        val tampilData: Call<ModelRegister> = api.register(name,email,password);

        tampilData.enqueue(object : retrofit2.Callback<ModelRegister>{
            override fun onResponse(
                call: Call<ModelRegister>,
                response: retrofit2.Response<ModelRegister>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@RegisterActivity,"Berhasil register",Toast.LENGTH_SHORT).show()
                    val intent:Intent = Intent(this@RegisterActivity,MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@RegisterActivity,"Gagal register",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModelRegister>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"Gagal menghubungi server",Toast.LENGTH_SHORT).show()
                println("ERROR "+t.message)
            }

        })

    }
}


