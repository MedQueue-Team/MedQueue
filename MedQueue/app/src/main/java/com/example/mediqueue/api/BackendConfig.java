package com.example.mediqueue.api;

import com.example.mediqueue.BuildConfig;

public final class BackendConfig {

    public static final String DEFAULT_BASE_URL = BuildConfig.DEBUG
            ? "http://10.114.136.128:8080/api/v1/"
            : "https://mediqueue-api.example.com/api/v1/";

    private BackendConfig() {
    }

    public static String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            return DEFAULT_BASE_URL;
        }

        String normalized = baseUrl.trim();
        return normalized.endsWith("/") ? normalized : normalized + "/";
    }
}
