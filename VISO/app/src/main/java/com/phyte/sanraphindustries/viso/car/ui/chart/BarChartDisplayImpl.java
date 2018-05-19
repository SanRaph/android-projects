package com.phyte.sanraphindustries.viso.car.ui.chart;

import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.utils.DataUtil;
import com.phyte.sanraphindustries.viso.car.utils.Util;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import java.util.ArrayList;
import java.util.List;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.chart
 *
 * File Description: TODO
 * Creator: Write a classic
 * Created: 2017/3/28 18:35
 */
public class BarChartDisplayImpl extends BaseChartDisplayImpl<BarChart, BarData>{

    @Override public void init(BarChart chart, boolean touchEnable) {
        super.init(chart, touchEnable);
        if (touchEnable) {
            //Drag the chart
            chart.setDragEnabled(true);
            //Turn on or off the zoom for all axes of the chart
            chart.setScaleEnabled(true);
            chart.setPinchZoom(true);
            chart.setDoubleTapToZoomEnabled(false);
            chart.setHighlightFullBarEnabled(false);
        }
        //Above this value, no value is displayed
        chart.setMaxVisibleValueCount(MAX_VISIBLE_VALUE_COUNT);
        chart.setDrawGridBackground(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(MINIMUM_VALUE);
        // Grid lines are drawn in dotted pattern
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setTextSize(mAxisSize);
        leftAxis.setTextColor(Util.getColor(mAppContext, R.color.gray_dark));
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(mAxisSize);
        xAxis.setTextColor(Util.getColor(mAppContext, R.color.gray_dark));

        chart.getLegend().setEnabled(false);
    }

    @Override public BarData convert(List<ConsumerDetail> list) {
        if (!DataUtil.isEmpty(list)) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            final int size = list.size();
            for (int i = 0; i < size; i++) {
                entries.add(new BarEntry(i, list.get(i).getMoney()));
            }
            BarDataSet ds = new BarDataSet(entries, EMPTY_LABEL);
            ds.setValueTextSize(mTextSize);
            ds.setColors(Util.getColorTemplate(mAppContext));
            return new BarData(ds);
        }
        return null;
    }

    @Override ChartData getChartData(BarData barData) {
        return barData;
    }
}
