package com.phyte.sanraphindustries.viso.car.db.table;

/**
 *Application Name: CarAssistant
 * Package name: com.classic.car.app
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 10:26
 */
public class ConsumerTable {
    public static final String NAME = "t_consumer";

    public static final String COLUMN_ID               = "id";
    public static final String COLUMN_CREATE_TIME      = "createTime";
    public static final String COLUMN_CONSUMPTION_TIME = "consumptionTime";
    public static final String COLUMN_MONEY            = "money";
    public static final String COLUMN_TYPE             = "type";
    public static final String COLUMN_NOTES            = "notes";
    public static final String COLUMN_OIL_TYPE         = "oilType";
    public static final String COLUMN_UNIT_PRICE       = "unitPrice";
    public static final String COLUMN_CURRENT_MILEAGE  = "currentMileage";
    public static final String COLUMN_LAST_UPDATE_TIME = "lastUpdateTime";

    public static String create(){
        //noinspection StringBufferReplaceableByString
        return new StringBuilder("CREATE TABLE ").append(NAME).append("(")
                                                 .append(COLUMN_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                                                 .append(COLUMN_CREATE_TIME).append(" INTEGER NOT NULL,")
                                                 .append(COLUMN_CONSUMPTION_TIME).append(" INTEGER NOT NULL,")
                                                 .append(COLUMN_LAST_UPDATE_TIME).append(" INTEGER DEFAULT 0, ")
                                                 .append(COLUMN_MONEY).append(" INTEGER,")
                                                 .append(COLUMN_UNIT_PRICE).append(" INTEGER,")
                                                 .append(COLUMN_TYPE).append(" INTEGER,")
                                                 .append(COLUMN_OIL_TYPE).append(" INTEGER,")
                                                 .append(COLUMN_CURRENT_MILEAGE).append(" INTEGER,")
                                                 .append(COLUMN_NOTES).append(" TEXT")
                                                 .append(")").toString();
    }

    /**
     * Add Last Updated Field
     *
     * @return sql
     */
    public static String updateToVersion2(){
        //noinspection StringBufferReplaceableByString
        return new StringBuilder().append("ALTER TABLE ")
                                  .append(NAME)
                                  .append(" ADD COLUMN ")
                                  .append(COLUMN_LAST_UPDATE_TIME)
                                  .append(" INTEGER DEFAULT 0 ")
                                  .toString();
    }
}
