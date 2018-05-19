package com.phyte.sanraphindustries.viso.car.entity;

import android.support.annotation.IntDef;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.entity
 *
 * File Description: Chart Type
 * Creator: Write a classic
 * Created: 2017/3/25 11:41
 */
@IntDef({ChartType.BAR_CHART, ChartType.PIE_CHART, ChartType.LINE_CHART})
public @interface ChartType {
    int BAR_CHART  = 1;
    int PIE_CHART  = 2;
    int LINE_CHART = 3;
}
