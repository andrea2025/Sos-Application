package com.example.sos.network

import com.example.sos.model.SosInfoRequest
import com.example.sos.model.SosInfoResponse
import com.google.android.gms.common.api.Api
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("create")
    suspend fun sosInfo(@Body sosInfoRequest: SosInfoRequest): SosInfoResponse

    companion object {

        var retrofitService: ApiService? = null
        fun getInstance() : ApiService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://dummy.restapiexample.com/api/v1/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!
        }
    }
}


