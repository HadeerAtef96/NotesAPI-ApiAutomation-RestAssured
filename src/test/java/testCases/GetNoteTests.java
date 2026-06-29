package testCases;

import apiObjectModels.GetNote_RequestModel;
import apiObjectModels.Login_RequestModel;
import apiObjectModels.Logout_RequestModel;
import apiObjectModels.Register_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;

public class GetNoteTests extends BaseTest {
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
    public void getNoteByIdWithCorrectToken() {

        new GetNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(json.readTestData("noteInfo.id"))
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.getNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"))
                .verifyTitleFromResponse(json.readTestData("noteInfo.title"))
                .verifyDescriptionFromResponse(json.readTestData("noteInfo.description"))
                .verifyCategoryFromResponse(json.readTestData("noteInfo.category"))
                .verifyCompletedFlagFromResponse(json.readTestData("noteInfo.completed"));
    }

    @Test(groups = {"negative"})
    public void getNoteByIdWithIncorrectToken() {

        new GetNote_RequestModel()
                .setTokenInRequestHeaders(token + "0")
                .setNoteIDInRequestParameter(json.readTestData("noteInfo.id"))
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }

    @Test(groups = {"negative"})
    public void getNoteByIdWithAnotherToken() {
        String randomEmail = generateRandomEmail();
        String randomName = generateRandomFullName();
        String randomPassword = generateRandomPassword();

        new Register_RequestModel()
                .prepareRequestBodyOfRegister(randomName, randomEmail, randomPassword)
                .sendRequestOfRegister();


        String token2 =
                new Login_RequestModel()
                        .prepareRequestBodyForLogin(randomEmail, randomPassword)
                        .sendRequestOfLogin()
                        .readTokenFromResponse();

        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
                .setTokenInRequestHeaders(token2)
                .setNoteIDInRequestParameter(json.readTestData("noteInfo.id"))
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidID"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidID"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }

    @Test(groups = {"negative"})
    public void getNoteByIdAfterLogout() {

        new Logout_RequestModel()
                .setTokenInRequestHeaders(token)
                .sendRequestOfLogout()
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.logout"));

        new GetNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(json.readTestData("noteInfo.id"))
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }
}
