package testCases;

import apiObjectModels.CreateNote_RequestModel;
import apiObjectModels.GetNote_RequestModel;
import apiObjectModels.Login_RequestModel;
import apiObjectModels.UpdateNote_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.util.List;

import static utils.DataGenerator.generateItemFromList;
import static utils.DataGenerator.generateRandomFullName;

public class UpdateNoteTests extends BaseTest{
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
    public void updateNoteWithRandomData(){
        String title = generateRandomFullName();
        String description = generateRandomFullName();
        String category = generateItemFromList(List.of("Personal","Home","Work"));
        String completedFlag =  generateItemFromList(List.of("true","false"));

        UpdateNote_RequestModel updateNoteRequestModel = new UpdateNote_RequestModel();
        updateNoteRequestModel
                .prepareRequestBodyForUpdateNote(title,description,category,completedFlag)
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID)
                .sendRequestOfUpdateNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.updateNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.updateNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.updateNote"));

        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(noteID)
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.getNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"))
                .verifyTitleFromResponse(title)
                .verifyDescriptionFromResponse(description)
                .verifyCategoryFromResponse(category)
                .verifyCompletedFlagFromResponse(completedFlag);
    }

    @Test  (groups = {"negative"})
    public void updateNoteIncorrectId() {
        String title = generateRandomFullName();
        String description = generateRandomFullName();
        String category = generateItemFromList(List.of("Personal", "Home", "Work"));
        String completedFlag = generateItemFromList(List.of("true", "false"));

        UpdateNote_RequestModel updateNoteRequestModel = new UpdateNote_RequestModel();
        updateNoteRequestModel
                .prepareRequestBodyForUpdateNote(title, description, category, completedFlag)
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID + "0")
                .sendRequestOfUpdateNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.updateNote.incorrectId"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.updateNote.incorrectId"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.updateNote"));

    }
}
