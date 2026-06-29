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

    @BeforeMethod(alwaysRun = true)
    public void loginWithExistingEmail() {

        token =
                new Login_RequestModel()
                        .prepareRequestBodyForLogin(
                                json.readTestData("userData2.email"),
                                json.readTestData("userData2.password")
                        )
                        .sendRequestOfLogin()
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.login"))
                        .readTokenFromResponse();
    }

    @Test(groups = {"positive"})
    public void getAllNotesByToken() {

        new GetAllNotes_RequestModel()
                .setTokenInRequestHeaders(token)
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.getAllNotes"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getAllNotes"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getAllNotes"));

    }

    @Test(groups = {"negative"})
    public void getAllNotesWithEmptyToken() {

        new GetAllNotes_RequestModel()
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getAllNotes.invalidToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getAllNotes.invalidToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getAllNotes"));

    }
}
