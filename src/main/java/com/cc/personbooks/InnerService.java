package com.cc.personbooks;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2015-8-3.
 */
public class InnerService extends Service {

    private Context mContext;
    private BillSQLite sqLite;

    public InnerService() {
        super();
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Log.e("dada", "sqLite == null?:" + (sqLite == null));
        Log.e("dada", ":"+getApplicationContext());
        if (sqLite == null)
            sqLite =  new BillSQLite(getApplicationContext());
        Log.e("dada", "sqLite == null?:"+ (sqLite == null));
        return START_STICKY;
    }

}
