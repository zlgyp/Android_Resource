package com.vivo.test.systemui.activity.notification;

import android.app.Dialog;
import android.app.Notification;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.vivo.test.systemui.R;

public class NormalNotificationTestActivity extends BaseActivity {

    private final static String TAG = NormalNotificationTestActivity.class.getName();
    private final static String ITEM_NONE_STRING = "none";
    private final static int ITEM_NONE_INT = -1000;

    private String[] mPriorityType;
    private String[] mDefaultType;
    private String[] mCategoryType;
    private String[] mSecretType;
    private String[] mFlagType;
    private Spinner mPrioritySpinner;
    private Spinner mDefaultSpinner;
    private Spinner mCategorySpinner;
    private Spinner mSecretSpinner;
    private int mNotificationPriority = Notification.PRIORITY_DEFAULT;
    private int mNotificationParameter = Notification.DEFAULT_ALL;
    private String mNotificationCategory = Notification.CATEGORY_MESSAGE;
    private int mNotificationSecret = Notification.VISIBILITY_PUBLIC;
    private boolean[] mFlagSelected;

    private EditText mTextNotiFlag;

    private EditText mActionNumText;
    private EditText mAction1Text;
    private EditText mAction2Text;
    private EditText mAction3Text;
    private int mActionNum = 0;

    private Dialog mSelectFlagDialog;

    private int mNotificationFlag = ITEM_NONE_INT;

    private EditText mNotificationTitleText;
    private EditText mNotificationContentText;
    private EditText mNotificationTickerText;

