package com.example.circleoflinks.models

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

import com.example.circleoflinks.config.ConfigApp

class RetrofitRequest {

private var ConApp : ConfigApp = ConfigApp()

    public fun getRetrofit(): Retrofit {


        return Retrofit.Builder()
            .baseUrl(this.ConApp.getURLAPI())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    }


}