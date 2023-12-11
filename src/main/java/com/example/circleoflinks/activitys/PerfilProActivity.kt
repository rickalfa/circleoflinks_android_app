package com.example.circleoflinks.activitys

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.circleoflinks.R
import com.example.circleoflinks.databinding.ActivityLoginBinding

import com.example.circleoflinks.databinding.ActivityPerfilProBinding
import com.example.circleoflinks.models.ModelApiService
import com.example.circleoflinks.models.RetrofitRequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers


import org.json.JSONObject
import java.lang.reflect.Array

class PerfilProActivity : AppCompatActivity() {

    private var RetroRequest = RetrofitRequest();

    private var items_type_user = arrayOf("free","pro","reclutador");
    private lateinit var SpinTypeUser: Spinner ;


    private lateinit var binding: ActivityPerfilProBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPerfilProBinding.inflate(layoutInflater)

        setContentView(binding.root);


        var SpinItem = binding.spinnerTypeuser;

        this.SpinTypeUser = SpinItem;

        SpinItem.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.items_type_user);


        var ButtonSave = binding.buttonSavePerfil;


        ButtonSave.setOnClickListener{


           this.initCreatePerfil();


        }


    }

    //// END OnCreate

    private fun initCreatePerfil(){


        var inputs_ar = this.getInputsDates();

        val list_inputs =" "

        for (input in inputs_ar) {

            Log.v("datos de Inputs : ", input.toString() );

        }


      //  Log.v("datos de Inputs : ", inputs_ar[2].toString() );

        val id_user: Int = getIdUser();

        this.createPerfilUser("usersperfil?info="+inputs_ar[0]+ "info&education=" +inputs_ar[1]+
                "&habilidades="+inputs_ar[2]+"&profetion_name="+inputs_ar[3]+"&user_id="+id_user.toString()+"&exp_laboral="+inputs_ar[4]);



    }

    private fun getInputsDates(): kotlin.Array<String>
    {

        var inputsDates_ar = arrayOf("");

        var state_user = 0;
        //obtenemos el valor del spinner del State user
        val itemselec: String= this.SpinTypeUser.selectedItem.toString();

        when(itemselec){

            "free"-> state_user = 1
            "pro"->  state_user = 2
            "reclutador"-> state_user = 3


        }

        Log.v(" ide Items select DropMenu", state_user.toString());

        val existDates = this.loadkeySharedExist("dateuser")

        if (existDates){

            this.showToastMessage("los Shareddatos user si existen", 0)

            val id_user : Int = getIdUser();

            Log.v("ID del User : " , id_user.toString())

        }


        if (getIdUser() != 0){
            val user_id = getIdUser()

            val infoextra = binding.inputinfo.text;
            val education = binding.inputestudiante.text;
            val habilidades = binding.inputhabilidades.text;
            val profesion_name = binding.inputnombrepro.text;

            val stateinfoextra = infoextra.toString().isNotEmpty();
            val stateeducation = education.toString().isNotEmpty();
            val statehabilidades = habilidades.toString().isNotEmpty();
            val stateprofesion_name = profesion_name.toString().isNotEmpty();


            if ( stateinfoextra || stateeducation || statehabilidades || stateprofesion_name ){

                showToastMessage("campos vacios Complete los datos ", 1);

                inputsDates_ar = arrayOf(infoextra.toString(), education.toString(), habilidades.toString(), profesion_name.toString(),user_id.toString())

                for (input in inputsDates_ar) {

                    Log.v("getdatesInputs datos de Inputs : ", input.toString() );

                }


            }else{


                inputsDates_ar = arrayOf(infoextra.toString(), education.toString(), habilidades.toString(), profesion_name.toString(),user_id.toString())

                for (input in inputsDates_ar) {

                    Log.v("getdatesInputs datos de Inputs : ", input.toString() );

                }


            }



        }

        return inputsDates_ar;

    }


///////// END getInputsDAtes()

    private fun createPerfilUser(query : String): String{

        var resp: String = "";

        CoroutineScope(Dispatchers.IO).launch {

            val responseApi: String = RetroRequest.getRetrofit().create(ModelApiService::class.java).createUserPerfil(query)

            runOnUiThread {

                if(responseApi != null && responseApi.length > 150){



                    Log.v("Respuesta Api ", responseApi.toString());





                    this@PerfilProActivity.showToastMessage("mensaje de la API " + responseApi.toString() , 2)

                }else{


                    this@PerfilProActivity.showToastMessage("respuesta mala de la api", 2)


                }

            }




        }


        return resp;

    }
    public fun loadkeySharedExist(key_shared: String): Boolean{

        var respon: Boolean = false;
        val sharedPref : SharedPreferences = getSharedPreferences("usersessiondate",Context.MODE_PRIVATE);

        if(sharedPref.contains(key_shared)){

            respon = true;

        }else{
            respon = false;
        }


        return respon;


    }

    public fun getIdUser(): Int{
        var id_user = 0;

        val sharedDatesUser = getSharedPreferences("usersessiondate", Context.MODE_PRIVATE);

           if (sharedDatesUser.contains("dateuser")){

               var dates_user = sharedDatesUser.getString("dateuser", "no-exist");

               val jsonRes: JSONObject = JSONObject(dates_user);

               val jsonUser = jsonRes.getJSONObject("userauth");

               id_user = jsonUser.getString("id").toInt();

           }




        return id_user;
    }

    private fun saveDateUserSession(key_date :String ,UserjsonObject: JSONObject)
    {
        val sharedDates: SharedPreferences = getSharedPreferences("usersessiondate", Context.MODE_PRIVATE );

        val editor = sharedDates.edit()

        editor.apply{

            putString(key_date, UserjsonObject.toString());

        }.apply()


    }

    private fun showToastMessage(mensaje: String, time_toat : Int)
    {

        Toast.makeText(this, mensaje, time_toat).show();



    }





}