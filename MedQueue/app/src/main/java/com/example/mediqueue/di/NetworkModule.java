package com.example.mediqueue.di;

import android.content.Context;
import com.example.mediqueue.api.ApiService;
import com.example.mediqueue.api.RetrofitClient;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

/**
 * Hilt module for providing network dependencies
 */
@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public ApiService provideApiService(@ApplicationContext Context context) {
        return RetrofitClient.getApiService(context);
    }
}
