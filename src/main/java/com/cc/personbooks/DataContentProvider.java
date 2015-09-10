package com.cc.personbooks;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Administrator on 2015-8-3.
 */
public class DataContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.cc.Billbook";
    private static final int RECORD = 2000;
    private static final int RECORD_ID = 2001;
    private BillSQLite helper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "record", RECORD);
        sUriMatcher.addURI(AUTHORITY, "record/#", RECORD_ID );
        sUriMatcher.addURI(AUTHORITY, "record/#", RECORD_ID );
        sUriMatcher.addURI(AUTHORITY, "record/#", RECORD_ID );
        sUriMatcher.addURI(AUTHORITY, "record/#", RECORD_ID );
    }

    public DataContentProvider() {
        Log.e("dada", "DataContentProvider");
        Log.e("dada", "getContext() == null?:" + (getContext() == null));
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Log.e("dada", "getContext().getApplicationContext():" + getContext().getApplicationContext());
        helper = new BillSQLite(getContext().getApplicationContext());
        return true;
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase database = helper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case RECORD:
                count = database.delete(BillSQLite.CORE_DATE, selection, selectionArgs);
                break;
            case RECORD_ID:
                count = database.delete(BillSQLite.CORE_DATE, selection, selectionArgs);
                break;

            default:
                break;
        }
        if(count !=0){
            getContext().getContentResolver().notifyChange(	uri, null);//for ContentObserver
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase database = helper.getWritableDatabase();
        long id = 0;
        switch (sUriMatcher.match(uri)) {
            case RECORD:
                id = database.insert(BaseSQLite.CORE_DATE, null, values);
                break;
            case RECORD_ID:
                id = database.insert(BaseSQLite.CORE_DATE, null, values);
                break;
            default:
                break;
        }
        if(id < 0){
            return null;
        }
        getContext().getContentResolver().notifyChange(	uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case RECORD:
                cursor = database.query(BillSQLite.CORE_DATE, null, selection, selectionArgs, null, null,
                        sortOrder==null?BillSQLite.RecordColumns.DATE_TIME:sortOrder);
                break;
            case RECORD_ID:
                cursor = database.query(BillSQLite.CORE_DATE, null, selection, selectionArgs, null, null,
                        sortOrder==null?BillSQLite.RecordColumns.DATE_TIME:sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase database = helper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case RECORD:
                count = database.update(BillSQLite.CORE_DATE, values, selection, selectionArgs);
                break;
            default:
                break;
        }
        if(count >0){
            getContext().getContentResolver().notifyChange(	uri, null);
        }
        return count;
    }
}
