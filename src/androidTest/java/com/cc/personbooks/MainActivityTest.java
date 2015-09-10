package com.cc.personbooks;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2015-8-3.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    MainActivity mFirstTestActivity;
    private EditText mZaoshang;
    private EditText mZhongwu;
    private EditText mXiawu;
    private TextView mShow;

    public MainActivityTest(Class<MainActivity> activityClass) {
        super(MainActivity.class);
    }

    public void testOnClick() throws Exception {

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFirstTestActivity = (MainActivity) getActivity();
//        Intent Intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        mZaoshang = (EditText) mFirstTestActivity.findViewById(R.id.editText1);
        mZhongwu = (EditText) mFirstTestActivity.findViewById(R.id.editText2);
        mXiawu = (EditText) mFirstTestActivity.findViewById(R.id.editText3);
        mShow = (TextView) mFirstTestActivity.findViewById(R.id.show);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @MediumTest
    public void testPreconditions() {
        //Try to add a message to add context to your assertions. These messages will be shown if
        //a tests fails and make it easy to understand why a test failed

        assertNotNull("mFirstTestActivity is null", mFirstTestActivity);
        assertNotNull("mZaoshang is null", mZaoshang);
        assertNotNull("mZhongwu is null", mZhongwu);
        assertNotNull("mXiawu is null", mXiawu);
        assertNotNull("mShow is null", mShow);
    }

    @MediumTest
    public void testEditText_labelText() {
        final String actual = mZaoshang.getText().toString();
        Double valueOf = Double.valueOf(actual);
        assertEquals("mFirstTestText contains wrong text", valueOf, actual);
    }
}