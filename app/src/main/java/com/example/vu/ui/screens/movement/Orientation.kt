package com.example.vu.ui.screens.movement

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.Surface

class Orientation(private val context: Context) {
    private val mSensorManager: SensorManager by lazy {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    private val mRotationSensor: Sensor? by lazy {
        mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }

    fun startListening() {
        mSensorManager.registerListener(mSensorEventListener, mRotationSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    var listener: ((Float, Float, Float) -> Unit)? = null

    private val mSensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        @SuppressLint("NewApi")
        override fun onSensorChanged(event: SensorEvent?) {
            event?.let {
                if (it.sensor == mRotationSensor) {
                    val rotationMatrix = FloatArray(9)
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

                    val (worldAxisForDeviceAxisX, worldAxisForDeviceAxisY) = when (context.display?.rotation) {
                        Surface.ROTATION_0 -> Pair(SensorManager.AXIS_X, SensorManager.AXIS_Z)
                        Surface.ROTATION_90 -> Pair(
                            SensorManager.AXIS_Z,
                            SensorManager.AXIS_MINUS_X
                        )
                        Surface.ROTATION_180 -> Pair(
                            SensorManager.AXIS_MINUS_X,
                            SensorManager.AXIS_MINUS_Z
                        )
                        Surface.ROTATION_270 -> Pair(
                            SensorManager.AXIS_MINUS_Z,
                            SensorManager.AXIS_X
                        )
                        else -> Pair(SensorManager.AXIS_X, SensorManager.AXIS_Z)
                    }

                    val adjustedRotationMatrix = FloatArray(9)
                    SensorManager.remapCoordinateSystem(
                        rotationMatrix, worldAxisForDeviceAxisX,
                        worldAxisForDeviceAxisY, adjustedRotationMatrix
                    )

                    // Transform rotation matrix into azimuth/pitch/roll
                    val orientation = FloatArray(3)
                    SensorManager.getOrientation(adjustedRotationMatrix, orientation)

                    // Convert radians to degrees
                    val pitch = orientation[1] * -57
                    val roll = orientation[2] * -57
                    val yaw = orientation[0] * -57

                    // Update state with new values
                    listener?.invoke(pitch, roll, yaw)
                }
            }
        }
    }
}
