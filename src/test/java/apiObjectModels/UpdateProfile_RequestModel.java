package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class UpdateProfile_RequestModel {
    //Variables
    String UpdateEndpoint = "/users/profile";
    Map<String, Object> requestBody = new HashMap<>();
    Map<String, Object> headers = new HashMap<>();
    Response response;

    //Method to set Request Body for Post Request
    @Step
    public UpdateProfile_RequestModel prepareRequestBodyOfUpdateProfile (String name , String phone , String company ){
        requestBody.put("name",name);
        requestBody.put("phone",phone);
        requestBody.put("company",company);
        return this;
    };

    //Method to set Request Headers
    @Step
    public UpdateProfile_RequestModel setTokenInRequestHeaders(String token) {
        headers.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public UpdateProfile_ResponseModel sendRequestOfUpdateProfile() {
        response =
                sendApiRequest(
                        "patch",
                        getPropertiesValue("apiBaseURL")+UpdateEndpoint,
                        "application/x-www-form-urlencoded",
                        requestBody,
                        headers
                );
        return new UpdateProfile_ResponseModel (response);
    }
}
