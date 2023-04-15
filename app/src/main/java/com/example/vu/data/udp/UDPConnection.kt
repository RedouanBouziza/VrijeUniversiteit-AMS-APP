package com.example.vu.data.udp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.util.Log
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.example.vu.ASectionDecoder


class UDPConnection(
    private val context: Context,
    private val firstDelay: Long,
    private val everyDelay: Long,
    private val setConnectedCallBack: (isConnected: Boolean, isReceivingData: Boolean) -> Unit,
    private val setASectionMeasurement: (measurements: Map<Int, ASection>) -> Unit = {}
) : Runnable {

    companion object {
        private const val UDP_TAG = "UDP"
        private const val UDP_PORT = 1234
        private const val BUFFER_LENGTH = 2048
        private val NETWORK_NAMES = arrayOf("AMS", "AndroidWifi", "VU")
        private const val CONNECTION_TIMEOUT_SECONDS = 3
        private const val BYTE_BUFFER_SIZE = 4
    }

    override fun run() {
        var lastReceivedPacketDate: Date? = null

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
            {

                if (lastReceivedPacketDate === null && userIsOnline()) {
                    setConnectedCallBack(true, false)
                    return@scheduleAtFixedRate
                }

                if (lastReceivedPacketDate === null && userIsOnline()) {
                    Log.i(UDP_TAG, "No stable connection")
                    setConnectedCallBack(false, false)
                    return@scheduleAtFixedRate
                }

                val currentDate = Date()
                val diff = currentDate.time - lastReceivedPacketDate!!.time
                val secondsDifference = diff / 100

                if (secondsDifference >= CONNECTION_TIMEOUT_SECONDS) {
                    Log.i(UDP_TAG, "No stable connection!")
                    setConnectedCallBack(false, false)
                } else {
                    Log.i(UDP_TAG, "Stable connection")
                    setConnectedCallBack(true, true)
                }
            }, firstDelay, everyDelay, TimeUnit.SECONDS
        )

        try {
            val udpSocket = DatagramSocket(UDP_PORT)
            val buffer = ByteArray(BUFFER_LENGTH)
            val packet = DatagramPacket(buffer, buffer.size)
            val aDecoding = ASectionDecoder()
            val byteBuffer = ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE).order(ByteOrder.LITTLE_ENDIAN)

            while (true){
                udpSocket.receive(packet)
                val uByteArray = packet.data.map { it.toUByte() }
                setASectionMeasurement(aDecoding)
            }
        }catch (){

        }
    }

    @SuppressLint("ServiceCast")
    private fun userIsOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        val ssid = wifiInfo.ssid.replace("\"", "")
        val foundNames = NETWORK_NAMES.filter { name -> ssid.contains(name) }

        return foundNames.isNotEmpty() && capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }
}