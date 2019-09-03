package com.example.biblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart(){
        super.onStart()

        var intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 1010)
    }
}
