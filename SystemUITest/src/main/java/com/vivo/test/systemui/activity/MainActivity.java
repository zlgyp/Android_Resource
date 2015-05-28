package com.vivo.test.systemui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vivo.test.systemui.R;
import com.vivo.test.systemui.activity.battery.BatteryWarningTestActivity;
import com.vivo.test.systemui.activity.fullscreen.FullScreenTestActivity;
import com.vivo.test.systemui.activity.notification.CustomNotificationTestActivity;
import com.vivo.test.systemui.activity.notification.NormalNotificationTestActivity;
import com.vivo.test.systemui.activity.recent.RecentTaskTestActivity;
import com.vivo.test.systemui.activity.systemstatus.SystemStatusTestActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Settings.Global.putInt(getContentResolver(), "sysui_demo_allowed", 1);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.system_status_test_button:
                startActivity(new Intent(this, SystemStatusTestActivity.class));
                break;
            case R.id.fullscreen_test_button:
                startActivity(new Intent(this, FullScreenTestActivity.class));
                break;
            case R.id.battery_level_test_button:
                startActivity(new Intent(this, BatteryWarningTestActivity.class));
                break;
            case R.id.recent_task_test_button:
                startActivity(new Intent(this, RecentTaskTestActivity.class));
                break;
            case R.id.normal_noti_test_button:
                startActivity(new Intent(this, NormalNotificationTestActivity.class));
                break;
            case R.id.custom_noti_test_button:
                startActivity(new Intent(this, CustomNotificationTestActivity.class));
                break;
            case R.id.systemui_demo_mode_button:
                startActivity(new Intent(this, SystemUIDemoActivity.class));
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
