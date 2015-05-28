package com.vivo.test.systemui.activity.fullscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.vivo.test.systemui.R;


public class FullScreenTestActivity extends Activity {

    RelativeLayout mRLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_test);
        mRLayout = (RelativeLayout)findViewById(R.id.content);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.systemui_invisible:
                mRLayout.setSystemUiVisibility(View.INVISIBLE);
                break;
            case R.id.systemui_visible:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                break;
            case R.id.systemui_fullscreen:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;
            case R.id.systemui_layout_fullscreen:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                break;
            case R.id.systemui_layout_stable:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                break;
            case R.id.systemui_immersive:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                        |  View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;
            case R.id.systemui_immersive_sticky:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
                break;
            case R.id.systemui_visible_combine:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
                break;
            case R.id.systemui_visible_combine_one:
                mRLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // hide status bar
                       );
            default:
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.full_screen_test, menu);
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
