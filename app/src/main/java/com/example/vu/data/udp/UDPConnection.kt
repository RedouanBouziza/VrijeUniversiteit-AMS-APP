package com.example.vu.data.udp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import com.example.vu.data.decoding.decoder.ASectionDecoder
import com.example.vu.data.decoding.models.ASection
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


/**
 * A class that represents a UDP connection. This class handles the connection to a remote server
 * via UDP and receives packets of data.
 *
 * @param context              The context of the application.
 * @param firstDelay           The initial delay before the connection is started.
 * @param everyDelay           The delay between connection attempts.
 * @param setConnectedCallBack A callback function that is called when the connection is established
 *                             or lost.
 * @param setASectionMeasurement A callback function that is called when new data is received from
 *                               the remote server.
 * @author Kaan Ugur
 */
class UDPConnection(
    private val context: Context,
    private val firstDelay: Long,
    private val everyDelay: Long,
    private val setConnectedCallBack: (isConnected: Boolean, isReceivingData: Boolean) -> Unit,
    private val setASectionMeasurement: (measurements: Map<Int, ASection>) -> Unit = {}
) : Runnable {

    companion object {
        /**
         * The tag used for logging.
         */
        private const val UDP_TAG = "UDP"

        /**
         * The length of the buffer used to receive data from the remote server.
         */
        private const val UDP_PORT = 1234

        /**
         * The length of the buffer used to receive data from the remote server.
         */
        private const val BUFFER_LENGTH = 2048

        /**
         * An array of network names to check for when determining if the user is connected to the
         * correct network.
         */
        private val NETWORK_NAMES = arrayOf("AMS", "AndroidWifi", "VU", "R")

        /**
         * The timeout in seconds for determining if the connection is stable.
         */
        private const val CONNECTION_TIMEOUT_SECONDS = 3

        /**
         * The size of the byte buffer used to decode ASections.
         */
        private const val BYTE_BUFFER_SIZE = 4
    }

    override fun run() {
        // With the lastReceivedPacketDate, we can check if the packets are coming in at time
        var lastReceivedPacketDate: Date? = null

        // Check on a different thread if packets are coming in
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
            val byteBuffer =
                ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE)

            println("test")
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
            println("kaasnoot")

            while (true) {
                udpSocket.receive(packet)
//                val uByteArray = packet.data.map { it.toUByte() }
                val uByteArray = arrayListOf<UByte>()
                packet.data.forEach {
                    uByteArray.add(it.toUByte())
                }

                println("uByte array $uByteArray")
                println("packet $packet")

                setASectionMeasurement(aDecoding.convertBytes(uByteArray, byteBuffer))

                lastReceivedPacketDate = Date()
            }
        } catch (e: IOException) {
            Log.e(UDP_TAG, "IO error", e)
            setConnectedCallBack(false, false)
        } catch (e: Exception) {
            Log.e(UDP_TAG, "Error", e)
            setConnectedCallBack(false, false)
        }
    }

    @SuppressLint("ServiceCast")
    private fun userIsOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        var ssid: String? = null
        val wifiManager: WifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.connectionInfo
        if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
            // remove double quotes from ssid format
            ssid = wifiInfo.ssid.replace("\"", "")
        }

        val foundNames = NETWORK_NAMES.filter { name -> ssid.toString().contains(name) }

        return foundNames.isNotEmpty() &&
                capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}