package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendGetRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class GetAllNotes_RequestModel {
    //Variables
    String getAllNotesEndpoint = "/notes";
    Response response;
    Map<String, Object> requestHeaders = new HashMap<>();

    //Method to set Request Headers
    @Step
    public GetAllNotes_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public GetAllNotes_ResponseModel sendRequestOfGetNote() {
        response =
        sendGetRequest(
                getPropertiesValue("apiBaseURL")+getAllNotesEndpoint,
                null,
                null,
                requestHeaders
        );
        return new GetAllNotes_ResponseModel(response);
    }
}
