package com.phyte.sanraphindustries.viso.car.consts;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *Application Name: CarAssistant
 * Package name: com.classic.car.app
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 10:58
 */
public final class Consts {

    public static final String DIR_NAME           = "CarAssistant";
    public static final String APK                = ".apk";
    public static final String AUTHORITIES_SUFFIX = ".provider";

    /** Prevent accidental clicks */
    public static final int    SHIELD_TIME     = 3;

    public static final String[] TYPE_MENUS =
            { "Other", "Fuel cost", "Parking fees", "Maintenance fees", "Tolls", "Insurance", "Car maintenance fee", "Annual automobile inspection fee", "\n" +
                    "Traffic violation fine" };

    public static final int TYPE_OTHER             = 0; //other
    public static final int TYPE_FUEL              = 1; //Fuel costs
    public static final int TYPE_PARKING           = 2; //parking fee
    public static final int TYPE_REPAIR            = 3; //Maintenance fees
    public static final int TYPE_ROAD_TOLL         = 4; //Tolls
    public static final int TYPE_PREMIUM           = 5; //insurance
    public static final int TYPE_MAINTENANCE       = 6; //Car maintenance fee
    public static final int TYPE_EXAMINATION       = 7; //Annual automobile inspection fee
    public static final int TYPE_TRAFFIC_VIOLATION = 8; //Traffic violation fine

    public static final String[] FUEL_MENUS = { "gasoline 89/90", "gasoline 92/93", "gasoline 95/97", "gasoline 0#" };
    //The original No. 90 gasoline, the new standard was changed to 89 gasoline
    public static final int FUEL_GASOLINE_89 = 0;
    //The original 93 petrol, the new standard changed to 92 petrol
    public static final int FUEL_GASOLINE_92 = 1;
    //The original No. 97 gasoline, the new standard was changed to No. 95 gasoline
    public static final int FUEL_GASOLINE_95 = 2;
    public static final int FUEL_DIESEL      = 3; //Diesel 0#

    public static final String FORMAT_MONEY    = "$%s";
    public static final String FORMAT_RMB      = "%sDollars";
    public static final String FORMAT_OIL_MESS = "%sRise";

    public static final String STORAGE_PERMISSIONS_DESCRIBE  = "Applications need to access your storage space for log storage";
    public static final String FEEDBACK_PERMISSIONS_DESCRIBE = "Voice feedback requires voice recording permission";

    public static final ArrayList<Integer> YEARS;
    private static final int MIN_YEAR       = 2014;
    private static final int MIN_YEARS_SIZE = 10;

    public static final String BACKUP_PREFIX = "CarAssistant_";
    public static final String BACKUP_SUFFIX = ".backup";

    static {
        YEARS = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int size = currentYear < MIN_YEAR ? MIN_YEARS_SIZE : (currentYear - MIN_YEAR);
        for (int i = MIN_YEAR; i <= MIN_YEAR + size; i++) {
            YEARS.add(i);
        }
    }
}
