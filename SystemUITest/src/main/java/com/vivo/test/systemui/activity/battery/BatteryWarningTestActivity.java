package com.vivo.test.systemui.activity.battery;

import android.app.Activity;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.vivo.test.systemui.R;

public class BatteryWarningTestActivity extends Activity {

    private Spinner mStatusSpinner;
    private EditText mLevelEditText;
    private Spinner mPlugSpinner;
    private String[] mStatusArrays;
    private ArrayAdapter<String> mStatusAdapter;
    private CheckBox mPlugCheckBox;
    private int mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_warning_test);

        mLevelEditText = (EditText)findViewById(R.id.level_editText);
        mStatusSpinner = (Spinner)findViewById(R.id.status_spinner);
        mStatusArrays = getResources().getStringArray(R.array.battery_status);
        mStatusAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mStatusArrays);
        mStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpinner.setAdapter(mStatusAdapter);
        mStatusSpinner.setVisibility(View.VISIBLE);
        mStatusSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        mPlugCheckBox = (CheckBox)findViewById(R.id.battery_plugged);
        (findViewById(R.id.battery_para_send_button)).setOnClickListener(mOnClickListener);


    }

    class SpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            mStatus = arg2 + 1;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String numText = mLevelEditText.getText().toString();
            int level = 20;
            if (numText != null && "".equals(numText)) {
                level = Integer.parseInt(numText);
            }
            int plugged = 0;
            if (mPlugCheckBox != null && mPlugCheckBox.isChecked()) {
                plugged = 1;
            }

            Intent intent = new Intent("systemui.test.battery.level");
            intent.putExtra(BatteryManager.EXTRA_LEVEL, level);
            intent.putExtra(BatteryManager.EXTRA_PLUGGED,plugged );
            intent.putExtra(BatteryManager.EXTRA_STATUS, mStatus);
            BatteryWarningTestActivity.this.sendBroadcast(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.battery_warning_test, menu);
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
}
