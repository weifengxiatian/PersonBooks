package com.cc.personbooks;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.cc.model.RentModel;

import java.util.ArrayList;
/**
 *@author cc
 * */
public class PriceActivity extends Activity {

    private PriceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contentView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PriceAdapter();
        recyclerView.setAdapter(mAdapter);
        GetDateAsynac asynac = new GetDateAsynac();
        asynac.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_price, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class GetDateAsynac extends AsyncTask<Void, Void, ArrayList<RentModel>> {

        @Override
        protected ArrayList<RentModel> doInBackground(Void... params) {
            String[] project = new String[]{};
            Cursor datequery = getContentResolver().query(Uri.parse(RentContentProvider.mUri), project, null, null, BaseSQLite.RecordColumns.DATE_TIME + " DESC");
            ArrayList<RentModel> datequeryArrayList = new ArrayList<>();
            RentModel rentModel = null;
            while (datequery!= null && datequery.moveToNext()) {
                rentModel = new RentModel();
                int anInt = datequery.getInt(datequery.getColumnIndex(BaseSQLite.RecordColumns.WATT_HOUR_SUM));
                int anInt1 = datequery.getInt(datequery.getColumnIndex(BaseSQLite.RecordColumns.WATERMETER_SUM));
                int anInt2 = datequery.getInt(datequery.getColumnIndex(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR));
                int anInt3 = datequery.getInt(datequery.getColumnIndex(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER));
                rentModel.setSumElec(anInt);
                rentModel.setSumwater(anInt1);
                rentModel.setCostelec(anInt2);
                rentModel.setCostwater(anInt3);
                datequeryArrayList.add(rentModel);
            }
            datequery.close();
            return datequeryArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<RentModel> aVoid) {
            mAdapter.setDatequeryArrayList(aVoid);
        }
    }
}
