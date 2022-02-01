package com.example.sos.Repository

import com.example.sos.model.SosInfoRequest
import com.example.sos.model.SosInfoResponse
import com.example.sos.network.ApiService
import com.example.sos.network.ResponseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*


class SosRepository constructor(private val apiService: ApiService) {
   fun sosFeed(sosInfoRequest: SosInfoRequest): Flow<ResponseManager<SosInfoResponse>> {
        return flow {
            val sosProfile = apiService.sosInfo(sosInfoRequest)
            emit(ResponseManager.Success(sosProfile))
            emit(ResponseManager.Loading(false))
        }.flowOn(Dispatchers.IO)
            .catch { emit(ResponseManager.Failure(it)) }
            .onStart { emit(ResponseManager.Loading(true)) }
            .onCompletion { emit(ResponseManager.Loading(false))}

    }


}




