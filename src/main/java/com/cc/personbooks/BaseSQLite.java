package com.cc.personbooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Administrator on 2015-8-4.
 * 保存 通通乘以100
 * 显示、计算通通除以100
 */
public class BaseSQLite extends SQLiteOpenHelper{
    private final static String TAG = "BaseSQLite";
    private final static String DATABASE_NAME = "daily.db";
    private final static int DATABASE_VERSION = 1;
    public static final String CORE_DATE = "base_date";

    public interface RecordColumns {
        public static final String _ID = BaseColumns._ID;
        //当前电表计数
        public static final String WATT_HOUR_SUM = "watt_hour_sum";
        //当前水表计数
        public static final String WATERMETER_SUM = "watermeter_sum";
        //当月消耗电数
        public static final String CONSUME_CURRENT＿WATT_HOUR = "consume_current＿watt_hour";
        //当月消耗电数
        public static final String CONSUME_CURRENT_WATER = "consume_current_water";
        //电价
        public static final String PRICE_WATT_HOUR = "price_watt_hour";
        //水价
        public static final String PRICE_WATER = "price_water";
        //当月电费
        public static final String SUM_WATT = "sum_watt";
        //当月水费
        public static final String SUM_WATER = "sum_water";
        //当月房租相关消费
        public static final String COST = "cost";
        //房租
        public static final String HOUSERENT = "houserent";
        //宽带费
        public static final String NETCOST = "netcost";
        // 登记时间
        public static final String DATE_TIME = "DATE_TIME";
        // 月份@年份和月份拼接组成
        public static final String MOUNTH = "mounth";

    }

    public BaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onUpgrade the version
     * @param context
     * @param version
     */
    public BaseSQLite(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }
    /*
    public BaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "1");
        String sql = "CREATE TABLE " + BaseSQLite.CORE_DATE + " (" +
                RecordColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecordColumns.WATT_HOUR_SUM+ " LONG, " +
                RecordColumns.WATERMETER_SUM + " LONG, " +
                RecordColumns.CONSUME_CURRENT＿WATT_HOUR + " LONG, " +
                RecordColumns.CONSUME_CURRENT_WATER + " LONG, " +
                RecordColumns.PRICE_WATT_HOUR + " LONG, " +
                RecordColumns.PRICE_WATER + " LONG, " +
                RecordColumns.SUM_WATT + " LONG, " +
                RecordColumns.SUM_WATER + " LONG, " +
                RecordColumns.COST + " LONG, " +
                RecordColumns.HOUSERENT + " LONG, " +
                RecordColumns.NETCOST + " LONG, " +
                RecordColumns.MOUNTH + " LONG, " +
                RecordColumns.DATE_TIME + " LONG" +
                ");";
        Log.e(TAG, "2");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //temp null
    }
}
