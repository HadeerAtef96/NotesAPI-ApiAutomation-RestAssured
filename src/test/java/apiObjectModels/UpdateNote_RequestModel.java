package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class UpdateNote_RequestModel {
    //Variables
    String updateNoteEndpoint = "/notes/";
    String noteId;
    Response response;
    Map<String , Object> requestBody = new HashMap<>();
    Map<String, Object> requestHeaders = new HashMap<>();

    //Method to set Request Body for Post Request
    @Step
    public UpdateNote_RequestModel prepareRequestBodyForUpdateNote(String title , String description, String category, String completed){
        requestBody.put("title",title);
        requestBody.put("description",description);
        requestBody.put("category",category);
        requestBody.put("completed",completed);
        return this;
    }

    //Method to set Request Headers
    @Step
    public UpdateNote_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to set Note Id in URL
    @Step
    public UpdateNote_RequestModel setNoteIdAsPathParameter (String noteId){
        this.noteId = noteId;
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public UpdateNote_ResponseModel sendRequestOfUpdateNote() {
        response =
        sendApiRequest(
                "put",
                getPropertiesValue("apiBaseURL")+updateNoteEndpoint+noteId,
                "application/x-www-form-urlencoded",
                requestBody,
                requestHeaders
        );
        return new UpdateNote_ResponseModel(response);
    }
}
