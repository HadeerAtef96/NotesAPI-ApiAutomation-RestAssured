package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendGetRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class GetProfile_RequestModel {
    //Variables
    Map<String, Object> headers = new HashMap<>();
    String getProfileEndpoint = "/users/profile";
    Response response;

    //Method to set Request Headers
    @Step
    public GetProfile_RequestModel setTokenInRequestHeaders(String token) {
        headers.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public GetProfile_ResponseModel sendRequestOfGetProfile() {
        response =
                sendGetRequest(
                        getPropertiesValue("apiBaseURL") + getProfileEndpoint,
                        null,
                        null,
                        headers
                );

        return new GetProfile_ResponseModel(response);
    }
}
