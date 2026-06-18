package testCases;

import apiObjectModels.Login_RequestModel;
import apiObjectModels.Logout_RequestModel;
import apiObjectModels.Register_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;

public class LogoutTests extends BaseTest{
    //Variables
    String token;

    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @BeforeMethod(alwaysRun = true)
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


    @Test(groups = {"positive"})
    public void logoutWithCorrectToken() {
        Logout_RequestModel logoutRequestModel = new Logout_RequestModel();
        logoutRequestModel
                .setTokenInRequestHeaders(token)
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("successMessages.logout"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.logout"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }

    @Test(groups = {"negative"})
    public void logoutWithIncorrectToken() {
        Logout_RequestModel logoutRequestModel = new Logout_RequestModel();
        logoutRequestModel
                .setTokenInRequestHeaders(token+"0")
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("errorMessages.logout.incorrectToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.logout.incorrectToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }

    @Test(groups = {"negative"})
    public void logoutWithEmptyToken() {
        Logout_RequestModel logoutRequestModel = new Logout_RequestModel();
        logoutRequestModel
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("errorMessages.logout.emptyToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.logout.emptyToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }
}