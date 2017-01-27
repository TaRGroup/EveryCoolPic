package com.targroup.everycoolpic;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by liangyuteng0927 on 17-1-25.
 * Email: liangyuteng12345@gmail.com
 */

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(getCacheDir()))
                .build());
    }
    public static Context getContext () {
        return mContext;
    }
}
