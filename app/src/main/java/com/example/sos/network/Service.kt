package com.example.sos.network

import com.example.sos.model.SosInfoRequest
import com.example.sos.model.SosInfoResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ServiceWrapper {
    interface ApiService {
        @POST("create")
        suspend fun sosInfo(@Body sosInfoRequest: SosInfoRequest):SosInfoResponse
    }

    private fun buildService(url: String): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://dummy.restapiexample.com/api/v1/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    fun getNetworkService(url: String) = buildService(url)
}
