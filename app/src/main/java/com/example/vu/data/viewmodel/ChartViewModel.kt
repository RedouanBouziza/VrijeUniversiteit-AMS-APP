package com.example.vu.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vu.data.decoding.models.ASection
import java.util.*

/**
 * @author Kaan Ugur
 * @author Bunyamin Duduk
 */
class ChartViewModel : ViewModel() {

    val sectionAMeasurements: MutableLiveData<TreeMap<Int, ASection>> = MutableLiveData()

    fun setASectionMeasurement(measurements: TreeMap<Int, ASection>) {
        this.sectionAMeasurements.value = measurements
    }

}
