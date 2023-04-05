package nl.hva.vuwearable.udp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import nl.hva.vuwearable.BuildConfig
import nl.hva.vuwearable.decoding.decoder.ASectionDecoder
import nl.hva.vuwearable.decoding.models.ASection
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.SocketException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Handles the connection with UDP and decodes the data that is coming in.
 *
 * @author Bunyamin Duduk
 * @author Hugo Zuidema
 *
 * @property context
 * @property firstDelay delay that only will happen at the start
 * @property everyDelay delay that happens after every delay
 * @property setConnectedCallback gives back the connection in a callback
 * @property setASectionMeasurement gives back the A section measurement in a callback
 */
class UDPConnection(
    private val context: Context,
    private val firstDelay: Long,
    private val everyDelay: Long,
    private val setConnectedCallback: (isConnected: Boolean, isReceivingData: Boolean) -> Unit,
    private val setASectionMeasurement: (measurements: Map<Int, ASection>) -> Unit = {}
) : Runnable {

    companion object {
        const val UDP_TAG = "UDP"
        const val UDP_PORT = 1234
        const val BUFFER_LENGTH = 2048
        val NETWORK_NAMES = arrayOf("AMS", "AndroidWifi", "VU")
        const val CONNECTION_TIMEOUT_SECONDS = 3

        // In the normal decoder of AMS, a size of 10000000 is being used.
        // This is unnecessary and too big for what it needs.
        // When you add new sections that contain more than 4 byte elements in a byte array and the app crashes,
        // then you need to increase the byte buffer size to how much elements you have
        // in the byte array
        const val BYTE_BUFFER_SIZE = 4
    }

    override fun run() {
        // With the lastReceivedPacketDate, we can check if the packets are coming in at time
        var lastReceivedPacketDate: Date? = null

        // Check on a different thread if packets are coming in
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
            // If no packets are received yet
            if (lastReceivedPacketDate === null && userIsOnline()) {
                setConnectedCallback(true, false)
                return@scheduleAtFixedRate
            }

            if (lastReceivedPacketDate === null && !userIsOnline()) {
                Log.i(UDP_TAG, "No stable connection")
                setConnectedCallback(false, false)
                return@scheduleAtFixedRate
            }

            val currentDate = Date()
            val diff = currentDate.time - lastReceivedPacketDate!!.time
            val secondsDifference = diff / 1000

            // Connection is not stable
            if (secondsDifference >= CONNECTION_TIMEOUT_SECONDS) {
                Log.i(UDP_TAG, "No stable connection!")
                setConnectedCallback(false, false)
            } else {
                // Connection is stable
                Log.i(UDP_TAG, "Stable connection")
                setConnectedCallback(true, true)
            }
        }, firstDelay, everyDelay, TimeUnit.SECONDS)
        try {
            val udpSocket = DatagramSocket(UDP_PORT)
            val buffer = ByteArray(BUFFER_LENGTH)
            val packet = DatagramPacket(buffer, buffer.size)

            val aDecoding = ASectionDecoder()

            val byteBuffer = ByteBuffer.allocateDirect(BYTE_BUFFER_SIZE)

            // Place the biggest byte value at the start and the lowest at the end
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN)

            // Go through everytime a packet comes in
            while (true) {
                udpSocket.receive(packet)

                // Make a list of UBytes since Kotlin converts a UByteArray to a ByteArray
                val uByteArray = arrayListOf<UByte>()
                packet.data.forEach {
                    uByteArray.add(it.toUByte())
                }

                setASectionMeasurement(aDecoding.convertBytes(uByteArray, byteBuffer))

                // Set the last received date to see if there is a delay between next packet
                lastReceivedPacketDate = Date()
            }
        } catch (e: SocketException) {
            Log.e(UDP_TAG, "Socket error", e)
            setConnectedCallback(false, false)
        } catch (e: IOException) {
            Log.e(UDP_TAG, "IO error", e)
            setConnectedCallback(false, false)
        }
    }

    /**
     * Function which checks if the current user is connected to the correct network (if any)
     * and checks if the current network (if available) has WiFi capabilities
     */
    private fun userIsOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        var ssid: String? = null
        val wifiManager: WifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
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