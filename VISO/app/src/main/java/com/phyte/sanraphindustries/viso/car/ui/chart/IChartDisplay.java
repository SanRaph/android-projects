package com.phyte.sanraphindustries.viso.car.ui.chart;

import com.github.mikephil.charting.charts.Chart;

import java.util.List;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.chart
 *
 * File Description: TODO
 * Creator: Write a classic
 * Created: 2017/3/28 18:29
 */
public interface IChartDisplay<C extends Chart, T, V> {
    int    MAX_VISIBLE_VALUE_COUNT = 30;
    int    MINIMUM_VALUE           = 0;
    int    TEXT_SIZE               = 8;
    int    LARGE_TEXT_SIZE         = 16;
    int    LARGE_AXIS_SIZE         = 12;
    int    QUALITY                 = 100;
    String EMPTY_LABEL             = "";

    void init(C chart, boolean touchEnable);

    T convert(List<V> list);

    void display(C chart, T t);

    void animationDisplay(C chart, T t, int duration);
}
