package com.example.sos.ViewModel

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

                }
                is ResponseManager.Failure -> {

                }
                is ResponseManager.Loading -> {
                }
            }
        }.asLiveData(viewModelScope.coroutineContext)
    }

