/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.targroup.everycoolpic.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;

import com.targroup.everycoolpic.App;
import com.targroup.everycoolpic.wallpaper.WallpaperService;

import java.util.Locale;
import java.util.UUID;

/**
 * Class containing some static utility methods.
 */
public class Utils {

    public static String getUserAgent() {
        StringBuilder stringBuilder = new StringBuilder();
        String str = System.getProperty("http.agent");
        stringBuilder.append(str).append(" (#Build; ").append(Build.BRAND).append("; ").append(Build.MODEL).append("; ").append(Build.DISPLAY).append("; ").append(Build.VERSION.RELEASE).append(")");
        stringBuilder.append(" +CoolMarket/7.3");
        return Html.escapeHtml(stringBuilder.toString());
    }

    public static String getLocaleString() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    public static String getUUID() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        if (sp.contains("uuid")) {
            return sp.getString("uuid", "");
        }
        String uuid = UUID.randomUUID().toString();
        sp.edit().putString("uuid", uuid).apply();
        return uuid;
    }

    public static boolean isEnable (Context context) {
        //AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, WallpaperService.class), PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }
    public static void setEnable (boolean enable, Context context) {
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, new Intent(context, WallpaperService.class), 0);
        if (!enable) {
            manager.cancel(pendingIntent);
        } else {
            manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
}
