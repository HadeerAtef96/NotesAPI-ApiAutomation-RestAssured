package testCases;

import apiObjectModels.GetProfile_RequestModel;
import apiObjectModels.Login_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

public class GetProfileTests extends BaseTest {
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
    public void getUserProfileByToken() {

        new GetProfile_RequestModel()
                .setTokenInRequestHeaders(token)
                .sendRequestOfGetProfile()
                .verifyMessageFromResponse(json.readTestData("successMessages.getProfile"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getProfile"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getProfile"))
                .verifyEmailFromResponse(json.readTestData("userData.email"))
                .verifyUserIdFromResponse(json.readTestData("userData.id"));
    }

    //Negative Test Case
    @Test(groups = {"negative"})
    public void getUserProfileEmptyToken() {

        new GetProfile_RequestModel()
                .sendRequestOfGetProfile()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getProfile.emptyToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getProfile.emptyToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getProfile"));

    }


}
