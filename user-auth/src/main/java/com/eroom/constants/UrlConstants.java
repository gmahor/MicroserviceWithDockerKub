package com.eroom.constants;


public class UrlConstants {

    private UrlConstants() {
        throw new IllegalArgumentException("UrlConstants is a utility class");
    }

    // BASE URLS
    public static final String BASE_API_V1_AUTH = "/api/v1/auth";

    //SWAGGER API URLS
    public static final String SWAGGER_DOCS_URL = "/v3/api-docs/**";
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";

    // USER DEFINE API URLS
    public static final String ADD_USER = "/addUser";
    public static final String GET_ALL_USER = "/getAllUsers";
    public static final String SIGN_IN = "/signIn";
    public static final String LOGOUT = "/logout";
}



