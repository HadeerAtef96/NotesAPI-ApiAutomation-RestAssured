package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static utils.APIsManager.sendApiRequest;
import static utils.PropertiesReader.getPropertiesValue;

public class DeleteNote_RequestModel {
    //Variables
    String deleteNoteEndpoint = "/notes/";
    String noteId;
    Response response;
    Map<String, Object> requestHeaders = new HashMap<>();

    //Method to set Request Headers
    @Step
    public DeleteNote_RequestModel setTokenInRequestHeaders(String token){
        requestHeaders.put("x-auth-token", token);
        return this;
    }

    //Method to set Note Id in URL
    @Step
    public DeleteNote_RequestModel setNoteIdAsPathParameter (String noteId){
        this.noteId = noteId;
        return this;
    }

    //Method to send the Request and Receive the Response
    @Step
    public DeleteNote_ResponseModel sendRequestOfDeleteNote() {
        response =
        sendApiRequest(
                "delete",
                getPropertiesValue("apiBaseURL")+deleteNoteEndpoint+noteId,
                null,
                null,
                requestHeaders
        );
        return new DeleteNote_ResponseModel(response);
    }
}
