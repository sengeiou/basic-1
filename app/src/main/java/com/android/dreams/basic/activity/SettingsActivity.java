package com.android.dreams.basic.activity;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.provider.Settings;

import com.android.dreams.basic.R;
import com.android.dreams.basic.util.SharedPreferenceUtil;
import com.lunzn.tool.log.LogUtil;


public class SettingsActivity extends PreferenceActivity {

    private static final String TAG = "SettingsActivity";
    private ListPreference listPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        boolean isOpen = SharedPreferenceUtil.getBoolean("screen_switch", true);
        LogUtil.d(TAG, "获取存储的开关值： " + isOpen);

        SwitchPreference sp = (SwitchPreference) findPreference("screen_switch");
        sp.setChecked(isOpen);
        sp.setSummary(isOpen ? "开" : "关");
        sp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                LogUtil.d(TAG, "设置 screen_switch " + o);
                boolean temp = (boolean) o;
                if (temp) {

                } else {

                }
                SharedPreferenceUtil.set("screen_switch", temp);
                preference.setSummary(temp ? "开" : "关");
                return true;
            }
        });

        int timeout_index = SharedPreferenceUtil.getInt("screen_timeout", 0);
//        int timeout_index  = Settings.System.getInt(getContentResolver(),
//                android.provider.Settings.System.SCREEN_OFF_TIMEOUT,0);
        LogUtil.d(TAG, "获取存储的timeout_index ： " + timeout_index);
        String[] values = getResources().getStringArray(R.array.screen_timeout_values);
        String[] entries = getResources().getStringArray(R.array.screen_timeout_entries);
//        for (int i = 0; i < values.length; i++) {
//            if (values[i].equalsIgnoreCase((String) o)){
//                SharedPreferenceUtil.set("screen_timeout", i);
//                preference.setSummary(entries[i]);
//            }
//        }

        listPreference = (ListPreference) findPreference("screen_timeout");
        listPreference.setSummary(entries[timeout_index]);
        listPreference.setValueIndex(timeout_index);

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                LogUtil.d(TAG, "listPreference o " + o);
                for (int i = 0; i < values.length; i++) {
                    if (values[i].equalsIgnoreCase((String) o)) {
                        SharedPreferenceUtil.set("screen_timeout", i);
                        preference.setSummary(entries[i]);
                    }
                }
                Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_OFF_TIMEOUT, Integer.valueOf((String) o));
                return true;
            }
        });

    }
}
