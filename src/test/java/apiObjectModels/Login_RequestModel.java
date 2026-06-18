package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class Login_RequestModel {
    //Variables
    Map<String, Object> requestBody = new HashMap<>();;
    String loginEndpoint = "/users/login";
    Response response;

    //Method to set Request Body
    @Step
    public Login_RequestModel prepareRequestBodyForLogin(String email, String password) {
        requestBody.put("email", email);
        requestBody.put("password", password);
        return this;
    }

    //Method to set Request Headers

    //Method to set Request Token or Authentication

    //Method to send the Request and Receive the Response
    @Step
    public Login_ResponseModel sendRequestOfLogin() {
        response =
        sendApiRequest(
                "post",
                getPropertiesValue("apiBaseURL")+loginEndpoint,
                "application/x-www-form-urlencoded",
                requestBody,
                null
                );

        return new Login_ResponseModel(response);
    }
}
