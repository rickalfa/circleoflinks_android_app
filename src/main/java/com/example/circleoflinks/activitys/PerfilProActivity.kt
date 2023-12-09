package com.example.circleoflinks.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.circleoflinks.R
import com.example.circleoflinks.databinding.ActivityLoginBinding
import com.example.circleoflinks.databinding.ActivityPerfilProBinding

class PerfilProActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilProBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilProBinding.inflate(layoutInflater)

        setContentView(binding.root);




    }







}