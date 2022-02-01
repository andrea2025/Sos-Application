package com.example.sos.Utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sos.Repository.SosRepository
import com.example.sos.ViewModel.MainViewModel


class ViewModelFactory(private val repository: SosRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(this.repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}