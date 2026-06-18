package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendGetRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class GetNote_RequestModel {
    //Variables
    String getNoteEndpoint = "/notes/{id}";
    Response response;
    Map<String , Object> requestParameters = new HashMap<>();
    Map<String, Object> requestHeaders = new HashMap<>();

    //Method to set Request Body for Post Request
    @Step
    public GetNote_RequestModel setNoteIDInRequestParameter(String id){
        requestParameters.put("id",id);
        return this;
    }

    //Method to set Request Headers
    @Step
    public GetNote_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public GetNote_ResponseModel sendRequestOfGetNote() {
        response =
        sendGetRequest(
                getPropertiesValue("apiBaseURL")+getNoteEndpoint,
                "pathParameter",
                requestParameters,
                requestHeaders

        );
        return new GetNote_ResponseModel(response);
    }
}
