package com.phyte.sanraphindustries.viso.car.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.EditText;

import com.phyte.sanraphindustries.viso.R;
import com.phyte.sanraphindustries.viso.car.consts.Consts;

import java.util.Locale;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.utils
 *
 * File Description:TODO
 * Created by: Continued to write classic
 * Created on: 16/6/4 8:51 PM
 */
public final class Util {

    public static int getColorByType(int type){
        switch (type){
            case Consts.TYPE_FUEL: //Fuel costs
                return R.color.chartreuse_light;
            case Consts.TYPE_PARKING: //parking fee
                return R.color.blue_light;
            case Consts.TYPE_REPAIR: //Maintenance fees
                return R.color.orange_light;
            case Consts.TYPE_ROAD_TOLL: //Tolls
                return R.color.saffron_light;
            case Consts.TYPE_PREMIUM: // insurance
                return R.color.pale_red;
            case Consts.TYPE_MAINTENANCE: // Car maintenance fee
                return R.color.green_light;
            case Consts.TYPE_EXAMINATION: //Annual automobile inspection fee
                return R.color.purple_light;
            case Consts.TYPE_TRAFFIC_VIOLATION: //Traffic violation fine
                return R.color.sienna_light;
            default: // other
                return R.color.mediumorchid_light;
        }
    }

    public static int getBackgroundByType(int type){
        switch (type){
            case Consts.TYPE_FUEL:
                return R.drawable.bg_chartreuse_light;
            case Consts.TYPE_PARKING:
                return R.drawable.bg_blue_light;
            case Consts.TYPE_REPAIR:
                return R.drawable.bg_orange_light;
            case Consts.TYPE_ROAD_TOLL:
                return R.drawable.bg_saffron_light;
            case Consts.TYPE_PREMIUM:
                return R.drawable.bg_pale_red;
            case Consts.TYPE_MAINTENANCE:
                return R.drawable.bg_green_light;
            case Consts.TYPE_EXAMINATION:
                return R.drawable.bg_purple_light;
            case Consts.TYPE_TRAFFIC_VIOLATION:
                return R.drawable.bg_sienna_light;
            default:
                return R.drawable.bg_mediumorchid_light;
        }
    }

    public static int getIconByType(int type){
        switch (type){
            case Consts.TYPE_FUEL:
                return R.drawable.ic_fuel;
            case Consts.TYPE_PARKING:
                return R.drawable.ic_parking;
            case Consts.TYPE_REPAIR:
                return R.drawable.ic_repair;
            case Consts.TYPE_ROAD_TOLL:
                return R.drawable.ic_road_toll;
            case Consts.TYPE_PREMIUM:
                return R.drawable.ic_premium;
            case Consts.TYPE_MAINTENANCE:
                return R.drawable.ic_maintenance;
            case Consts.TYPE_EXAMINATION:
                return R.drawable.ic_examination;
            case Consts.TYPE_TRAFFIC_VIOLATION:
                return R.drawable.ic_traffic_violation;
            default:
                return R.drawable.ic_other;
        }
    }

    public static String format(String format, Number number){
        // return String.format(Locale.US, format, MoneyUtil.replace(number)) ;
        return String.format(Locale.US, format,
                             MoneyUtil.replace(MoneyUtil.newInstance(number).round(2).create())) ;
    }
    public static String formatMoney(float money){
        return format(Consts.FORMAT_MONEY, money);
    }
    public static String formatRMB(float money){
        return format(Consts.FORMAT_RMB, money);
    }
    public static String formatOilMess(float oilMess){
        return format(Consts.FORMAT_OIL_MESS, oilMess);
    }
    public static String formatPercentage(float percentage){
        return MoneyUtil.replace(MoneyUtil.newInstance(percentage).round(2).create()) + "%";
    }
    public static String formatPercentage(float value, float totalValue){
        return formatPercentage(value*100/totalValue);
    }

    public static void setText(EditText editText, String value){
        editText.setText(MoneyUtil.replace(value));
        editText.setSelection(editText.getText().length());
    }
    public static void setText(EditText editText, Number value){
        setText(editText, String.valueOf(value));
    }
    public static void setFocusable(EditText editText){
        editText.setFocusable(true);
        editText.requestFocus();
    }

    public static String createImageName() {
        //noinspection StringBufferReplaceableByString
        return new StringBuilder("CarAssistant_")
                .append(DateUtil.formatDate("yyyy-MM-dd_HH:mm:ss", System.currentTimeMillis()))
                .append(".png")
                .toString();
    }

    public static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }

    public static int getColor(Context context, int resId){
        //noinspection deprecation
        return context.getResources().getColor(resId);
    }

    private static int[] sColorTemplate;
    public static int[] getColorTemplate(Context context){
        if(null == sColorTemplate){
            sColorTemplate = new int[]{
                    getColor(context,R.color.blue_light),
                    getColor(context,R.color.pale_red),
                    getColor(context,R.color.chartreuse_light),
                    getColor(context,R.color.saffron_light),
                    getColor(context,R.color.mediumorchid_light),
                    getColor(context,R.color.green_light),
                    getColor(context,R.color.orange_light),
                    getColor(context,R.color.sienna_light),
                    getColor(context,R.color.purple_light),
                    getColor(context,R.color.pink_light)
            };
        }
        return sColorTemplate;
    }

    public static int dp2px(@NonNull Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                                               context.getResources().getDisplayMetrics());
    }

    // public static int getRandom(int maxValue){
    //     return (int)(Math.random()*maxValue);
    // }
    // public static SparseArray<Float> sortSparseArray(SparseArray<Float> sparseArray){
    //     List<Float> values = new ArrayList<>();
    //     for(int i =0; i < sparseArray.size();i++){
    //         values.add(sparseArray.valueAt(i));
    //     }
    //     // order
    //    // // Collections.sort(values);
    //    // // Reverse
    //     Collections.sort(values, new Comparator<Float>() {
    //         @Override public int compare(Float o1, Float o2) {
    //             return o2.compareTo(o1);
    //         }
    //     });
    //     SparseArray<Float> tempArray = new SparseArray<>();
    //     for (Float item : values) {
    //         tempArray.put(sparseArray.keyAt(sparseArray.indexOfValue(item)), item);
    //         // XLog.d(sparseArray.keyAt(sparseArray.indexOfValue(item)) + " " + item);
    //     }
    //     sparseArray.clear();
    //     return tempArray;
    // }
}
