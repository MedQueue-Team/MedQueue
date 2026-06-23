package com.clinical.manager.health.checks.Data.API;



/*
 * Stores backend API endpoints.
 */
public class ApiRoutes {

    /*
     * Base server URL.
     */
    public static final String BASE_URL =
            "http://10.0.2.2:8080/api/";

    /*
     * Authentication APIs.
     */
    public static final String LOGIN =
            BASE_URL + "auth/login";

    /*
     * Queue APIs.
     */
    public static final String REGISTER_PATIENT =
            BASE_URL + "queue/register";

    public static final String FETCH_QUEUE =
            BASE_URL + "doctor/queue";

    /*
     * Admin dashboard.
     */
    public static final String ADMIN_DASHBOARD =
            BASE_URL + "admin/dashboard";
}