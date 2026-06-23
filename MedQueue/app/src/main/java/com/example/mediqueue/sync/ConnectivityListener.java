package com.example.mediqueue.sync;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class ConnectivityListener {
    private static final String TAG = "ConnectivityListener";
    private final ConnectivityManager connectivityManager;
    private final SyncManager syncManager;

    @Inject
    public ConnectivityListener(@ApplicationContext Context context, SyncManager syncManager) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.syncManager = syncManager;
    }

    public void register() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                Log.d(TAG, "Network available. Triggering sync...");
                syncManager.scheduleImmediateSync();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                Log.d(TAG, "Network lost.");
            }
        });
    }
}
