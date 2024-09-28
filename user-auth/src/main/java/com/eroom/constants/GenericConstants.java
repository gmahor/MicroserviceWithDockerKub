package com.eroom.constants;

public class GenericConstants {

    private GenericConstants() {
        throw new IllegalArgumentException("GenericConstants is a utility class");
    }

    public static final String TOKEN_MESSAGE = "token_message";

    // Swagger
    public static final String AUTHORIZATION = "Authorization";
    public static final String HEADER = "header";
    public static final String GLOBAL = "global";
    public static final String ACCESS_EVERYTHING = "accessEverything";

    // User Define
    public static final String USER_STATUS = "1";
    public static final String JWT_TOKEN_CLAIM = "userDetails";
    public static final int LOGIN_HISTORIES_LIMIT = 10;
    public static final String E_ROOM_ADMIN_USERNAME = "eRoomAdmin";
}
