package com.vivo.test.systemui.activity.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.vivo.test.systemui.R;

public class BaseActivity extends Activity {
	/** Notification���� */
	public NotificationManager mNotificationManager;
    public final static  int BASE_TEXT_NOTI_ID = 1000;
    public final static  int INBOX_NOTI_ID = BASE_TEXT_NOTI_ID +1;
    public final static  int BIGTEXT_NOTI_ID = INBOX_NOTI_ID +1;
    public final static  int BIGPICTRUE_NOTI_ID = BIGTEXT_NOTI_ID +1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initService();
	}

	private void initService() {
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	public void clearNotify(int notifyId){
		mNotificationManager.cancel(notifyId);
	}

	public void clearAllNotify() {
		mNotificationManager.cancelAll();
	}

	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}

    public Notification.Builder getBaseNotificationBuilder() {
        Notification.Builder builder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.default_icon)
                        .setTicker(getString(R.string.base_notification_ticker))
                        .setContentTitle(getString(R.string.base_notification_title))
                        .setContentText(getString(R.string.base_notification_content));
        return  builder;
    }
}
