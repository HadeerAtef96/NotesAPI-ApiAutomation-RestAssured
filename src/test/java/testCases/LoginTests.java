package testCases;

import apiObjectModels.Login_RequestModel;
import apiObjectModels.Register_RequestModel;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;

public class LoginTests extends BaseTest{
    //Variables
    String token;

    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @Test(groups = {"positive"})
    public void loginWithExistingEmail() {
        Login_RequestModel loginRequestModel = new Login_RequestModel();
        token =
                loginRequestModel
                        .prepareRequestBodyForLogin(json.readTestData("userData.email"), json.readTestData("userData.password"))
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

        Register_RequestModel registerRequestModel = new Register_RequestModel();
        String userID =
                registerRequestModel
                        .prepareRequestBodyOfRegister(randomName, randomEmail, randomPassword)
                        .sendRequestOfRegister()
                        .verifyEmailFromResponse(randomEmail)
                        .verifyMessageFromResponse(json.readTestData("successMessages.register"))
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.register"))
                        .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"))
                        .getUserIdFromResponse();

        Login_RequestModel loginRequestModel = new Login_RequestModel();
        loginRequestModel
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
        Login_RequestModel loginRequestModel = new Login_RequestModel();

        loginRequestModel
                .prepareRequestBodyForLogin(randomEmail,  json.readTestData("userData.password"))
                .sendRequestOfLogin()
                .verifyMessageFromResponse(json.readTestData("errorMessages.login.invalidCredentials"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.login.invalidCredentials"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"));
    }

    @Test(groups = {"negative"})
    public void loginWithNonExistingPassword() {
        String randomPassword = generateRandomPassword();
        Login_RequestModel loginRequestModel = new Login_RequestModel();

        loginRequestModel
                .prepareRequestBodyForLogin(json.readTestData("userData.email"),  randomPassword)
                .sendRequestOfLogin()
                .verifyMessageFromResponse(json.readTestData("errorMessages.login.invalidCredentials"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.login.invalidCredentials"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.login"));
    }

}
