package com.example.sos.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sos.Repository.SosRepository
import com.example.sos.model.SosInfoRequest
import com.example.sos.network.ResponseManager
import kotlinx.coroutines.flow.onEach

class MainViewModel constructor(private val sosRepository: SosRepository) : ViewModel() {
    fun sendSosRequest(sosInfoRequest: SosInfoRequest) =
        sosRepository.sosFeed(sosInfoRequest).onEach { response ->
            when (response) {
                is ResponseManager.Success -> {
                    Log.i("success",response.data.status)

                }
                is ResponseManager.Failure -> {
                    Log.i("failed",response.error.toString())

                }
                is ResponseManager.Loading -> {
                }
            }
        }.asLiveData(viewModelScope.coroutineContext)
    }

