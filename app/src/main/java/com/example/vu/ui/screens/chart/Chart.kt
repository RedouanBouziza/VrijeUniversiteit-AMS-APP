package com.example.vu.ui.screens.chart

import android.view.Gravity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.vu.BuildConfig
import androidx.fragment.app.activityViewModels
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.data.websocket.SocketService
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.*
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries
import com.scichart.core.annotations.Orientation
import com.scichart.core.framework.UpdateSuspender
import com.scichart.core.model.DoubleValues
import com.scichart.core.model.IntegerValues
import com.scichart.core.utility.Dispatcher
import com.scichart.data.model.DoubleRange
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil
import java.util.Collections


@Composable
fun Chart(navController: NavHostController) {
    SciChartSurfaceView()
}


@Composable
fun SciChartSurfaceView() {
    val webSocket: SocketService by lazy { SocketService() }
    webSocket.openConnection()

    val chartViewModel: ChartViewModel = viewModel()

    val twoEcgLineData = DoubleValues()
    val twoEcgLineDataSeries =
        XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType).apply {
            append(xValues, yValues)
        }

    val isrcLineData = IntegerValues()
    val isrcLineDataSeries =
        XyDataSeries(Int::class.javaObjectType, Int::class.javaObjectType).apply {
            append(xValues, yValues)
        }

    val ecgLineData = DoubleValues()
    val ecgLineDataSeries =
        XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType).apply {
            append(xValues, yValues)
        }

    val icgLineData = DoubleValues()
    val icgLineDataSeries =
        XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType).apply {
            append(xValues, yValues)
        }

    val temperatureLineData = DoubleValues()
    val temperateLineDataSeries =
        XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType).apply {
            append(xValues, yValues)
        }

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

                twoEcgLineDataSeries.seriesName = "2ECG"
                isrcLineDataSeries.seriesName = "ISRC"
                ecgLineDataSeries.seriesName =  "ECG"
                icgLineDataSeries.seriesName = "ICG"
                temperateLineDataSeries.seriesName = "T"

                // How much it will show on the screen
                twoEcgLineDataSeries.fifoCapacity = 5000
                isrcLineDataSeries.fifoCapacity = 5000
                ecgLineDataSeries.fifoCapacity = 5000
                icgLineDataSeries.fifoCapacity = 5000
                temperateLineDataSeries.fifoCapacity = 5000

                yAxis.growBy = DoubleRange(0.3, 0.3)

                val xValues = IntegerValues()

                // Append data to initialise the data series
                twoEcgLineDataSeries.append(xValues, twoEcgLineData)
                isrcLineDataSeries.append(xValues, isrcLineData)
                ecgLineDataSeries.append(xValues, ecgLineData)
                icgLineDataSeries.append(xValues, icgLineData)
                temperateLineDataSeries.append(xValues, temperatureLineData)

                // Type of line
                val twoEcgLineSeries: IRenderableSeries = FastLineRenderableSeries()
                twoEcgLineSeries.dataSeries = twoEcgLineDataSeries

                val isrcLineSeries: IRenderableSeries = FastLineRenderableSeries()
                isrcLineSeries.dataSeries = isrcLineDataSeries

                val ecgLineSeries: IRenderableSeries = FastLineRenderableSeries()
                ecgLineSeries.dataSeries = ecgLineDataSeries

                val icgLineSeries: IRenderableSeries = FastLineRenderableSeries()
                icgLineSeries.dataSeries = icgLineDataSeries

                val temperatureLineSeries: IRenderableSeries = FastLineRenderableSeries()
                temperatureLineSeries.dataSeries = temperateLineDataSeries

