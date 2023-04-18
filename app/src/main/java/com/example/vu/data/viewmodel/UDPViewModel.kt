package com.example.vu.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UDPViewModel : ViewModel() {

    private val isConnected = MutableLiveData(false)
    private val isReceivingData = MutableLiveData(false)

    fun setIsConnected(isConnected: Boolean) {
        this.isConnected.value = isConnected
    }

    fun setIsReceivingData(isReceivingData: Boolean) {
        this.isReceivingData.value = isReceivingData
    }

}