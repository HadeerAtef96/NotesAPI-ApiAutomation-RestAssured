package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class CreateNote_RequestModel {
    //Variables
    String createNoteEndpoint = "/notes";
    Response response;
    Map<String , Object> requestBody = new HashMap<>();
    Map<String, Object> requestHeaders = new HashMap<>();

    //Method to set Request Body for Post Request
    @Step
    public CreateNote_RequestModel prepareRequestBodyForCreateNote(String title , String description, String category){
        requestBody.put("title",title);
        requestBody.put("description",description);
        requestBody.put("category",category);
        return this;
    }

    //Method to set Request Headers
    @Step
    public CreateNote_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public CreateNote_ResponseModel sendRequestOfCreateNote() {
        response =
        sendApiRequest(
                "post",
                getPropertiesValue("apiBaseURL")+createNoteEndpoint,
                "application/x-www-form-urlencoded",
                requestBody,
                requestHeaders
        );
        return new CreateNote_ResponseModel(response);
    }
}
