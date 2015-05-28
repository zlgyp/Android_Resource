package com.vivo.test.systemui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.vivo.test.systemui.R;
import com.vivo.test.systemui.fragment.BarFragment;
import com.vivo.test.systemui.fragment.BatteryFragment;
import com.vivo.test.systemui.fragment.ClockFragment;
import com.vivo.test.systemui.fragment.FragmentDrawer;
import com.vivo.test.systemui.fragment.NetworkFragment;
import com.vivo.test.systemui.fragment.NotificationFragment;
import com.vivo.test.systemui.fragment.StatusFragment;
import com.vivo.test.systemui.utils.BaseTools;
import com.vivo.test.systemui.utils.Constants;


public class SystemUIDemoActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener  {

    private static String TAG = SystemUIDemoActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md_main);

        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha=1.0f;
        getWindow().setAttributes(lp);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

        Bundle bundle = new Bundle();
        bundle.putCharSequence(Constants.COMMAND,Constants.COMMAND_ENTER);
        BaseTools.sendBroadcast(this,bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(Constants.COMMAND,Constants.COMMAND_EXIT);
        BaseTools.sendBroadcast(this,bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
            displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new ClockFragment();
                title = getString(R.string.title_clock);
                break;
            case 1:
                fragment = new BatteryFragment();
                title = getString(R.string.title_battery);
                break;
            case 2:
                fragment = new NetworkFragment();
                title = getString(R.string.title_network);
                break;
            case 3:
                fragment = new BarFragment();
                title = getString(R.string.title_clock);
                break;
            case 4:
                fragment = new StatusFragment();
                title = getString(R.string.title_status);
                break;
            case 5:
                fragment = new NotificationFragment();
                title = getString(R.string.title_notification);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
