package com.example.hardwarestore;


public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ITEMS = API_PATH +  "/items";
    public static final String CART = API_PATH + "/cart";
    public static final String ORDER = API_PATH + "/order";
    public static final String ENTITY = "/{id}";
    public static final String USER = "/{userId}";
    public static final String EXPORT_TYPE = "/export/{type}";
//TODO: poate o sa am nevoie de un path pt sell sau cv, vad cu timpul
    public static final String SEARCH = "/{string}";
    public static final String FIRST_TEST = "/firstTest";

    public static final String REVIEWS = "/reviews";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";

}
