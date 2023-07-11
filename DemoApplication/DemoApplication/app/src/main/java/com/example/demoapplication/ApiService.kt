package com.example.demoapplication

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?results=10")
    suspend fun getUsers( ): Response<DataClass>
}