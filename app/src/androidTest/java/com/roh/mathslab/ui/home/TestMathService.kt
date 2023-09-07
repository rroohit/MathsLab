package com.roh.mathslab.ui.home

import com.roh.mathslab.domain.repository.MathService
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestMathService: MathService {
    override fun getResultForExpression(expr: String): Call<String> {
        return object : Call<String> {
            override fun clone(): Call<String> {
                return this
            }

            override fun execute(): Response<String> {
                return Response.success("4")
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun isCanceled(): Boolean {
                return false
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout.NONE
            }

            override fun enqueue(callback: Callback<String>) {

            }

        }
    }
}