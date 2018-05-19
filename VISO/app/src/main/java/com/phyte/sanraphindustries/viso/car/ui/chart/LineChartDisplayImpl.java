package com.phyte.sanraphindustries.viso.car.ui.chart;

import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.entity.FuelConsumption;
import com.phyte.sanraphindustries.viso.car.utils.DataUtil;
import com.phyte.sanraphindustries.viso.car.utils.MoneyUtil;
import com.phyte.sanraphindustries.viso.car.utils.Util;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.ui.chart
 *
 * File Description: TODO
 * Creator: Write a classic
 * Created: 2017/3/28 19:55
 */
public class LineChartDisplayImpl extends BaseChartDisplayImpl<LineChart, LineChartDisplayImpl.LineChartData>{

    @Override public void init(LineChart chart, boolean touchEnable) {
        super.init(chart, touchEnable);
        //Above this value, no value is displayed
        chart.setMaxVisibleValueCount(MAX_VISIBLE_VALUE_COUNT * 2);
        chart.setDrawGridBackground(false);
        if (touchEnable) {
            // enable scaling and dragging
            // lineChart.setDragEnabled(true);
            // lineChart.setScaleEnabled(true);
            // lineChart.setHighlightPerDragEnabled(true);
            // if disabled, scaling can be done on x- and y-axis separately
            chart.setPinchZoom(true);
        }

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(1);
        leftAxis.setTextSize(mAxisSize);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setTextColor(Util.getColor(mAppContext, R.color.gray_dark));
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(mAxisSize);
        xAxis.setAxisMinimum(MINIMUM_VALUE);
        xAxis.setTextColor(Util.getColor(mAppContext, R.color.gray_dark));

        chart.getLegend().setForm(Legend.LegendForm.LINE);
        chart.getLegend().setTextSize(mTextSize);
        chart.getLegend().setTextColor(Util.getColor(mAppContext, R.color.gray_dark));
    }

    @Override public LineChartData convert(List<ConsumerDetail> list) {
        if (DataUtil.isEmpty(list)) { return null; }
        LineChartData lineChartData = new LineChartData();
        lineChartData.fuelConsumptions = new ArrayList<>();
        final int size = list.size() - 1;
        for (int i = 0; i < size; i++) {
            ConsumerDetail startItem = list.get(i);
            ConsumerDetail endItem = list.get(i + 1);
            long mileage = endItem.getCurrentMileage() - startItem.getCurrentMileage();
            mileage = mileage > 0L ? mileage : 0L;
            float money = mileage == 0L ? 0F :
                    MoneyUtil.newInstance(startItem.getMoney()).multiply(100).divide(mileage).create().floatValue();
            final float oilMass = startItem.getUnitPrice() == 0F ? 0F :
                    MoneyUtil.newInstance(money).divide(startItem.getUnitPrice()).create().floatValue();

            FuelConsumption item = new FuelConsumption(mileage,
                                                       Float.valueOf(MoneyUtil.replace(money)),
                                                       Float.valueOf(MoneyUtil.replace(oilMass)));
            lineChartData.fuelConsumptions.add(item);
            if (null == lineChartData.minFuelConsumption || item.getMoney() < lineChartData.minFuelConsumption.getMoney()) {
                lineChartData.minFuelConsumption = item;
            }

            if (null == lineChartData.maxFuelConsumption || item.getMoney() > lineChartData.maxFuelConsumption.getMoney()) {
                lineChartData.maxFuelConsumption = item;
            }
        }
        lineChartData.lineData = convertLineData(lineChartData.fuelConsumptions);
        return lineChartData;
    }

    @Override ChartData getChartData(LineChartData lineChartData) {
        return null == lineChartData ? null : lineChartData.lineData;
    }

    public static class LineChartData {
        public LineData              lineData;
        public FuelConsumption       minFuelConsumption;
        public FuelConsumption       maxFuelConsumption;
        public List<FuelConsumption> fuelConsumptions;
    }

    /**Fuel consumption curve data display*/
    private static final IValueFormatter OIL_MESS_FORMATTER = new IValueFormatter() {
        @Override public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                                  ViewPortHandler viewPortHandler) {
            return MoneyUtil.replace(MoneyUtil.newInstance(value).round(2).create());
        }
    };

    private LineData convertLineData(List<FuelConsumption> list){
        if(DataUtil.isEmpty(list)){
            return null;
        }

        ArrayList<Entry> moneyValues = new ArrayList<>();
        ArrayList<Entry> oilMessValues = new ArrayList<>();
        final int size = list.size();
        for (int i = 0; i < size; i++) {
            moneyValues.add(new Entry(i, list.get(i).getMoney()));
            oilMessValues.add(new Entry(i, list.get(i).getOilMass()));
        }

        LineDataSet moneySet = new LineDataSet(moneyValues,
                                               Util.getString(mAppContext, R.string.chart_fuel_consumption_money));
        applyLineDataSetStyle(moneySet, Util.getColor(mAppContext, R.color.colorAccent));

        LineDataSet oilMessSet = new LineDataSet(oilMessValues,
                                                 Util.getString(mAppContext, R.string.chart_fuel_consumption));
        applyLineDataSetStyle(oilMessSet, Util.getColor(mAppContext, R.color.blue));

        return new LineData(moneySet, oilMessSet);
    }

    private void applyLineDataSetStyle(LineDataSet lineDataSet, int color) {
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setValueTextSize(mTextSize);
        lineDataSet.setValueFormatter(OIL_MESS_FORMATTER);

        //moneySet.setCircleRadius(5f); //Dot radius
        //moneySet.setDrawCircleHole(false); //Is the dot hollow?
        //moneySet.setCircleHoleRadius(3f); //Hollow radius
        //moneySet.setHighlightEnabled(true); //Highlighted
        //moneySet.enableDashedLine(10f, 5f, 0f); // Enable dotted line
        //moneySet.enableDashedHighlightLine(10f, 5f, 0f); //Enable highlighted line
    }
}
