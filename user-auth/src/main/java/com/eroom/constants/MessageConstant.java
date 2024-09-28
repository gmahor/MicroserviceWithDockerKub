package com.eroom.constants;


public class MessageConstant {

    private MessageConstant() {
        throw new IllegalArgumentException("MessageConstant Is a utility class");
    }

    public static final String REQUEST_ERROR = "Error In Request:: {}";
    public static final String CAPTCHA_CREATED_SUCCESSFULLY = "Captcha Created Successfully.";
    public static final String ERROR_CREATING_CAPTCHA = "Error While Creating Captcha.";
    public static final String ERROR_WHILE_CREATING_TOKEN = "Error While Creating Token";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String USER_NOT_FOUND_WITH_THIS_USERNAME = "User Not Found With This Username";
    public static final String CAPTCHA_NOT_VALID = "Captcha Not Valid";
    public static final String SIGN_IN_SUCCESS = "Sign In Success";
    public static final String PASSWORD_INCORRECT = "Password Incorrect";
    public static final String ERROR_WHILE_SIGN_IN = "Error While Sign In";
    public static final String TOKEN_IS_EXPIRED = "Token Is Expired.";
    public static final String INVALID_TOKEN = "Token Is Invalid.";
    public static final String NO_CLAMS_FOUND_WITH_THIS_TOKEN = "No Claims Found With This Token.";
    public static final String REFRESH_TOKEN_CREATED_SUCCESSFULLY = "Refresh Token Created Successfully.";
    public static final String ERROR_CREATING_REFRESH_TOKEN = "Error While Creating Refresh Token.";
    public static final String USER_LOGOUT_SUCCESS = "Logged Out Successfully.";
    public static final String ERROR_LOGOUT_USER = "Error While Logout User.";
    public static final String USER_NOT_FOUND = "User Not Found.";

}