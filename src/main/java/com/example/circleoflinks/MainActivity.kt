package com.example.circleoflinks

import android.content.Intent
import com.example.circleoflinks.models.UsersModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import android.widget.Button
import com.example.circleoflinks.activitys.LoginActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Llamamos a la vista layaout Activity_main
        setContentView(R.layout.activity_main)


        // inicializamos una lista
        val DateList:List<String> = listOf("lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo");

        val RegionNameList:MutableList<String> = mutableListOf("I Región de Tarapacá", "II Región de Antofagasta", "III Región de Atacama",
                                                               "IV Región de Coquimbo", "V Región de Valparaíso", "VI Región de O’Higgins");


        var texto_mensaje = "";

        val ar_dates = arrayOf("lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo");


        val UsersList:List<String> = listOf("ritch", "robert", "gabriel", "seba", "esteban", "juan", "diego", "miguel", "hector", "shanon");


        RegionNameList.add(2,"x");


        // obtenemos los componentes de la vista por su ID
        val text_view = findViewById<TextView>(R.id.textviewmain);

        // boton STAR LOGIN
        val BtnStartLogin = findViewById<Button>(R.id.button_start_login);

        // text_view.text = ar_dates[0].toString();

        for(position in ar_dates.indices){

          //  texto_mensaje += " - " + ar_dates[position];

        }


         RegionNameList.add(1, "nuevo elemento");

        texto_mensaje = RegionNameList.last();


        // modificamos el texto del textview que tenemos en la vista
        text_view.text = "bienvenido a Circle of Links  hoy es : " + texto_mensaje;


        // Evento de Click en el Boton Start Login

        BtnStartLogin.setOnClickListener{

            val intencionViewLoginAct = Intent(this, LoginActivity::class.java);

            intencionViewLoginAct.putExtra("mensaje", texto_mensaje);


            startActivity(intencionViewLoginAct);



        }





    }


}