//
//                val twoEcgData = XyDataSeries(
//                    Double::class.javaObjectType,
//                    Double::class.javaObjectType
//                )
//
//                val isrcData = XyDataSeries( // TODO: Maybe Integer
//                    Double::class.javaObjectType,
//                    Double::class.javaObjectType
//                )
//
//                val ecgData = XyDataSeries(
//                    Double::class.javaObjectType,
//                    Double::class.javaObjectType
//                )
//
//                val icgData = XyDataSeries(
//                    Double::class.javaObjectType,
//                    Double::class.javaObjectType
//                )
//
//                val temperatureData = XyDataSeries(
//                    Double::class.javaObjectType,
//                    Double::class.javaObjectType
//                )

//                for (i in 0 until 100) {
//                    val x = i.toDouble()
//                    val y = kotlin.math.sin(x * 0.2)
//                    twoEcgData.append(x, y)
//                }
//
//                for (i in 0 until 100) {
//                    val x = i.toDouble()
//                    val y = kotlin.math.sin(x * 0.9)
//                    isrcData.append(x, y)
//                }
//
//                for (i in 0 until 100) {
//                    val x = i.toDouble()
//                    val y = kotlin.math.sin(x * 0.2)
//                    ecgData.append(x, y)
//                }
//
//                for (i in 0 until 100) {
//                    val x = i.toDouble()
//                    val y = kotlin.math.sin(x * 0.2)
//                    icgData.append(x, y)
//                }
//
//                for (i in 0 until 100) {
//                    val x = i.toDouble()
//                    val y = kotlin.math.sin(x * 0.2)
//                    temperatureData.append(x, y)
//                }
//
//                twoEcgLineSeries.apply {
//                    dataSeries = twoEcgData
//                    strokeStyle = SolidPenStyle(ColorUtil.Green, true, 5f, null)
//                }
//
//                isrcLineSeries.apply {
//                    dataSeries = isrcData
//                    strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
//                }
//
//                ecgLineSeries.apply {
//                    dataSeries = ecgData
//                    strokeStyle = SolidPenStyle(ColorUtil.LimeGreen, true, 5f, null)
//                }
//                icgLineSeries.apply {
//                    dataSeries = icgData
//                    strokeStyle = SolidPenStyle(ColorUtil.Yellow, true, 5f, null)
//                }
//
//                temperatureLineSeries.apply {
//                    dataSeries = temperatureData
//                    strokeStyle = SolidPenStyle(ColorUtil.Red, true, 5f, null)
//                }

                // Color of the line
                twoEcgLineSeries.strokeStyle = SolidPenStyle(ColorUtil.Green, true, 5f, null)
                isrcLineSeries.strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
                ecgLineSeries.strokeStyle = SolidPenStyle(ColorUtil.LimeGreen, true, 5f, null)
                icgLineSeries.strokeStyle = SolidPenStyle(ColorUtil.Yellow, true, 5f, null)
                temperatureLineSeries.strokeStyle = SolidPenStyle(ColorUtil.Red, true, 5f, null)


                // Legend with all the data from the lines
                val legendModifier = LegendModifier(context)
                legendModifier.setOrientation(Orientation.VERTICAL)
                legendModifier.setLegendPosition(Gravity.START, 0, 0, 0, 10)

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
    LaunchedEffect(Unit) {
        chartViewModel.sectionAMeasurements.observe() {

        }
//        chartViewModel.sectionAMeasurements.observeForever { data ->
//            // Loop through the properties in an 'A' section
//            data.values.forEach { section ->
//                // Append the values to the chart
//                // if the last timestamp on the x-axis is greater than the timestamp of this section, skip this section
//                // in the graph
//                if (twoEcgLineDataSeries.xMax <= section.tickCount && isrcLineDataSeries.xMax <= section.tickCount) {
//
//                    twoEcgLineDataSeries.append(section.tickCount, section.twoEcg)
//                    isrcLineDataSeries.append(section.tickCount, section.isrc)
//                    ecgLineDataSeries.append(section.tickCount, section.ecg)
//                    icgLineDataSeries.append(section.tickCount, section.icg)
//                    temperateLineDataSeries.append(section.tickCount, section.temperature)
//
//                }
//            }
//        }
    }
}