package com.example.mediqueue;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

/**
 * MediQueue Application class
 * Entry point for the application
 */
@HiltAndroidApp
public class MediQueueApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
