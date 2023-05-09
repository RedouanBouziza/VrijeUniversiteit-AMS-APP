package com.example.vu.data.decoding

import java.nio.ByteBuffer

/**
 * Decode a section of a packet.
 *
 * Be aware that if you are decoding more than 4 bytes, that you should change
 * the byte buffer size to how much you decode at once with 'ByteBuffer.getInt(byteArray)'
 *
 * @author Kaan Ugur
 */
interface PacketDecoding<T> {

    /**
     * Parses the bytes and returns all the bytes of a specific section.
     *
     * @param data All the bytes in a packet
     * @return All the sections
     */
    fun parsePacket(data: List<UByte>): LinkedHashMap<Int, List<UByte>>

    /**
     * Splits all the bytes into its own little section.
     *
     * For example with A section:
     * From: [65, 89, 0, 3, 65, 29, 9, 4]
     * To: 0: [65, 89, 0, 3], 1: [65, 29, 9, 4]
     *
     * @param array All the bytes of a specific section
     * @return Map with the sections splitted
     */
    fun separateIntoSections(array: List<UByte>): LinkedHashMap<Int, List<UByte>>

    /**
     * Converts all the bytes into an integer and and puts all the
     * values of a specific property into a map.
     *
     * @param array All the bytes in a packet
     * @param byteBuffer Byte buffer
     * @return All the sections in a map with the values
     */
    fun convertBytes(array: List<UByte>, byteBuffer: ByteBuffer): T

}

/**
 * Puts the byte array into the buffer and returns an int
 *
 * @param array array of bytes
 * @return the int value of the byte array
 */
fun ByteBuffer.getInt(array: Array<UByte>): Int {
    this.clear()
    array.forEach { this.put(it.toByte()) }
    this.position(0)
    return this.int
}
