package com.example.vu.data.udp

import android.content.Context
import kotlinx.coroutines.Delay
import org.w3c.dom.CDATASection

class UDPConnection(
    private val context: Context,
    private val firstDelay: Long,
    private val everyDelay: Long,
    private val setConnectedCallBack: (isConnected: Boolean, isReceivingData: Boolean) -> Unit,
    private val setASectionMeasurement: (measurements: Map<Int, ASection>) -> Unit = {}
): Runnable {

    companion object {
        private const val UDP_TAG = "UDP"
        private const val UDP_PORT = 1234
        private const val BUFFER_LENGTH = 2048
        private val NETWORK_NAMES = arrayOf("AMS", "AndroidWifi", "VU")
        private const val CONNECTION_TIMEOUT_SECONDS = 3
        private const val BYTE_BUFFER_SIZE = 4
    }

    override fun run() {
        TODO("Not yet implemented")
    }
}