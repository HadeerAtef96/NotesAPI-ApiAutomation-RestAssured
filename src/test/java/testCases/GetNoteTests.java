package testCases;

import apiObjectModels.GetNote_RequestModel;
import apiObjectModels.Login_RequestModel;
import apiObjectModels.Logout_RequestModel;
import apiObjectModels.Register_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;
import static utils.DataGenerator.generateRandomFullName;
import static utils.DataGenerator.generateRandomPassword;

public class GetNoteTests extends BaseTest {
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
                                json.readTestData("userData2.email"),
                                json.readTestData("userData2.password")
                        )
                        .sendRequestOfLogin()
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.login"))
                        .readTokenFromResponse();
    }

    @Test(groups = {"positive"})
    public void getNoteByIdWithCorrectToken() {
        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
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
        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
                .setTokenInRequestHeaders(token+"0")
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

        Register_RequestModel registerRequestModel = new Register_RequestModel();
        registerRequestModel
                .prepareRequestBodyOfRegister(randomName, randomEmail, randomPassword)
                .sendRequestOfRegister();

       Login_RequestModel loginRequestModel = new Login_RequestModel();
        String token2 =
                loginRequestModel
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
        Logout_RequestModel logoutRequestModel = new Logout_RequestModel();
        logoutRequestModel
                .setTokenInRequestHeaders(token)
                .sendRequestOfLogout()
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.logout"));

        GetNote_RequestModel getNoteRequestModel = new GetNote_RequestModel();
        getNoteRequestModel
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(json.readTestData("noteInfo.id"))
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }
}
