package com.example.vu.ui.screens.chart

import android.view.Gravity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vu.data.viewmodel.ChartViewModel
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.*
import com.scichart.charting.themes.ThemeManager
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries
import com.scichart.core.annotations.Orientation
import com.scichart.core.framework.UpdateSuspender
import com.scichart.core.model.DoubleValues
import com.scichart.core.model.IntegerValues
import com.scichart.data.model.DoubleRange
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil
import java.util.*


@Composable
fun Chart(chartViewModel: ChartViewModel) {

    val context = LocalContext.current
    val sciChartSurface = SciChartSurface(context)

    val sectionAMeasurements by chartViewModel.sectionAMeasurements.observeAsState()

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
            .padding(bottom = 55.dp)
    ) {
        AndroidView(
            factory = { context ->
                // Create Android View with SciChartSurface
                val sciChartSurfaceView = SciChartSurface(context)

                // Configure SciChartSurface with chart data and options here
                val xAxis: IAxis = NumericAxis(context)
                val yAxis: IAxis = NumericAxis(context)

                twoEcgLineDataSeries.seriesName = "2ECG"
                isrcLineDataSeries.seriesName = "ISRC"
                ecgLineDataSeries.seriesName = "ECG"
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

                // Add all those data and modifiers
                UpdateSuspender.using(sciChartSurfaceView) {
                    Collections.addAll(
                        sciChartSurfaceView.renderableSeries,
                        twoEcgLineSeries,
                        ecgLineSeries,
                        isrcLineSeries,
                        icgLineSeries,
                        temperatureLineSeries
                    )
                    Collections.addAll(
                        sciChartSurfaceView.chartModifiers,
                        PinchZoomModifier(),
                        ZoomPanModifier(),
                        ZoomExtentsModifier(),
                    )
                    Collections.addAll(sciChartSurfaceView.chartModifiers, legendModifier)
                    Collections.addAll(sciChartSurfaceView.chartModifiers, RolloverModifier())
                }

                // Add the x and y axis to the chart
                UpdateSuspender.using(sciChartSurfaceView) {
                    Collections.addAll(sciChartSurfaceView.xAxes, xAxis)
                    Collections.addAll(sciChartSurfaceView.yAxes, yAxis)
                    sciChartSurfaceView.zoomExtentsX()
                    sciChartSurface.zoomExtentsY()
                }
                sciChartSurfaceView // return the SciChartSurface instance
            }, update = {
                sectionAMeasurements?.let { sectionMeasurements ->
                    sectionMeasurements.values.forEach { section ->
                        if (twoEcgLineDataSeries.xMax > section.tickCount || isrcLineDataSeries.xMax > section.tickCount) {
                            return@let
                        }

                        twoEcgLineDataSeries.append(section.tickCount, section.twoEcg)
                        isrcLineDataSeries.append(section.tickCount, section.isrc)
                        ecgLineDataSeries.append(section.tickCount, section.ecg)
                        icgLineDataSeries.append(section.tickCount, section.icg)
                        temperateLineDataSeries.append(section.tickCount, section.temperature)

                        sciChartSurface.zoomExtentsX()
                        sciChartSurface.zoomExtentsY()
                    }
                }
            }
        )
    }
}