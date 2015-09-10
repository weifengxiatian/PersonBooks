package com.cc.personbooks.utils;

import android.app.Application;

import com.cc.personbooks.BaseSQLite;

/**
 * Created by Administrator on 2015-8-26.
 */
public class MainApp extends Application{

    public static int Field = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        String mUri = "content://com.cc.rentbook/record";
        String[] projection = new String[]{BaseSQLite.RecordColumns.DATE_TIME};
        /*
        Cursor query = getContentResolver().query(Uri.parse(mUri), projection, null, null, BaseSQLite.RecordColumns.DATE_TIME + " DESC");
        if (query != null && query.moveToNext()) {
            long data = query.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.DATE_TIME));
            Calendar calendar = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH);
            calendar.setTimeInMillis(data);
            int maxMonth = calendar.get(Calendar.MONTH);
            if (currentMonth == maxMonth) {

            }
        }
        */
    }



}
