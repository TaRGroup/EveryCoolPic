package com.targroup.everycoolpic.wallpaper;

import android.app.Activity;
import android.os.Bundle;

import com.targroup.everycoolpic.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .add(R.id.activity_settings, new SettingsFragment())
                .commitAllowingStateLoss();
    }
}
