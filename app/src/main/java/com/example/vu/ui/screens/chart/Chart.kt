package com.example.vu.ui.screens.chart

import android.view.Gravity
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.vu.BuildConfig
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.*
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.core.annotations.Orientation
import com.scichart.core.framework.UpdateSuspender
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil
import java.util.Collections


@Composable
fun Chart(navController: NavHostController) {
    SciChartSurfaceView()
}


@Composable
fun SciChartSurfaceView() {
    SciChartSurface.setRuntimeLicenseKey(BuildConfig.SCI_CHART_KEY)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AndroidView(
            factory = { context ->
                // Create Android View with SciChartSurface
                val sciChartSurface = SciChartSurface(context)

                // Configure SciChartSurface with chart data and options here
                val xAxis = NumericAxis(context)
                val yAxis = NumericAxis(context)

                // The lines that are shown in the graph
                val twoEcgLineSeries = FastLineRenderableSeries()
                val isrcLineSeries = FastLineRenderableSeries()
                val ecgLineSeries = FastLineRenderableSeries()
                val icgLineSeries = FastLineRenderableSeries()
                val temperatureLineSeries = FastLineRenderableSeries()

                // Legend with all the data from the lines
                val legendModifier = LegendModifier(context)
                legendModifier.setOrientation(Orientation.VERTICAL)
                legendModifier.setLegendPosition(Gravity.START, 0, 0, 0, 10)

                val twoEcgData = XyDataSeries<Double, Double>(
                    Double::class.javaObjectType,
                    Double::class.javaObjectType
                )

                val isrcData = XyDataSeries<Double, Double>( // TODO: Maybe Integer
                    Double::class.javaObjectType,
                    Double::class.javaObjectType
                )

                val ecgData = XyDataSeries<Double, Double>(
                    Double::class.javaObjectType,
                    Double::class.javaObjectType
                )

                val icgData = XyDataSeries<Double, Double>(
                    Double::class.javaObjectType,
                    Double::class.javaObjectType
                )

                val temperatureData = XyDataSeries<Double, Double>(
                    Double::class.javaObjectType,
                    Double::class.javaObjectType
                )

                for (i in 0 until 100) {
                    val x = i.toDouble()
                    val y = kotlin.math.sin(x * 0.2)
                    twoEcgData.append(x, y)
                }

                for (i in 0 until 100) {
                    val x = i.toDouble()
                    val y = kotlin.math.sin(x * 0.9)
                    isrcData.append(x, y)
                }

                for (i in 0 until 100) {
                    val x = i.toDouble()
                    val y = kotlin.math.sin(x * 0.2)
                    ecgData.append(x, y)
                }

                for (i in 0 until 100) {
                    val x = i.toDouble()
                    val y = kotlin.math.sin(x * 0.2)
                    icgData.append(x, y)
                }

                for (i in 0 until 100) {
                    val x = i.toDouble()
                    val y = kotlin.math.sin(x * 0.2)
                    temperatureData.append(x, y)
                }

                // Create a line series with the data series and set its properties
                twoEcgLineSeries.apply {
                    dataSeries = twoEcgData
                    strokeStyle = SolidPenStyle(ColorUtil.Green, true, 5f, null)
                }

                isrcLineSeries.apply {
                    dataSeries = isrcData
                    strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
                }

                ecgLineSeries.apply {
                    dataSeries = ecgData
                    strokeStyle = SolidPenStyle(ColorUtil.LimeGreen, true, 5f, null)
                }

                icgLineSeries.apply {
                    dataSeries = icgData
                    strokeStyle = SolidPenStyle(ColorUtil.Yellow, true, 5f, null)
                }

                temperatureLineSeries.apply {
                    dataSeries = temperatureData
                    strokeStyle = SolidPenStyle(ColorUtil.Red, true, 5f, null)
                }

                // Add the line series to the chart's collection
                UpdateSuspender.using(sciChartSurface) {
                    Collections.addAll(
                        sciChartSurface.renderableSeries,
                        twoEcgLineSeries,
                        ecgLineSeries,
                        isrcLineSeries,
                        icgLineSeries,
                        temperatureLineSeries
                    )
                    Collections.addAll(
                        sciChartSurface.chartModifiers,
                        PinchZoomModifier(),
                        ZoomPanModifier(),
                        ZoomExtentsModifier()
                    )
                    Collections.addAll(sciChartSurface.xAxes, xAxis)
                    Collections.addAll(sciChartSurface.yAxes, yAxis)
                    Collections.addAll(sciChartSurface.chartModifiers, legendModifier)
                    Collections.addAll(sciChartSurface.chartModifiers, RolloverModifier())
                }
                sciChartSurface // return the SciChartSurface instance
            },
            update = {
                // add any update logic here
            }
        )
    }
}