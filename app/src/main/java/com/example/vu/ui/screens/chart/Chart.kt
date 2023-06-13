package com.example.vu.ui.screens.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vu.data.viewmodel.ChartViewModel
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.*
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.AutoRange
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries
import com.scichart.core.framework.UpdateSuspender
import com.scichart.core.model.DoubleValues
import com.scichart.core.model.IntegerValues
import com.scichart.data.model.DoubleRange
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil
import java.util.*


@Composable
fun Chart(chartViewModel: ChartViewModel) {


    Column {
        val tabs = listOf("Custom", "2ECG", "ISRC", "ECG", "ICG", "TEMP", "Tab 6", "Tab 7")
        val selectedTabIndex = remember { mutableStateOf(0) }

        HorizontalScrollableTabs(tabs = tabs, selectedTabIndex = selectedTabIndex)

        // Rest of your screen content based on the selected tab
        when (selectedTabIndex.value) {
            0 -> {
                Text(text = "Preferences screen")
            }
            1 -> {
                ChartType(chartViewModel, lineName = "2ECG")
            }
            2 -> {
                ChartType(chartViewModel, lineName = "ISRC")
            }
            3 -> {
                ChartType(chartViewModel, lineName = "ECG")
            }
            4 -> {
                ChartType(chartViewModel, lineName = "ICG")
            }
            5 -> {
                ChartType(chartViewModel, lineName = "T")
            }
            6 -> {
                // Content for Tab 6
            }
            7 -> {
                // Content for Tab 7
            }
        }


    }
}

@Composable
fun ChartType(chartViewModel: ChartViewModel, lineName: String) {
    val context = LocalContext.current
    val sciChartSurface = SciChartSurface(context)

    val sectionAMeasurements by chartViewModel.sectionAMeasurements.observeAsState()

    val lineData = DoubleValues()
    val lineDataSeries = XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType)

    val xValues = IntegerValues()

    // Append data to initialize the data series
    lineDataSeries.append(xValues, lineData)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 55.dp)
    ) {
        AndroidView(
            factory = { context ->
                // Create Android View with SciChartSurface
                val sciChartSurfaceView = SciChartSurface(context)
                sciChartSurface.setBackgroundColor(ColorUtil.White)

                // Configure SciChartSurface with chart data and options here
                val xAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.3, 0.3)
                    autoRange = AutoRange.Always
                }
                val yAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.3, 0.3)
                    autoRange = AutoRange.Always
                }

                lineDataSeries.seriesName = lineName

                // How much it will show on the screen
                lineDataSeries.fifoCapacity = 5000

                // Append data to initialise the data series
                lineDataSeries.append(xValues, lineData)

                // Type of line

                val lineSeries: IRenderableSeries = FastLineRenderableSeries().apply {
                    dataSeries = lineDataSeries
                    strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
                }

                // Set the stroke style and color for the line

                // Add all those data and modifiers
                UpdateSuspender.using(sciChartSurfaceView) {
                    Collections.addAll(
                        sciChartSurfaceView.renderableSeries,
                        lineSeries
                    )
                    Collections.addAll(
                        sciChartSurfaceView.chartModifiers,
                        PinchZoomModifier(),
                        ZoomPanModifier(),
                        ZoomExtentsModifier(),
                    )
                    sciChartSurfaceView.chartModifiers.add(RolloverModifier())
                }

                // Add the x and y axis to the chart
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.xAxes.add(xAxis)
                    sciChartSurfaceView.yAxes.add(yAxis)
                }

                sciChartSurfaceView // return the SciChartSurface instance
            }, update = {
                sectionAMeasurements?.let { sectionMeasurements ->
                    sectionMeasurements.values.forEach { section ->
                        if (lineDataSeries.xMax > section.tickCount) {
                            return@let
                        }
                        when (lineName) {
                            "2ECG" -> lineDataSeries.append(section.tickCount, section.twoEcg)
                            "ISRC" -> lineDataSeries.append(
                                section.tickCount,
                                section.isrc.toDouble()
                            )
                            "ECG" -> lineDataSeries.append(section.tickCount, section.ecg)
                            "ICG" -> lineDataSeries.append(section.tickCount, section.icg)
                            "T" -> lineDataSeries.append(section.tickCount, section.temperature)
                        }

//                        UpdateSuspender.using(sciChartSurface) {
//                            lineDataSeries.updateRangeYAt(0, lineData)
//
//                            // zoom series to fit viewport size into X-Axis direction
//                            sciChartSurface.zoomExtentsX()
//                        }
                    }
                }
            }
        )
    }
}

@Composable
fun AllCharts(chartViewModel: ChartViewModel) {
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

                //TODO: See if this is needed
                /*// Legend with all the data from the lines
                val legendModifier = LegendModifier(context)
                legendModifier.setOrientation(Orientation.VERTICAL)
                legendModifier.setLegendPosition(Gravity.START, 0, 0, 0, 10)*/

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
                    //TODO: See if this is needed
                    /*Collections.addAll(sciChartSurfaceView.chartModifiers, legendModifier)*/
                    Collections.addAll(sciChartSurfaceView.chartModifiers, RolloverModifier())
                }

                // Add the x and y axis to the chart
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.xAxes.add(xAxis)
                    sciChartSurfaceView.yAxes.add(yAxis)
                    sciChartSurfaceView.zoomExtentsX()
                    sciChartSurfaceView.zoomExtentsY()
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

@Composable
fun HorizontalScrollableTabs(
    tabs: List<String>,
    selectedTabIndex: MutableState<Int>
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth()) {
        // Scrollable row for the tabs
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .clip(RoundedCornerShape(ZeroCornerSize))
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = title, color = Color.White)
                }
            }
        }

        // Indicator line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
                .clip(RoundedCornerShape(ZeroCornerSize))
                .background(MaterialTheme.colors.secondary)
                .align(Alignment.Start)
                .widthIn(max = scrollState.maxValue.dp)
                .offset(x = with(LocalDensity.current) { scrollState.value.toDp() })
        )
    }
}