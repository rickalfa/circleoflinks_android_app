package com.example.circleoflinks.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.circleoflinks.R

import android.widget.Button
import android.widget.TextView
import com.example.circleoflinks.MainActivity
import com.example.circleoflinks.activitys.ContentMainActivity
import com.example.circleoflinks.databinding.ActivityLoginBinding
import com.example.circleoflinks.models.ModelApiService

import com.example.circleoflinks.config.ConfigApp


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.json.JSONArray
import org.json.JSONObject
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    private val responseLogin: String = "";
    private val ConApp = ConfigApp();

    private var responseRe: String = "";



    // Binding creamos una vinculacion de vista para cargar el layout y acceder a sus componentes
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root);



        ///obtenemos lso datos enviados desde otro activiti con intent
        val name_app = intent.extras?.getString("mensaje").orEmpty();


        if (name_app.isNotEmpty()){

            binding.textViewNameapp.text = name_app;
        }


        /// accion de CLICK en el Boton

        binding.buttonLogin.setOnClickListener {

            val emailuser = binding.inputEmail.text;
            val passuser = binding.inputPassword.text;

            GetuserLogin("users/login/$emailuser/$passuser")


        }

    }


    private fun initSession(UserjsonObject: JSONObject)
    {
        val keyshared: Boolean = this.loadkeySharedExist("dateuser");

        if (keyshared){

            Log.v("STATE KEY SHARED : ", " DONE exist");


        }else{

            Log.v("STATE KEY SHARED : ", " NO exist");

            this.saveDateUserSession("dateuser", UserjsonObject);

        }

        val state_user = UserjsonObject.getJSONObject("userauth");

        Log.v("STATE USER : ", state_user.getString("status_user_id").toString());

        val  status_user : Int= state_user.getString("status_user_id").toInt();

        if (status_user == 1){

            this.accessPerfil(true);



        }else{

            this.accesContent(true);

        }


    }

    private fun saveDateUserSession(key_date :String ,UserjsonObject: JSONObject)
    {
        val sharedDates: SharedPreferences = getPreferences( Context.MODE_PRIVATE );

        val editor = sharedDates.edit()

        editor.apply{

            putString(key_date, UserjsonObject.toString());

        }.apply()


    }

    private fun loadkeySharedExist(key_shared: String): Boolean{

        var respon: Boolean = false;
        val sharedPref :SharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains(key_shared)){

            respon = true;

        }else{
            respon = false;
        }


    return respon;


    }


    //metodo que abre otra activity
    private fun accessPerfil(done: Boolean)
    {

        if (done){


                // creamos la Intencion para llamar a la actividad ContenMainActivity
                val intencion = Intent(this, PerfilProActivity::class.java);

                startActivity(intencion);


        }


    }

    private fun accesContent(done: Boolean){
        if (done){


            // creamos la Intencion para llamar a la actividad ContenMainActivity
            val intencion = Intent(this, ContentMainActivity::class.java);

            startActivity(intencion);


        }

    }

    private fun getRetrofit():Retrofit{


        return Retrofit.Builder()
            .baseUrl(this.ConApp.getURLAPI())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    }

    // Corrutine estas Corrutinas son utilizadas para crear procesos  Una corrutina es un patrón de diseño de simultaneidad
    // que puedes usar en Android para simplificar el código que se ejecuta de forma asíncrona
    //las corrutinas ayudan a administrar tareas de larga duración que, de lo contrario, podrían bloquear el
    // subproceso principal y hacer que tu app dejara de responder

    private fun GetuserLogin(query : String):String{

        //tipos de hilos de coroutine
        //Main = hilo principal
        // IO =el hilo va de forma secundaria
        // Default = procesar informacion que requiere mas procesamiento


        CoroutineScope(Dispatchers.IO).launch {

            val call:String = getRetrofit().create(ModelApiService::class.java).getUsers(query);


            runOnUiThread {

                if(call.isNotEmpty())
                {
                    // respuesta realisada
                   // binding.textViewNameapp.text = call

                    Log.v("Mensaje API",call.toString());


                    // transformamos la respuesta String en un ObjectJSON
                     val jsonRes: JSONObject = JSONObject(call);


                    Log.v("Mensaje Response de Json ", jsonRes.getString("login-state"));

                    responseRe = jsonRes.getString("login-state");


                    Toast.makeText(this@LoginActivity, "login : $responseRe",Toast.LENGTH_LONG).show();

                    if (responseRe.equals("success")){



                        this@LoginActivity.initSession(jsonRes);


                    }


                }else{

                    showError();
                    // nos se consiguio respuesta


                }

            }




        }

        ////fin de la Coroutina

        binding.errorMessage.text = responseRe.toString()

        //Toast.makeText(this, " 2 login : $responseRe",Toast.LENGTH_LONG).show()


        return "finish";

    }


    private fun showError(){

        binding.textViewNameapp.text = "Error en la ejecucion de la corrutine";

    }




}