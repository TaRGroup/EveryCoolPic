package com.targroup.everycoolpic.wallpaper;

import android.app.IntentService;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.targroup.everycoolpic.api.ApiManager;
import com.targroup.everycoolpic.model.PictureEntity;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import io.reactivex.functions.Consumer;

/**
 * Created by liangyuteng0927 on 17-1-27.
 * Email: liangyuteng12345@gmail.com
 */

public class WallpaperService extends IntentService {
    public WallpaperService () {
        super("WallpaperService");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Consumer<List<PictureEntity>> mConsumer = new Consumer<List<PictureEntity>>() {
            @Override
            public void accept(List<PictureEntity> pictureEntities) throws Exception {
                if (pictureEntities == null || pictureEntities.size() == 0)
                    return;
                int max=pictureEntities.size();
                int min=0;
                Random random = new Random();

                int s = random.nextInt(max)%(max-min+1) + min;
                ImageLoader.getInstance()
                        .loadImage(pictureEntities.get(s).getPic()
                                , new DisplayImageOptions.Builder()
                                        .cacheOnDisk(true).build()
                                , new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String imageUri, View view) {

                                    }

                                    @Override
                                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                                    }

                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperService.this);
                                        try {
                                            wallpaperManager.setBitmap(loadedImage);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onLoadingCancelled(String imageUri, View view) {

                                    }
                                });
            }
        };
        ApiManager.getInstance()
                .pictureList(PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString("key_type", "splash"))
                .subscribe(mConsumer);
    }
}
