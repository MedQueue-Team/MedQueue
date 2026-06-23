package com.example.mediqueue.api;

import android.content.Context;
import com.example.mediqueue.core.SessionManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    private static String API_BASE_URL = BackendConfig.DEFAULT_BASE_URL;

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            SessionManager sessionManager = new SessionManager(context);
            
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        String token = sessionManager.getAuthToken();
                        
                        if (token != null && !token.isEmpty()) {
                            Request request = original.newBuilder()
                                    .header("Authorization", "Bearer " + token)
                                    .build();
                            return chain.proceed(request);
                        }
                        return chain.proceed(original);
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(Context context) {
        return getRetrofitInstance(context).create(ApiService.class);
    }

    /**
     * Call this method to update the base URL for physical devices
     * Example: setBaseUrl("http://192.168.1.100:8080/")
     */
    public static void setBaseUrl(String newBaseUrl) {
        if (newBaseUrl == null || newBaseUrl.isEmpty()) return;
        API_BASE_URL = BackendConfig.normalizeBaseUrl(newBaseUrl);
        retrofit = null;  // Reset so next call will recreate Retrofit with new URL
    }
}
