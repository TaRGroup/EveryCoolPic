package com.targroup.everycoolpic.wallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.targroup.everycoolpic.R;
import com.targroup.everycoolpic.util.Utils;

/**
 * Created by liangyuteng0927 on 17-1-27.
 * Email: liangyuteng12345@gmail.com
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.wallpaper);
        findPreference("key_start_now").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startService(new Intent(getActivity(), WallpaperService.class));
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                return false;
            }
        });
        final SwitchPreference enable = (SwitchPreference)findPreference("key_enable");
        enable.setChecked(Utils.isEnable(getActivity()));
        enable.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Utils.setEnable(enable.isChecked(), getActivity());
                return false;
            }
        });
        findPreference("key_source").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/TaRGroup/EveryCoolPic")));
                } catch (Exception ignore) {}
                return false;
            }
        });
    }

}
