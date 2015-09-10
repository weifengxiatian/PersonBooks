package com.cc.personbooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Administrator on 2015-8-3.
 */
public class BillSQLite extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "record.db";
    private final static int DATABASE_VERSION = 1;
    public static final String CORE_DATE = "core_date";
//	public static final String WHITELIST = "whitelist";
//	public static final String INTERCEPTLIST = "interceptlist";

    public interface RecordColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String MORNING = "morning";
        public static final String NOON = "noon";
        public static final String EVENING = "evening";
        public static final String LAST_MODIFIED = "Last_Modified";
        public static final String DATE_TIME = "Date_Time";
    }

    public BillSQLite(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        String databaseName = getDatabaseName();
        Log.e("dada", "databaseName:" + databaseName);
        getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("dada", "1");
        String sql = "CREATE TABLE " + BillSQLite.CORE_DATE + " (" +
                RecordColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecordColumns.MORNING+ " LONG, " +
                RecordColumns.NOON + " LONG, " +
                RecordColumns.EVENING + " LONG, " +
                RecordColumns.LAST_MODIFIED + " LONG, " +
                RecordColumns.DATE_TIME + " LONG" +
                ");";
        Log.e("dada", "2");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
