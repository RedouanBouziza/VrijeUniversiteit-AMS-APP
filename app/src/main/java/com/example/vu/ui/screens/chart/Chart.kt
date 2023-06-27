package com.example.vu.ui.screens.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vu.data.viewmodel.ChartViewModel
import com.scichart.charting.R
import com.scichart.charting.model.dataSeries.XyDataSeries
import com.scichart.charting.modifiers.*
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.axes.AutoRange
import com.scichart.charting.visuals.axes.IAxis
import com.scichart.charting.visuals.axes.NumericAxis
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries
import com.scichart.charting.visuals.renderableSeries.IRenderableSeries
import com.scichart.core.framework.UpdateSuspender
import com.scichart.data.model.DoubleRange
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil


@Composable
fun Chart(chartViewModel: ChartViewModel) {
    Column {
        val tabs = listOf("ECG", "RES", "GYRO", "ACC", "PRESS")
        val selectedTabIndex = remember { mutableStateOf(0) }
//        HorizontalScrollableTabs(tabs = tabs, selectedTabIndex = selectedTabIndex)

        if (selectedTabIndex.value != 5) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
            ) {
                itemsIndexed(tabs) { index, title ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        // Rest of your screen content based on the selected tab
        when (selectedTabIndex.value) {
            0 -> {
                ChartType(chartViewModel, lineName = "ECG")
            }
            1 -> {
                ChartType(chartViewModel, lineName = "RES")
            }
            2 -> {
                ChartType(chartViewModel, lineName = "GYRO")
            }
            3 -> {
                ChartType(chartViewModel, lineName = "ACC")
            }
            4 -> {
                ChartType(chartViewModel, lineName = "PRES")
            }
        }

    }
}

@Composable
fun ChartType(chartViewModel: ChartViewModel, lineName: String) {
    val sectionAMeasurements by chartViewModel.sectionAMeasurements.observeAsState()
    val lineDataSeries = remember { XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType) }
    val chartModifiers = remember {
        mutableListOf<IChartModifier>(
            PinchZoomModifier(),
            ZoomPanModifier(),
            ZoomExtentsModifier(),
            RolloverModifier()
        )
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
                sciChartSurfaceView.theme = com.scichart.charting.R.style.SciChart_ChromeStyle

                // Configure SciChartSurface with chart data and options here
                val xAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.0, 0.0)
                    autoRange = AutoRange.Always
                }
                val yAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.0, 0.0)
                    autoRange = AutoRange.Always
                }

                lineDataSeries.seriesName = lineName

                // How much it will show on the screen
                lineDataSeries.fifoCapacity = 5000

                val lineSeries: IRenderableSeries = FastLineRenderableSeries().apply {
                    dataSeries = lineDataSeries
                    strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
                }

                // Add all those data and modifiers
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.renderableSeries.add(lineSeries)
                    chartModifiers.forEach { sciChartSurfaceView.chartModifiers.add(it) }
                }

                // Add the x and y axis to the chart
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.xAxes.add(xAxis)
                    sciChartSurfaceView.yAxes.add(yAxis)
                }

                sciChartSurfaceView // return the SciChartSurface instance
            }
        )

        LaunchedEffect(sectionAMeasurements) {
            sectionAMeasurements?.let { sectionMeasurements ->
                sectionMeasurements.values.forEach { section ->
                    if (lineDataSeries.xMax > section.tickCount) {
                        return@let
                    }
                    when (lineName) {
                        "ECG" -> lineDataSeries.append(section.tickCount, section.ecg)
                        "RES" -> lineDataSeries.append(section.tickCount, section.icg)
                        "GYRO" -> lineDataSeries.append(section.tickCount, section.temperature)
                        "ACC" -> lineDataSeries.append(section.tickCount, section.temperature)
                        "PRES" -> lineDataSeries.append(section.tickCount, section.temperature)
                    }
                }
            }
        }
    }
}

@Composable
fun ChartTypeHome(chartViewModel: ChartViewModel, lineName: String) {
    val sectionAMeasurements by chartViewModel.sectionAMeasurements.observeAsState()
    val lineDataSeries = remember { XyDataSeries(Int::class.javaObjectType, Double::class.javaObjectType) }
    val chartModifiers = remember {
        mutableListOf<IChartModifier>(
            PinchZoomModifier(),
            ZoomPanModifier(),
            ZoomExtentsModifier(),
            RolloverModifier()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AndroidView(
            factory = { context ->
                // Create Android View with SciChartSurface
                val sciChartSurfaceView = SciChartSurface(context)
                sciChartSurfaceView.theme = R.style.SciChart_ChromeStyle

                // Configure SciChartSurface with chart data and options here
                val xAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.0, 0.0)
                    autoRange = AutoRange.Always
                }
                val yAxis: IAxis = NumericAxis(context).apply {
                    growBy = DoubleRange(0.0, 0.0)
                    autoRange = AutoRange.Always
                }

                lineDataSeries.seriesName = lineName

                // How much it will show on the screen
                lineDataSeries.fifoCapacity = 5000

                val lineSeries: IRenderableSeries = FastLineRenderableSeries().apply {
                    dataSeries = lineDataSeries
                    strokeStyle = SolidPenStyle(ColorUtil.Blue, true, 5f, null)
                }

                // Add all those data and modifiers
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.renderableSeries.add(lineSeries)
                    chartModifiers.forEach { sciChartSurfaceView.chartModifiers.add(it) }
                }

                // Add the x and y axis to the chart
                UpdateSuspender.using(sciChartSurfaceView) {
                    sciChartSurfaceView.xAxes.add(xAxis)
                    sciChartSurfaceView.yAxes.add(yAxis)
                }

                sciChartSurfaceView // return the SciChartSurface instance
            }
        )

        LaunchedEffect(sectionAMeasurements) {
            sectionAMeasurements?.let { sectionMeasurements ->
                sectionMeasurements.values.forEach { section ->
                    if (lineDataSeries.xMax > section.tickCount) {
                        return@let
                    }
                    when (lineName) {
                        "ECG" -> lineDataSeries.append(section.tickCount, section.ecg)
                        "RES" -> lineDataSeries.append(section.tickCount, section.icg)
                        "GYRO" -> lineDataSeries.append(section.tickCount, section.temperature)
                        "ACC" -> lineDataSeries.append(section.tickCount, section.temperature)
                        "PRES" -> lineDataSeries.append(section.tickCount, section.temperature)
                    }
                }
            }
        }
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