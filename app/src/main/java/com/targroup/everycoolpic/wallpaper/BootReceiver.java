package com.targroup.everycoolpic.wallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.targroup.everycoolpic.util.Utils;

/**
 * Created by liangyuteng0927 on 17-1-27.
 * Email: liangyuteng12345@gmail.com
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext())
                .getBoolean("key_enable",false)) {
            Utils.setEnable(true, context);
        }
    }
}
