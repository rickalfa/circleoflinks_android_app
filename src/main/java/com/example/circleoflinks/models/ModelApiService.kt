package com.example.circleoflinks.models

import retrofit2.http.GET
import retrofit2.http.Url

interface ModelApiService {

    @GET
    suspend fun getUsers(@Url url:String):String;


}