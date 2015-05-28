package com.vivo.test.systemui.activity.systemstatus;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.nfc.NfcAdapter;
import android.content.Intent;


import com.vivo.test.systemui.R;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SystemStatusTestActivity extends PreferenceActivity {
    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    private CheckBoxPreference mHifiCheckBox;
    private CheckBoxPreference mNfcCheckBox;
    private CheckBoxPreference mSpsCheckBox;
    private CheckBoxPreference mSmartCheckBox;
    private CheckBoxPreference mVistorCheckBox;
    private CheckBoxPreference mFaceCheckBox;
    private CheckBoxPreference mGestureCheckBox;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.

        //add-start by zhangliang
        // Add 'HIFI' preferences, and a corresponding header.
        addPreferencesFromResource(R.xml.pref_mian);

        /*
        PreferenceCategory hifiHeader = new PreferenceCategory(this);
        hifiHeader.setTitle(R.string.pref_header_hifi);
        getPreferenceScreen().addPreference(hifiHeader);
        mHifiCheckBox = new CheckBoxPreference(this);
        mHifiCheckBox.setTitle(R.string.pref_content_hifi);
        getPreferenceScreen().addPreference(mHifiCheckBox);

        // Add 'NFC' preferences, and a corresponding header.
        PreferenceCategory nfcHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_nfc);
        getPreferenceScreen().addPreference(nfcHeader);
        mNfcCheckBox = new CheckBoxPreference(this);
        mNfcCheckBox.setTitle(R.string.pref_content_nfc);
        getPreferenceScreen().addPreference(mNfcCheckBox);

        // Add 'SPS' preferences, and a corresponding header.
        PreferenceCategory spsHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_sps);
        getPreferenceScreen().addPreference(spsHeader);
        mSpsCheckBox = new CheckBoxPreference(this);
        mSpsCheckBox.setTitle(R.string.pref_content_sps);
        getPreferenceScreen().addPreference(mSpsCheckBox);

        // Add 'Smart Pause' preferences, and a corresponding header.
        PreferenceCategory smartHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_smart_pause);
        getPreferenceScreen().addPreference(smartHeader);
        mSmartCheckBox = new CheckBoxPreference(this);
        mSmartCheckBox.setTitle(R.string.pref_content_smart_pause);
        getPreferenceScreen().addPreference(mSmartCheckBox);

        // Add 'vistor mode ' preferences, and a corresponding header.
        PreferenceCategory vistorHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_vistor_mode);
        getPreferenceScreen().addPreference(vistorHeader);
        mVistorCheckBox = new CheckBoxPreference(this);
        mVistorCheckBox.setTitle(R.string.pref_content_vistor_mode);
        getPreferenceScreen().addPreference(mVistorCheckBox);

        // Add 'face dect  ' preferences, and a corresponding header.
        PreferenceCategory faceHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_face_dect);
        getPreferenceScreen().addPreference(faceHeader);
        mFaceCheckBox = new CheckBoxPreference(this);
        mFaceCheckBox.setTitle(R.string.pref_content_face_dect);
        getPreferenceScreen().addPreference(mFaceCheckBox);

        // Add 'gesture dect  ' preferences, and a corresponding header.
        PreferenceCategory gestureHeader = new PreferenceCategory(this);
        nfcHeader.setTitle(R.string.pref_header_gesture);
        getPreferenceScreen().addPreference(gestureHeader);
        mGestureCheckBox = new CheckBoxPreference(this);
        mGestureCheckBox.setTitle(R.string.pref_content_gesture);
        getPreferenceScreen().addPreference(mGestureCheckBox);
        */

        mFaceCheckBox = (CheckBoxPreference)findPreference("face_pref");
        mGestureCheckBox = (CheckBoxPreference)findPreference("gesture_pref");
        mHifiCheckBox = (CheckBoxPreference)findPreference("hifi_pref");
        mNfcCheckBox = (CheckBoxPreference)findPreference("nfc_pref");
        mSmartCheckBox = (CheckBoxPreference)findPreference("smart_pref");
        mSpsCheckBox = (CheckBoxPreference)findPreference("sps_pref");
        mVistorCheckBox = (CheckBoxPreference)findPreference("vistor_pref");

        bindPreferenceSummaryToValue(mFaceCheckBox);
        bindPreferenceSummaryToValue(mGestureCheckBox);
        bindPreferenceSummaryToValue(mHifiCheckBox);
        bindPreferenceSummaryToValue(mNfcCheckBox);
        bindPreferenceSummaryToValue(mSmartCheckBox);
        bindPreferenceSummaryToValue(mSpsCheckBox);
        bindPreferenceSummaryToValue(mVistorCheckBox);
        //add-end by zhangliang
    }

    /** {@inheritDoc} */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
        & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Determines whether the simplified settings UI should be shown. This is
     * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
     * doesn't have newer APIs like {@link PreferenceFragment}, or the device
     * doesn't have an extra-large screen. In these cases, a single-pane
     * "simplified" settings UI should be shown.
     */
    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }

    /** {@inheritDoc} */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {

    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private  Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            if (preference instanceof CheckBoxPreference) {
                boolean newValue = !((CheckBoxPreference)preference).isChecked();
                if (preference == mVistorCheckBox ) {
                    Intent intent = new Intent("android.settings.VisitMode.action.TURN_ON");
                    intent.putExtra("visit_mode", newValue ? 1 :0);
                    SystemStatusTestActivity.this.sendBroadcast(intent);

                } else if (preference == mNfcCheckBox) {
                    Intent intent = new Intent(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
                    intent.putExtra(NfcAdapter.EXTRA_ADAPTER_STATE, newValue ? NfcAdapter.STATE_ON : NfcAdapter.STATE_OFF);
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                } else if (preference == mSpsCheckBox) {
                    Intent intent = new Intent("intent.action.super_power_save_send");
                    intent.putExtra("sps_action", newValue ? "entered" : "stop");
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                } else if (preference == mHifiCheckBox) {
                    Intent intent = new Intent("com.bbk.audiofx.hifi.display");
                    intent.putExtra("state", newValue);
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                } else if (preference == mSmartCheckBox) {
                    Intent intent = new Intent("action.android.smartpausevideo.notification");
                    intent.putExtra("notificationstate", newValue);
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                } else if (preference == mFaceCheckBox) {
                    Intent intent = new Intent("com.android.action.FACE__DETECTION");
                    intent.putExtra("show", newValue);
                    intent.putExtra("begin",false);
                    intent.putExtra("has_face", true);
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                } else if (preference == mGestureCheckBox) {
                    Intent intent = new Intent("android.vivo.cameragesture.action.GESTURE_ON_OFF");
                    intent.putExtra("onoff", newValue);
                    SystemStatusTestActivity.this.sendBroadcast(intent);
                }
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private  void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
    }
}
