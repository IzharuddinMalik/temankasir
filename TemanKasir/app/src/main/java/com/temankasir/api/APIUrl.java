package com.temankasir.api;

public class APIUrl {
    // ini adalah IP localhost.
    public static final String BASE_URL_API = "https://sinarcenter.xyz/apitemankasir/apici/api/user/";

    public static BaseApiInterface getAPIService(){
        return APIClient.getClient(BASE_URL_API).create(BaseApiInterface.class);
    }
}
