package com.example.circleoflinks.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.circleoflinks.R

class ContentMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_main)

        val Btn_back = findViewById<Button>(R.id.button_back_view);



        Btn_back.setOnClickListener{

            super.onBackPressed();

        };




    }
}