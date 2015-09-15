package com.cc.personbooks;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.personbooks.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RentEditActivity extends AppCompatActivity implements View.OnClickListener, DialogPerFragment.extreAction {

    private static String TAG = "RentEdit";
    private TextView showConsume;
    private EditText editeRent;
    private EditText editNet;
    private EditText editElec;
    private EditText editWater;
    //private long perElec;
    //private long perWater;

    // 当前电单位价格;
    private double perElec = -1;
    // 当前水单位价格;
    private double perWater = -1;
    // 目前电表电量
    private long mCurrentElec;
    // 目前电表水量
    private long mCurrentWater;
    private TextView currentper;
    // 消耗的水电数量
    private EditText costwater;
    private EditText costelec;
    // 模式
    private int mFiled = 1;
    private LinearLayout mEditField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_edit);
        initBaseUI();
        String[] projection = new String[]{BaseSQLite.RecordColumns.MOUNTH, BaseSQLite.RecordColumns.DATE_TIME};
        Cursor query = getContentResolver().query(Uri.parse(RentContentProvider.mUri), projection, null, null, BaseSQLite.RecordColumns.DATE_TIME + " DESC");
        if (query != null && query.getCount() != 0 && query.moveToNext()) {
            String data_mounth = query.getString(query.getColumnIndex(BaseSQLite.RecordColumns.MOUNTH));
            String mounth = data_mounth.substring(data_mounth.length() - 2);
            Calendar calendar = Calendar.getInstance();
            int current_mounth = calendar.get(Calendar.MONTH);
            Integer temp_mounth = Integer.valueOf(mounth);
            //Log.e("TAG", "current_mounth:" + current_mounth);
            //Log.e("TAG", "temp_mounth:" + temp_mounth);
            if (current_mounth == temp_mounth) {
                initShowFieldUI(query, data_mounth);
                query.close();
                return;
            }
        }
        query.close();
        initView();
        initDate();
    }

    private void initShowFieldUI(Cursor query, String data_mounth) {
        mEditField.setVisibility(View.GONE);
        findViewById(R.id.save).setVisibility(View.GONE);
        findViewById(R.id.showper).setVisibility(View.GONE);
        showConsume.setVisibility(View.GONE);
        ViewStub stub = (ViewStub) findViewById(R.id.showField);
        //stub.setVisibility(View.VISIBLE);
        View showField = stub.inflate();
        TextView showField_title = (TextView) showField.findViewById(R.id.title);
        TextView showField_content = (TextView) showField.findViewById(R.id.content);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        long perElec = sp.getLong("perElec", 0);
        long perWater = sp.getLong("perWater", 0);
        this.perElec = (double) perElec / 100.0;
        this.perWater = (double) perWater / 100.0;
        showCurrentPer(this.perElec, this.perWater);


        String[] temp = new String[]{data_mounth};
        Cursor datequery = getContentResolver().query(Uri.parse(RentContentProvider.mUri), null, BaseSQLite.RecordColumns.MOUNTH + "=?", temp, BaseSQLite.RecordColumns.DATE_TIME + " DESC");
        JSONObject rentMode = null;
        if (datequery != null && datequery.getCount()!= 0 && datequery.moveToNext()){
            rentMode = new JSONObject();
            long watermeter_sum = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.WATERMETER_SUM));
            long watt_hour_sum = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.WATT_HOUR_SUM));
            long consume_current＿watt_hour = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR));
            long consume_current_water = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER));
            long price_watt_hour = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.PRICE_WATT_HOUR));
            long price_water = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.PRICE_WATER));
            long sum_watt = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.SUM_WATT));
            long sum_water = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.SUM_WATER));
            long cost = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.COST));
            long houserent = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.HOUSERENT));
            long netcost = datequery.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.NETCOST));
            try {
                rentMode.put(BaseSQLite.RecordColumns.WATERMETER_SUM, watermeter_sum);
                rentMode.put(BaseSQLite.RecordColumns.WATT_HOUR_SUM, watt_hour_sum);
                rentMode.put(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR, consume_current＿watt_hour);
                rentMode.put(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER, consume_current_water);
                rentMode.put(BaseSQLite.RecordColumns.PRICE_WATT_HOUR, price_watt_hour);
                rentMode.put(BaseSQLite.RecordColumns.PRICE_WATER, price_water);
                rentMode.put(BaseSQLite.RecordColumns.SUM_WATT, sum_watt);
                rentMode.put(BaseSQLite.RecordColumns.SUM_WATER, sum_water);
                rentMode.put(BaseSQLite.RecordColumns.COST, cost);
                rentMode.put(BaseSQLite.RecordColumns.HOUSERENT, houserent);
                rentMode.put(BaseSQLite.RecordColumns.NETCOST, netcost);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        datequery.close();
        if (rentMode == null)
            return ;
        try {
            String title = getString(R.string.showField_rent);
            String content = getString(R.string.showfield_detail);
            showField_title.setText(String.format(title, rentMode.getLong(BaseSQLite.RecordColumns.COST)/100.0));
            showField_content.setText(String.format(content, rentMode.getLong(BaseSQLite.RecordColumns.HOUSERENT)/100.0,
                    "8",
                    rentMode.getInt(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR),
                    rentMode.getInt(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER),
                    rentMode.getDouble(BaseSQLite.RecordColumns.SUM_WATT),
                    rentMode.getDouble(BaseSQLite.RecordColumns.SUM_WATER),
                    rentMode.getInt(BaseSQLite.RecordColumns.NETCOST)/100.0
            ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return;
    }

    private void initBaseUI() {
        mEditField = (LinearLayout) findViewById(R.id.editField);
        currentper = (TextView) findViewById(R.id.currentper);
        showConsume = (TextView) findViewById(R.id.show_consume);
        showConsume.setOnClickListener(this);
    }

    private void initDate() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int rent = sp.getInt("rent", 0);
        int netcost = sp.getInt("netcost", 0);

        long perElec = sp.getLong("perElec", 0);
        long perWater = sp.getLong("perWater", 0);
        // Toast.makeText(this, "elec : " + perElec + "/ water : " + perWater, Toast.LENGTH_SHORT).show();
        if (rent != 0)
            editeRent.setText(rent);
         else
            editeRent.setHint("0");

        if (netcost != 0)
            editNet.setText(netcost);
        else
            editNet.setHint("0");

        if (perElec == -1)
            this.perElec = (double) perElec / 100.0;
        if (perWater == -1)
            this.perWater = (double) perWater / 100.0;
        showCurrentPer(this.perElec, this.perWater);
        String[] projection = new String[]{BaseSQLite.RecordColumns.WATT_HOUR_SUM, BaseSQLite.RecordColumns.WATERMETER_SUM};
        Cursor query = getContentResolver().query(Uri.parse(RentContentProvider.mUri), projection, null, null, BaseSQLite.RecordColumns.DATE_TIME + " DESC");
        if (query == null || query.getCount() == 0) {
            mCurrentElec = 0;
            mCurrentWater = 0;
            initcostData();
        } else {
            query.moveToNext();
            mCurrentElec = query.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.WATT_HOUR_SUM));
            mCurrentWater = query.getLong(query.getColumnIndex(BaseSQLite.RecordColumns.WATERMETER_SUM));
        }
        query.close();
    }

    /**
     * 初始化部分UI
     */
    private void initcostData() {
        findViewById(R.id.linearLayout5).setVisibility(View.VISIBLE);
        findViewById(R.id.linearLayout6).setVisibility(View.VISIBLE);
        costwater = (EditText) findViewById(R.id.consume_water);
        costelec = (EditText) findViewById(R.id.consume_elec);
    }

    public void showCurrentPer(double tempelec, double tempwater) {
        String temp = getString(R.string.show_per);
        currentper.setText(String.format(temp, tempelec, tempwater));
        perElec = tempelec;
        perWater = tempwater;
    }


    private void initView() {
        editeRent = (EditText) findViewById(R.id.edite_rent);
        editNet = (EditText) findViewById(R.id.edit_net);
        editElec = (EditText) findViewById(R.id.edit_elec);
        editWater = (EditText) findViewById(R.id.edit_water);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.showper).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rent_edit, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                //TODO implement
                if (editeRent.getText().length() == 0 || editNet.getText().length() == 0 || editElec.getText().length() == 0 || editWater.getText().length() == 0)
                    break;

                int tempelec = Integer.parseInt(editElec.getText().toString());

                double saveElec = 0;
                if (mCurrentElec == 0)
                    saveElec = Integer.parseInt(costelec.getText().toString()) * perElec;
                else
                    saveElec = (tempelec - mCurrentElec) * perElec;


                double saveWater = 0;
                int tempwater = Integer.parseInt(editWater.getText().toString());
                if (mCurrentWater == 0)
                    saveWater = Integer.parseInt(costwater.getText().toString()) * perWater;
                else
                    saveWater = (tempwater - mCurrentWater) * perWater;


                int saverent = Integer.parseInt(editeRent.getText().toString());
                int savenet = Integer.parseInt(editNet.getText().toString());
                // 当月总费用
                double cost = saverent + savenet + saveWater + saveElec;
                ContentValues values = new ContentValues();
                Calendar rightNow = Calendar.getInstance();
                int year = rightNow.get(Calendar.YEAR);
                values.put(BaseSQLite.RecordColumns.MOUNTH, year + Utils.StringOfMonths(rightNow.get(Calendar.MONTH)));
                values.put(BaseSQLite.RecordColumns.DATE_TIME, System.currentTimeMillis());
                values.put(BaseSQLite.RecordColumns.SUM_WATER, saveWater);
                values.put(BaseSQLite.RecordColumns.SUM_WATT, saveElec);
                values.put(BaseSQLite.RecordColumns.COST, cost * 100);
                values.put(BaseSQLite.RecordColumns.HOUSERENT, saverent * 100);
                values.put(BaseSQLite.RecordColumns.NETCOST, savenet * 100);
                values.put(BaseSQLite.RecordColumns.PRICE_WATER, perWater * 100);
                values.put(BaseSQLite.RecordColumns.PRICE_WATT_HOUR, perElec * 100);
                if (mCurrentWater != 0)
                    values.put(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER, tempwater - mCurrentWater);
                else
                    values.put(BaseSQLite.RecordColumns.CONSUME_CURRENT_WATER, Integer.parseInt(costwater.getText().toString()));
                if (mCurrentElec != 0)
                    values.put(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR, tempelec - mCurrentElec);
                else
                    values.put(BaseSQLite.RecordColumns.CONSUME_CURRENT＿WATT_HOUR, Integer.parseInt(costelec.getText().toString()));
                values.put(BaseSQLite.RecordColumns.WATT_HOUR_SUM, tempelec);
                values.put(BaseSQLite.RecordColumns.WATERMETER_SUM, tempwater);
                Uri insert = getContentResolver().insert(Uri.parse(RentContentProvider.mUri), values);
                if (insert != null) {
                    Toast.makeText(this, "记录完毕", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.show_consume:
                if (editeRent.getText().length() == 0 || editNet.getText().length() == 0 || editElec.getText().length() == 0 || editWater.getText().length() == 0)
                    break;
                int tempElec = Integer.parseInt(editElec.getText().toString());
                int tempWater = Integer.parseInt(editWater.getText().toString());

                //double Elec = (tempElec - mCurrentElec)* perElec;
                //double Water = (tempWater - mCurrentWater) * perWater;

                double Elec = 0;
                if (mCurrentElec == 0) {
                    Elec = Integer.parseInt(costelec.getText().toString()) * perElec;
                } else {
                    Elec = (tempElec - mCurrentElec) * perElec;
                }

                double Water = 0;
                if (mCurrentWater == 0) {
                    Water = Integer.parseInt(costwater.getText().toString()) * perWater;
                } else {
                    Water = (tempWater - mCurrentWater) * perWater;
                }


                int rent = Integer.parseInt(editeRent.getText().toString());
                int net = Integer.parseInt(editNet.getText().toString());

                double total = rent + net + Elec + Water;
                showConsume.setText("cost:" + total + "Y");
                break;
            case R.id.showper:
                DialogPerFragment per = (DialogPerFragment) getFragmentManager().findFragmentByTag("per");
                if (null == per) {
                    per = DialogPerFragment.newInstance("title", "content");
                    per.show(getFragmentManager(), "per");
                }
                break;
        }
    }

    @Override
    public void todoSomethings(double tempelec, double tempWater) {
        showCurrentPer(tempelec, tempWater);
    }
}
