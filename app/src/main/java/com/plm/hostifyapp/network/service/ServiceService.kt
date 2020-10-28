package com.plm.hostifyapp.network.service

import com.plm.hostifyapp.models.Service
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ServiceService {

    @GET("v2/service")
    fun getServicesList(@Header("Authorization") token: String): Call<List<Service>>
}