package com.example.circleoflinks.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.circleoflinks.R

import android.widget.Button
import android.widget.TextView
import com.example.circleoflinks.MainActivity
import com.example.circleoflinks.activitys.ContentMainActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);

        val BtnLogin = findViewById<Button>(R.id.button_login);
        val textview_nameapp = findViewById<TextView>(R.id.textView_nameapp);



        val name_app = intent.extras?.getString("mensaje").orEmpty();


        if (name_app.isNotEmpty()){

            textview_nameapp.text = name_app;

        }


        /// accion de CLICK en el Boton

        BtnLogin.setOnClickListener {


            // creamos la Intencion para llamar a la actividad ContenMainActivity
            val intencion = Intent(this, ContentMainActivity::class.java);

            startActivity(intencion);
        }

    }

    private fun initComponents()
    {

    }


    private fun initListeners()
    {


    }

}