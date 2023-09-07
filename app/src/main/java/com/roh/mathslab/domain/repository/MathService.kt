package com.roh.mathslab.domain.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MathService {

    @GET("v4/")
    fun getResultForExpression(
        @Query("expr") expr: String
    ): Call<String>


}
