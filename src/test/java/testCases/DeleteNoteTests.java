package testCases;

import apiObjectModels.CreateNote_RequestModel;
import apiObjectModels.DeleteNote_RequestModel;
import apiObjectModels.GetNote_RequestModel;
import apiObjectModels.Login_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.util.List;

import static utils.DataGenerator.generateItemFromList;
import static utils.DataGenerator.generateRandomFullName;

public class DeleteNoteTests extends BaseTest{
    //Variables
    String token;
    String noteID;

    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @BeforeMethod  (alwaysRun = true)
    public void loginWithExistingEmail() {
        Login_RequestModel loginRequestModel = new Login_RequestModel();
        token =
                loginRequestModel
                        .prepareRequestBodyForLogin(
                                json.readTestData("userData.email"),
                                json.readTestData("userData.password")
                        )
                        .sendRequestOfLogin()
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.login"))
                        .readTokenFromResponse();
    }

    @BeforeMethod (dependsOnMethods = "loginWithExistingEmail" , alwaysRun = true)
    public void createNewNoteWithRandomData(){
        String title = generateRandomFullName();
        String description = generateRandomFullName();
        String category = generateItemFromList(List.of("Personal","Home","Work"));

        CreateNote_RequestModel createNoteRequestModel = new CreateNote_RequestModel();
        noteID =
                createNoteRequestModel
                        .prepareRequestBodyForCreateNote(title,description,category)
                        .setTokenInRequestHeaders(token)
                        .sendRequestOfCreateNote()
                        .readNoteIdFromResponse();
    }

    @Test  (groups = {"positive"})
    public void deleteNoteByID(){
        DeleteNote_RequestModel deleteNoteRequestModel= new DeleteNote_RequestModel();
        deleteNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID)
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.deleteNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.deleteNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));
    }

    @Test  (groups = {"negative"})
    public void deleteNoteByInvalidID(){
        DeleteNote_RequestModel deleteNoteRequestModel= new DeleteNote_RequestModel();
        deleteNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID+"0")
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.deleteNote.incorrectId"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.deleteNote.incorrectId"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));
    }

    @Test  (groups = {"negative"})
    public void getNoteByIDAfterDelete(){
        DeleteNote_RequestModel deleteNoteRequestModel= new DeleteNote_RequestModel();
        deleteNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID)
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.deleteNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.deleteNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));

        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(noteID)
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidID"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidID"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }
}
