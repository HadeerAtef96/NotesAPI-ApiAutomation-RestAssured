package testCases;

import apiObjectModels.Login_RequestModel;
import apiObjectModels.Logout_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

public class LogoutTests extends BaseTest {
    //Variables
    String token;

    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @BeforeMethod(alwaysRun = true)
    public void loginWithExistingEmail() {

        token =
                new Login_RequestModel()
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

        new Logout_RequestModel()
                .setTokenInRequestHeaders(token)
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("successMessages.logout"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.logout"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }

    @Test(groups = {"negative"})
    public void logoutWithIncorrectToken() {

        new Logout_RequestModel()
                .setTokenInRequestHeaders(token + "0")
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("errorMessages.logout.incorrectToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.logout.incorrectToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }

    @Test(groups = {"negative"})
    public void logoutWithEmptyToken() {

        new Logout_RequestModel()
                .sendRequestOfLogout()
                .verifyMessageFromResponse(json.readTestData("errorMessages.logout.emptyToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.logout.emptyToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.logout"));
    }
}