    private String[] mInBoxNotificationConent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_notification_test);

        mPrioritySpinner = (Spinner)findViewById(R.id.noti_priority);
        mDefaultSpinner = (Spinner)findViewById(R.id.noti_default);
        mCategorySpinner = (Spinner)findViewById(R.id.noti_category);
        mSecretSpinner = (Spinner)findViewById(R.id.noti_secret);

        mPriorityType = getResources().getStringArray(R.array.priority_type);
        mDefaultType = getResources().getStringArray(R.array.default_type);
        mCategoryType = getResources().getStringArray(R.array.category_type);
        mSecretType = getResources().getStringArray(R.array.secret_type);
        mFlagType = getResources().getStringArray(R.array.flag_type);
        mInBoxNotificationConent = getResources().getStringArray(R.array.inbox_line);
        mFlagSelected = new boolean[mFlagType.length];

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mPriorityType);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);
        mPrioritySpinner.setVisibility(View.VISIBLE);
        mPrioritySpinner.setOnItemSelectedListener(new PrioritySpinnerSelectedListener());

        ArrayAdapter<String> defaultAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mDefaultType);
        defaultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDefaultSpinner.setAdapter(defaultAdapter);
        mDefaultSpinner.setVisibility(View.VISIBLE);
        mDefaultSpinner.setOnItemSelectedListener(new DefaultSpinnerSelectedListener());


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mCategoryType);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(categoryAdapter);
        mCategorySpinner.setVisibility(View.VISIBLE);
        mCategorySpinner.setOnItemSelectedListener(new CategorySpinnerSelectedListener());

        ArrayAdapter<String> secretAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mSecretType);
        secretAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSecretSpinner.setAdapter(secretAdapter);
        mSecretSpinner.setVisibility(View.VISIBLE);
        mSecretSpinner.setOnItemSelectedListener(new SecretSpinnerSelectedListener());


        mTextNotiFlag = (EditText)findViewById(R.id.text_noti_flag);
        mAction1Text = (EditText)findViewById(R.id.noti_action1_content);
        mAction2Text = (EditText)findViewById(R.id.noti_action2_content);
        mAction3Text = (EditText)findViewById(R.id.noti_action3_content);
        mActionNumText = (EditText)findViewById(R.id.noti_action_num);

        mSelectFlagDialog = onCreateDialog();

        mNotificationTitleText = (EditText)findViewById(R.id.noti_title);
        mNotificationContentText = (EditText)findViewById(R.id.noti_content);
        mNotificationTickerText =  (EditText)findViewById(R.id.noti_ticker);
    }

    class DefaultSpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch (arg2){
                case 1:
                    mNotificationParameter = Notification.DEFAULT_ALL;
                    break;
                case 2:
                    mNotificationParameter = Notification.DEFAULT_SOUND;
                    break;
                case 3:
                    mNotificationParameter = Notification.DEFAULT_VIBRATE;
                    break;
                case 4:
                    mNotificationParameter = Notification.DEFAULT_LIGHTS;
                    break;
               default:
                    mNotificationParameter = ITEM_NONE_INT;
                    break;
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class CategorySpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch (arg2){
                case 1:
                    mNotificationCategory = Notification.CATEGORY_CALL;
                    break;
                case 2:
                    mNotificationCategory = Notification.CATEGORY_MESSAGE;
                    break;
                case 3:
                    mNotificationCategory = Notification.CATEGORY_EMAIL;
                    break;
                case 4:
                    mNotificationCategory = Notification.CATEGORY_EVENT;
                    break;
                case 5:
                    mNotificationCategory = Notification.CATEGORY_PROMO;
                    break;
                case 6:
                    mNotificationCategory = Notification.CATEGORY_ALARM;
                    break;
                case 7:
                    mNotificationCategory = Notification.CATEGORY_PROGRESS;
                    break;
                case 8:
                    mNotificationCategory = Notification.CATEGORY_SOCIAL;
                    break;
                case 9:
                    mNotificationCategory = Notification.CATEGORY_ERROR;
                    break;
                case 10:
                    mNotificationCategory = Notification.CATEGORY_TRANSPORT;
                    break;
                case 11:
                    mNotificationCategory = Notification.CATEGORY_SYSTEM;
                    break;
                case 12:
                    mNotificationCategory = Notification.CATEGORY_SERVICE;
                    break;
                case 13:
                    mNotificationCategory = Notification.CATEGORY_RECOMMENDATION;
                    break;
                case 14:
                    mNotificationCategory = Notification.CATEGORY_STATUS;
                    break;
                case 0:
                    mNotificationCategory = ITEM_NONE_STRING;
                    break;
                default:
                    mNotificationCategory = ITEM_NONE_STRING;
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class PrioritySpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch (arg2){
                case 1:
                    mNotificationPriority = Notification.PRIORITY_MIN;
                    break;
                case 2:
                    mNotificationPriority = Notification.PRIORITY_LOW;
                    break;
                case 3:
                    mNotificationPriority = Notification.PRIORITY_DEFAULT;
                    break;
                case 4:
                    mNotificationPriority = Notification.PRIORITY_HIGH;
                    break;
                case 5:
                    mNotificationPriority = Notification.PRIORITY_MAX;
                    break;
                default:
                    mNotificationPriority = ITEM_NONE_INT;
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class SecretSpinnerSelectedListener implements Spinner.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            switch (arg2){
                case 1:
                    mNotificationSecret = Notification.VISIBILITY_PUBLIC;
                    break;
                case 2:
                    mNotificationSecret = Notification.VISIBILITY_PRIVATE;
                    break;
                case 3:
                    mNotificationSecret = Notification.VISIBILITY_SECRET;
                    break;
                case 0:
                    mNotificationSecret = ITEM_NONE_INT;
                default:
                    mNotificationSecret = ITEM_NONE_INT;
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    public Dialog onCreateDialog() {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogInterface.OnMultiChoiceClickListener mutiListener =
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface,
                                        int which, boolean isChecked) {
                        mFlagSelected[which] = isChecked;
                    }
                };
        builder.setMultiChoiceItems(R.array.flag_type, mFlagSelected, mutiListener);
        DialogInterface.OnClickListener btnListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < mFlagSelected.length; i++) {
                            if (mFlagSelected[i] == true) {
                                if (mNotificationFlag == ITEM_NONE_INT)
                                    mNotificationFlag = 0;
                                setNotificationFlag(i);
                            }
                        }
                        mTextNotiFlag.setText(" " + mNotificationFlag);
                    }
                };
        builder.setPositiveButton(R.string.button_ok, btnListener);
        dialog = builder.create();
        return dialog;
    }

    public void setNotificationFlag(int which) {
        switch (which) {
            case 0:
                mNotificationFlag |= Notification.FLAG_SHOW_LIGHTS;
                break;
            case 1:
                mNotificationFlag |= Notification.FLAG_ONGOING_EVENT;
                break;
            case 2:
                mNotificationFlag |= Notification.FLAG_INSISTENT;
                break;
            case 3:
                mNotificationFlag |= Notification.FLAG_ONLY_ALERT_ONCE;
                break;
            case 4:
                mNotificationFlag |= Notification.FLAG_AUTO_CANCEL;
                break;
            case 5:
                mNotificationFlag |= Notification.FLAG_AUTO_CANCEL;
                break;
            case 6:
                mNotificationFlag |= Notification.FLAG_FOREGROUND_SERVICE;
                break;
            case 7:
                mNotificationFlag |= Notification.FLAG_LOCAL_ONLY;
                break;
            case 8:
                mNotificationFlag |= Notification.FLAG_GROUP_SUMMARY;
                break;
            default:
                mNotificationFlag = 0;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_noti_flag:
                selectNotificationFlag();
                break;
            case R.id.bt_action_num:
                showActionContentView();
                break;
            case R.id.bt_base_noti:
                setBaseNotification();
                break;
            case R.id.bt_bigtext_noti:
                setBigTextNotification();
                break;
            case R.id.bt_inbox_noti:
                setInboxNotification();
                break;
            case R.id.bt_bitpictrue_noti:
                setBigPictureNotification();
                break;
            default:
                break;
        }
    }


    private void selectNotificationFlag() {
        for (boolean item : mFlagSelected) {
            item = false;
        }
        mNotificationFlag = ITEM_NONE_INT;
        if (mSelectFlagDialog != null && mSelectFlagDialog.isShowing()) {
            mSelectFlagDialog.dismiss();
        } else {
            if (mSelectFlagDialog != null) {
                mSelectFlagDialog.show();
            }
        }
    }

    private void showActionContentView() {
        LinearLayout action1 = ((LinearLayout)findViewById(R.id.noti_action1));
        action1.setVisibility(View.GONE);
        LinearLayout action2 = ((LinearLayout)findViewById(R.id.noti_action2));
        action2.setVisibility(View.GONE);
        LinearLayout action3 = ((LinearLayout)findViewById(R.id.noti_action3));
        action3.setVisibility(View.GONE);
        String actionNum = mActionNumText.getText().toString();
        Log.d(TAG,"showActionContentView  actionNum :" + actionNum);
        if (actionNum != null && !actionNum.equals("")) {
            mActionNum = Integer.valueOf(actionNum);
            if(mActionNum == 1) {
                action1.setVisibility(View.VISIBLE);
            } else if (mActionNum == 2) {
                action1.setVisibility(View.VISIBLE);
                action2.setVisibility(View.VISIBLE);
            } else if (mActionNum ==3 ) {
                action1.setVisibility(View.VISIBLE);
                action2.setVisibility(View.VISIBLE);
                action3.setVisibility(View.VISIBLE);
            }
        }
    }

    public void addNotificationAction(Notification.Builder builder) {

        if (mActionNum >= 1) {
            String actionContent1 = mAction1Text.getText().toString();
            if (actionContent1 != null && !actionContent1.equals("")) {
                builder.addAction(0,actionContent1,getDefalutIntent(Notification.FLAG_AUTO_CANCEL));
            }
        }

        if (mActionNum >=2 ) {
            String actionContent2 = mAction2Text.getText().toString();
            if (actionContent2 != null && !actionContent2.equals("")) {
                builder.addAction(0,actionContent2,getDefalutIntent(Notification.FLAG_AUTO_CANCEL));
            }
        }

        if (mActionNum >=3 ) {
            String actionContent3 = mAction3Text.getText().toString();
            if (actionContent3 != null && !actionContent3.equals("")) {
                builder.addAction(0,actionContent3,getDefalutIntent(Notification.FLAG_AUTO_CANCEL));
            }
        }
    }

    private void setBaseNotification() {
        Log.d(TAG, "setBaseNotification , Priority = " + mNotificationPriority + ",Default" + mNotificationParameter +
        ",Category= " + mNotificationCategory + ",Secret = " + mNotificationSecret);

        String title = mNotificationTitleText.getText().toString();
        String content = mNotificationContentText.getText().toString();
        String ticker = mNotificationTickerText.getText().toString();
        Log.d(TAG, "setBaseNotification, mActionNum = " + mActionNum + " , flag = "  + mNotificationFlag +
                ",title = " + title + ",content = " +  content+ ",ticker =" + ticker);
        Notification.Builder builder = getBaseNotificationBuilder();

        if (mNotificationPriority != ITEM_NONE_INT ) {
            builder.setPriority(mNotificationPriority);
        }

        if (mNotificationParameter != ITEM_NONE_INT ) {
            builder.setDefaults(mNotificationParameter);
        }

        if (!ITEM_NONE_STRING.equals(mNotificationCategory)) {
            builder.setCategory(mNotificationCategory);
        }

        if (mNotificationSecret != ITEM_NONE_INT) {
            builder.setVisibility(mNotificationSecret);
        }

        if (title != null && !title.equals("")) {
            builder.setContentTitle(title);
        }

        if (content != null && !content.equals("")) {
            builder.setContentText(content);
        }

        if (ticker != null && !ticker.equals("")) {
            builder.setTicker(ticker);
        }

        if (mActionNum > 0) {
            addNotificationAction(builder);
        }

        Notification notification = builder.build();

        if (mNotificationFlag != ITEM_NONE_INT) {
            notification.flags = mNotificationFlag;
        }

        mNotificationManager.notify(BASE_TEXT_NOTI_ID,notification);

    }

    private void setBigTextNotification() {

        Notification.Builder builder = getBaseNotificationBuilder();

        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        bigTextStyle.setBigContentTitle(getString(R.string.big_text_notification_title));
        bigTextStyle.bigText(getString(R.string.big_text_content));
        builder.setStyle(bigTextStyle);

        if (mActionNum > 0) {
            addNotificationAction(builder);
        }

        Notification notification = builder.build();
        mNotificationManager.notify(BIGTEXT_NOTI_ID,notification);

    }

    private void setInboxNotification() {
        Notification.Builder builder = getBaseNotificationBuilder();
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
        inboxStyle.setBigContentTitle(getString(R.string.inbox_notification_title));
        for (String item :mInBoxNotificationConent) {
            inboxStyle.addLine(item);
        }
        builder.setStyle(inboxStyle);
        if (mActionNum > 0) {
            addNotificationAction(builder);
        }
        Notification notification = builder.build();
        mNotificationManager.notify(INBOX_NOTI_ID,notification);
    }

    private void setBigPictureNotification() {
        Notification.Builder builder = getBaseNotificationBuilder();
        Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(getString(R.string.big_picture_notification_title));
        BitmapDrawable drawable = (BitmapDrawable)getResources().getDrawable(R.drawable.default_big);
        bigPictureStyle.bigPicture(drawable.getBitmap());
        builder.setStyle(bigPictureStyle);
        if (mActionNum > 0) {
            addNotificationAction(builder);
        }
        Notification notification = builder.build();
        mNotificationManager.notify(BIGPICTRUE_NOTI_ID,notification);
    }

}
