package com.vivo.test.systemui.activity.notification;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import com.vivo.test.systemui.R;
import com.vivo.test.systemui.ui.DragAnimationView;

public class CustomNotificationTestActivity extends Activity {

    DragAnimationView arrowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_notification_test);

        arrowView = (DragAnimationView) findViewById(R.id.arrow_view);
        arrowView.changeToLine(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawHander.sendEmptyMessage(ARROW);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawHander.removeMessages(ARROW);
        mDrawHander.removeMessages(LINE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_notification_test, menu);
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

    private  static  final int ARROW = 1000;
    private  static  final int LINE = 2000;
    private  Handler mDrawHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch(msg.what){
                case ARROW:
                    arrowView.changeToArrow(0);
                    sendEmptyMessageDelayed(LINE,1000);
                break;
                case LINE:
                    arrowView.changeToLine(0);
                    sendEmptyMessageDelayed(ARROW,1000);
                break;
                default:
            }
        }
    };

}
