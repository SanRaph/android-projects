package com.phyte.sanraphindustries.viso.car.db.dao;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import com.phyte.sanraphindustries.viso.car.db.table.ConsumerTable;
import com.phyte.sanraphindustries.viso.car.entity.ConsumerDetail;
import com.phyte.sanraphindustries.viso.car.utils.CloseUtil;
import com.phyte.sanraphindustries.viso.car.utils.CursorUtil;
import com.phyte.sanraphindustries.viso.car.utils.DataUtil;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 *Application Name: CarAssistant
 * Package name: com.classic.car.app
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 10:26
 */
public class ConsumerDao {
    private BriteDatabase mDatabase;

    public ConsumerDao(BriteDatabase database) {
        this.mDatabase = database;
    }

    public BriteDatabase getDatabase() {
        return mDatabase;
    }

    public long insert(ConsumerDetail detail){
        return mDatabase.insert(ConsumerTable.NAME, convert(detail, true));
    }

    public int update(ConsumerDetail detail){
        return mDatabase.update(ConsumerTable.NAME, convert(detail, false), ConsumerTable.COLUMN_ID + " = ? ",
                String.valueOf(detail.getId()));
    }

    public Observable<List<ConsumerDetail>> queryAll(){
        return query(null, 0, 0, true, false);
    }

    public Observable<List<ConsumerDetail>> queryBetween(long startTime, long endTime){
        return query(null, startTime, endTime, false, true);
    }

    public Observable<List<ConsumerDetail>> query(Integer type, long startTime, long endTime,
                                                  boolean desc, boolean asc) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(ConsumerTable.NAME);
        final boolean isBetween = (startTime > 0 && endTime > 0);
        if (null != type || isBetween) {
            sql.append(" WHERE ");
        }
        if (null != type) {
            sql.append(ConsumerTable.COLUMN_TYPE).append(" = ").append(type);
        }
        if (null != type && isBetween) {
            sql.append(" AND ");
        }
        if (isBetween) {
            sql.append(ConsumerTable.COLUMN_CONSUMPTION_TIME)
               .append(" BETWEEN ")
               .append(startTime)
               .append(" AND ")
               .append(endTime);
        }
        if (asc) {
            sql.append(" ORDER BY ").append(ConsumerTable.COLUMN_CONSUMPTION_TIME);
        }
        if (desc) {
            sql.append(" ORDER BY ").append(ConsumerTable.COLUMN_CONSUMPTION_TIME).append(" DESC ");
        }
        return queryBySql(sql.toString());
    }

    public ConsumerDetail queryByCreateTime(long createTime) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(ConsumerTable.NAME)
                                                               .append(" WHERE ")
                                                               .append(ConsumerTable.COLUMN_CREATE_TIME)
                                                               .append(" = ")
                                                               .append(createTime);
        List<ConsumerDetail> list = convert(mDatabase.query(sql.toString()));
        return DataUtil.isEmpty(list) ? null : list.get(0);
    }

    public List<ConsumerDetail> queryAllSync() {
        //noinspection StringBufferReplaceableByString
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(ConsumerTable.NAME);
        return convert(mDatabase.query(sql.toString()));
    }

    private Observable<List<ConsumerDetail>> queryBySql(String sql){
        return mDatabase.createQuery(ConsumerTable.NAME, sql)
                        .map(new Func1<SqlBrite.Query, List<ConsumerDetail>>() {
                            @Override public List<ConsumerDetail> call(SqlBrite.Query query) {
                                return convert(query.run());
                            }
                        });
    }

    public int delete(long id){
        return mDatabase.delete(ConsumerTable.NAME, ConsumerTable.COLUMN_ID + " = ? ", String.valueOf(id));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN) private List<ConsumerDetail> convert(Cursor cursor){
        if(null == cursor){
            return null;
        }
        ArrayList<ConsumerDetail> result = new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                result.add(new ConsumerDetail(
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_ID),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CREATE_TIME),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_LAST_UPDATE_TIME),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CONSUMPTION_TIME),
                        CursorUtil.getFloat(cursor, ConsumerTable.COLUMN_MONEY),
                        CursorUtil.getInt(cursor, ConsumerTable.COLUMN_TYPE),
                        CursorUtil.getString(cursor, ConsumerTable.COLUMN_NOTES),
                        CursorUtil.getInt(cursor, ConsumerTable.COLUMN_OIL_TYPE),
                        CursorUtil.getFloat(cursor, ConsumerTable.COLUMN_UNIT_PRICE),
                        CursorUtil.getLong(cursor, ConsumerTable.COLUMN_CURRENT_MILEAGE)
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            CloseUtil.close(cursor);
        }
        return result;
    }

    private ContentValues convert(ConsumerDetail detail, boolean isInsert) {
        ContentValues values = new ContentValues();
        if (isInsert) {
            values.put(ConsumerTable.COLUMN_CREATE_TIME, detail.getCreateTime());
        }
        values.put(ConsumerTable.COLUMN_LAST_UPDATE_TIME, detail.getLastUpdateTime());
        values.put(ConsumerTable.COLUMN_CONSUMPTION_TIME, detail.getConsumptionTime());
        values.put(ConsumerTable.COLUMN_TYPE, detail.getType());
        values.put(ConsumerTable.COLUMN_MONEY, detail.getMoney());
        values.put(ConsumerTable.COLUMN_OIL_TYPE, detail.getOilType());
        values.put(ConsumerTable.COLUMN_UNIT_PRICE, detail.getUnitPrice());
        values.put(ConsumerTable.COLUMN_CURRENT_MILEAGE, detail.getCurrentMileage());
        values.put(ConsumerTable.COLUMN_NOTES, detail.getNotes());
        return values;
    }
}
