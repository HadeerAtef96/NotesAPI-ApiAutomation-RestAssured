package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class Logout_RequestModel {
    //Variables
    Map<String, Object> requestHeaders = new HashMap<>();;
    String logoutEndpoint = "/users/logout";
    Response response;

    //Method to set Request Body

    //Method to set Request Headers
    @Step
    public Logout_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public Logout_ResponseModel sendRequestOfLogout() {
        response =
        sendApiRequest(
                "delete",
                getPropertiesValue("apiBaseURL")+ logoutEndpoint,
                null,
                null,
                requestHeaders
                );

        return new Logout_ResponseModel(response);
    }
}
