package com.plm.hostifyapp.network.login

import com.plm.hostifyapp.models.Pokemon
import com.plm.hostifyapp.models.PokemonList
import com.plm.hostifyapp.models.User
import com.plm.hostifyapp.requests.Login
import retrofit2.Call
import retrofit2.http.*

interface LoginService {

    @POST("login_check")
    fun doLogin(@Body loginRequest: Login): Call<User>;
}