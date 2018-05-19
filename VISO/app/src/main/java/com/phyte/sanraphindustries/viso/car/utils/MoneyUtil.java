package com.phyte.sanraphindustries.viso.car.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.math.BigDecimal;

/**
 * File Description: High-precision data calculation tools
 * Creator: Write a classic
 * Creation time: 2015/11/3 17:26
 */
@SuppressWarnings({"unused", "WeakerAccess"}) public class MoneyUtil {

    // Default division accuracy
    private static final int DEFAULT_SCALE         = 2;
    private static final int DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    private MoneyUtil() { }

    private BigDecimal mBigDecimal;

    /**
     *
     Object to BigDecimal
     */
    public static BigDecimal objectToBigDecimal(@NonNull Object number) {
        BigDecimal value;
        if (number instanceof Integer) {
            value = new BigDecimal(Integer.toString((Integer)number));
        } else if (number instanceof Float) {
            value = new BigDecimal(Float.toString((Float)number));
        } else if (number instanceof Double) {
            value = new BigDecimal(Double.toString((Double)number));
        } else if (number instanceof Short) {
            value = new BigDecimal(Short.toString((Short)number));
        } else if (number instanceof Long) {
            value = new BigDecimal(Long.toString((Long)number));
        } else if (number instanceof String) {
            value = new BigDecimal(number.toString());
        } else { //Unknown type
            throw new IllegalArgumentException("unknown type!");
        }
        return value;
    }

    /**
     *Remove 0 after invalid decimal point
     */
    public static String replace(Number number) {
        return replace(String.valueOf(number));
    }

    /**
     *Remove 0 after invalid decimal point
     */
    public static String replace(String number) {
        if (TextUtils.isEmpty(number)) { return "0"; }
        if (number.indexOf(".") > 0) {
            number = number.replaceAll("0+?$", ""); //Remove the useless zero behind
            number = number.replaceAll("[.]$", ""); //If the decimal point is all zero, remove the decimal point
        }
        return number;
    }

    /**
     *Remove 0 after invalid decimal point
     */
    public static String replace(String number, int scale) {
        if (TextUtils.isEmpty(number)) { return "0"; }
        number = newInstance(number).round(scale).create().toString();
        if (number.indexOf(".") > 0) {
            number = number.replaceAll("0+?$", ""); //Remove the useless zero behind
            number = number.replaceAll("[.]$", ""); // If the decimal point is all zero, remove the decimal point
        }
        return number;
    }

    /**
     *Remove 0 after invalid decimal point
     */
    public static String replace(Number number, int scale) {
        return replace(String.valueOf(number), scale);
    }

    public static MoneyUtil newInstance(@NonNull Object number) {
        MoneyUtil moneyUtil = new MoneyUtil();
        moneyUtil.mBigDecimal = objectToBigDecimal(number);
        return moneyUtil;
    }

    /**
     * Less
     */
    public MoneyUtil subtract(@NonNull Object number) {
        mBigDecimal = mBigDecimal.subtract(objectToBigDecimal(number));
        return this;
    }

    /**
     * 乘
     */
    public MoneyUtil multiply(@NonNull Object number) {
        mBigDecimal = mBigDecimal.multiply(objectToBigDecimal(number));
        return this;
    }

    /**
     *
     except
     */
    public MoneyUtil divide(@NonNull Object number) {
        return divide(number, DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
    }

    /**
     *
     except
     *
     * @param scale accurate to a few decimal places
     */
    public MoneyUtil divide(@NonNull Object number, int scale) {
        return divide(number, scale, DEFAULT_ROUNDING_MODE);
    }

    /**
     * In addition to
     *
     * @param scale accurate to a few decimal places
     * @param roundingMode precise mode
     * @see BigDecimal
     */
    public MoneyUtil divide(@NonNull Object number, int scale, int roundingMode) {
        mBigDecimal = mBigDecimal.divide(objectToBigDecimal(number), scale, roundingMode);
        return this;
    }

    /**
     * Add
     */
    public MoneyUtil add(@NonNull Object number) {
        mBigDecimal = mBigDecimal.add(objectToBigDecimal(number));
        return this;
    }

    /**
     * rounding
     *
     * @param scale accurate to a few decimal places
     */
    public MoneyUtil round(int scale) {
        return round(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *rounding
     *
     * @param scale accurate to a few decimal places
     * @param roundingMode precise mode
     * @see BigDecimal
     */
    public MoneyUtil round(int scale, int roundingMode) {
        if (scale >= 0) {
            mBigDecimal = mBigDecimal.divide(new BigDecimal("1"), scale, roundingMode);
        }
        return this;
    }

    public BigDecimal create() {
        return mBigDecimal;
    }


}
