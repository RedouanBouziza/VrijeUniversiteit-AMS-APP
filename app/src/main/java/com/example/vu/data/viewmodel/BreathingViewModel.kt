package com.example.vu.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BreathingViewModel: ViewModel() {
    val breathIn = MutableLiveData(0.0)
    val breathOut = MutableLiveData(0.0)
    val pauseBreatheIn = MutableLiveData(0.0)
    val pauseBreatheOut = MutableLiveData(0.0)
}