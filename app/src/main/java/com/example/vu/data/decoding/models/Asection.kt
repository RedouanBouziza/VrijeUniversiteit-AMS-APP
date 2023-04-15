package com.example.vu.data.decoding.models

/**
 * Represents an 'A' section of a packet
 *
 * @author Kaan Ugur
 */
data class ASection(
    val tickCount: Int,
    val icg: Double,
    val twoEcg: Double,
    val isrc: Int,
    val ecg: Double,
    val temperature: Double
)
