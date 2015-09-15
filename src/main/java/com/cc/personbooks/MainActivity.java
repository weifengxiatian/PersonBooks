package com.cc.personbooks;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.personbooks.BillSQLite.RecordColumns;
import com.cc.personbooks.utils.ViewServer;

public class MainActivity extends ActionBarActivity implements OnClickListener {

    private EditText mZaoshang;
    private EditText mZhongwu;
    private EditText mXiawu;
    private TextView mShow;
    /** 输入框小数的位数 */
    private static final int DECIMAL_DIGITS = 2;
    private String mUri = "content://com.cc.Billbook/record";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // add cc 2015.9.6
        ViewServer.get(this).addWindow(this);
        // end
        initToolbar();
        initEdit();
//		Intent service = new Intent(MainActivity.this, InnerService.class);
//		startService(service);
    }

    private void initEdit() {
        mZaoshang = (EditText) findViewById(R.id.editText1);
        mZhongwu = (EditText) findViewById(R.id.editText2);
        mXiawu = (EditText) findViewById(R.id.editText3);
        mShow = (TextView) findViewById(R.id.show);
        mShow.setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);

        /** * 设置小数位数控制 */
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，，直接返回
                if ("".equals(source.toString()))
                    return null;
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }

        };
        //这样就OK了，很简单吧，其实这个InputFilter很强大滴
        mZaoshang.setFilters(new InputFilter[] { lengthfilter });
        mZhongwu.setFilters(new InputFilter[] { lengthfilter });
        mXiawu.setFilters(new InputFilter[] { lengthfilter });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);

        // 打開 up bottom
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // 實作 drawer toggle 並放入 toolbar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.save:
                ContentValues values = new ContentValues();
                long tempMoney = 0;
                Editable text = mZaoshang.getText();
                if (text.length() != 0) {
                    Double valueOf = Double.valueOf(text.toString());
                    tempMoney = (long) (valueOf * 100);
                    values.put(RecordColumns.MORNING, tempMoney);
                }
                text = mZhongwu.getText();
                if (text.length() != 0) {
                    Double valueOf = Double.valueOf(text.toString());
                    tempMoney = (long) (valueOf * 100);
                    values.put(RecordColumns.NOON, tempMoney);
                }
                text = mXiawu.getText();
                if (text.length() != 0) {
                    Double valueOf = Double.valueOf(text.toString());
                    tempMoney = (long) (valueOf * 100);
                    values.put(RecordColumns.EVENING, tempMoney);
                }
                if (values.size() != 0) {
                    values.put(RecordColumns.LAST_MODIFIED, System.currentTimeMillis());
                    values.put(RecordColumns.DATE_TIME, System.currentTimeMillis());
                    Uri insert = getContentResolver().insert(Uri.parse(mUri), values);
                    if (insert == null) {
                        Toast.makeText(getApplicationContext(), "1+failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "1+success", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.show:
                long lala = 0;
                Double valueOf = 0.0;
                Editable temp = mZaoshang.getText();
                if (temp.length() != 0) {
                    valueOf = Double.valueOf(temp.toString());
                    lala = (long) (valueOf*100);
                }
                mShow.setText(temp + "||" + lala);
                break;
            case R.id.item1:
                Intent intent = new Intent(this, PriceActivity.class);
                startActivity(intent);
                break;
            case R.id.item3:
                Intent rent = new Intent(this, RentEditActivity.class);
                startActivity(rent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        super.onBackPressed();
    }
}
