package com.madeean.madeefy.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.madeean.madeefy.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.setBackgroundDrawable(getDrawable(R.color.krem))
        supportActionBar?.title = "Register"


    }
}


