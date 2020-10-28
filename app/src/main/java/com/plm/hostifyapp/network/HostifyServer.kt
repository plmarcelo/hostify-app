package com.plm.hostifyapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HostifyServer<T> {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2a  /api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(service: Class<T>): T {
        return retrofit.create(service)
    }
}