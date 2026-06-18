package testCases;

import apiObjectModels.GetAllNotes_RequestModel;
import apiObjectModels.Login_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

public class GetAllNotesTests extends BaseTest {
    //Variables
    String token;

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

    @Test  (groups = {"positive"})
    public void getAllNotesByToken(){
        GetAllNotes_RequestModel getAllNotesRequestModel = new GetAllNotes_RequestModel();
        getAllNotesRequestModel
                .setTokenInRequestHeaders(token)
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.getAllNotes"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getAllNotes"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getAllNotes"));

    }
    @Test  (groups = {"negative"})
    public void getAllNotesWithEmptyToken(){
        GetAllNotes_RequestModel getAllNotesRequestModel = new GetAllNotes_RequestModel();
        getAllNotesRequestModel
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getAllNotes.invalidToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getAllNotes.invalidToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getAllNotes"));

    }
}
