package testCases;

import apiObjectModels.Login_RequestModel;
import apiObjectModels.Register_RequestModel;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;

public class LoginTests extends BaseTest {
    //Variables
    String token;

    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @Test(groups = {"positive"})
    public void loginWithExistingEmail() {

        token =
                new Login_RequestModel()
                        .prepareRequestBodyForLogin(
                                json.readTestData("userData.email"),
                                json.readTestData("userData.password"))
                        .sendRequestOfLogin()
                        .verifyMessageFromResponse(json.readTestData("successMessages.login"))
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.login"))
                        .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"))
                        .readTokenFromResponse();
    }

    @Test(groups = {"positive"})
    public void loginWithRandomEmailAfterRegister() {
        String randomEmail = generateRandomEmail();
        String randomName = generateRandomFullName();
        String randomPassword = generateRandomPassword();

        String userID =
                new Register_RequestModel()
                        .prepareRequestBodyOfRegister(randomName, randomEmail, randomPassword)
                        .sendRequestOfRegister()
                        .verifyEmailFromResponse(randomEmail)
                        .verifyMessageFromResponse(json.readTestData("successMessages.register"))
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.register"))
                        .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"))
                        .getUserIdFromResponse();

        new Login_RequestModel()
                .prepareRequestBodyForLogin(randomEmail, randomPassword)
                .sendRequestOfLogin()
                .verifyMessageFromResponse(json.readTestData("successMessages.login"))
                .verifyNameFromResponse(randomName)
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.login"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"))
                .verifyIdFromResponse(userID);
    }

    @Test(groups = {"negative"})
    public void loginWithNonExistingEmail() {
        String randomEmail = generateRandomEmail();

        new Login_RequestModel()
                .prepareRequestBodyForLogin(
                        randomEmail,
                        json.readTestData("userData.password"))
                .sendRequestOfLogin()
                .verifyMessageFromResponse(json.readTestData("errorMessages.login.invalidCredentials"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.login.invalidCredentials"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"));
    }

    @Test(groups = {"negative"})
    public void loginWithNonExistingPassword() {
        String randomPassword = generateRandomPassword();

        new Login_RequestModel()
                .prepareRequestBodyForLogin(
                        json.readTestData("userData.email"),
                        randomPassword)
                .sendRequestOfLogin()
                .verifyMessageFromResponse(json.readTestData("errorMessages.login.invalidCredentials"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.login.invalidCredentials"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"));
    }

}
