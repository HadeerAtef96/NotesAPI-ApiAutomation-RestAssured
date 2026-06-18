package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class Register_RequestModel {
    //Variables
    Map<String, Object> requestBody = new HashMap<>();;
    String registerEndpoint = "/users/register";
    Response response;

    //Method to set Request Body
    @Step
    public Register_RequestModel prepareRequestBodyOfRegister(String name, String email, String password) {
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);

        return this;
    }

    //Method to set Request Headers

    //Method to set Request Token or Authentication

    //Method to send the Request and Receive the Response
    @Step
    public Register_ResponseModel sendRequestOfRegister() {
        response = sendApiRequest(
                "post",
                getPropertiesValue("apiBaseURL") + registerEndpoint,
                "application/x-www-form-urlencoded",
                requestBody,
                null
        );

        return new Register_ResponseModel(response);
    }
}
