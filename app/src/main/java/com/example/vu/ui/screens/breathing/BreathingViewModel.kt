package com.example.vu.ui.screens.breathing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BreathingViewModel: ViewModel() {
    val breathIn = MutableLiveData(0)
    val breathOut = MutableLiveData(0)
    val pause = MutableLiveData(0)
}