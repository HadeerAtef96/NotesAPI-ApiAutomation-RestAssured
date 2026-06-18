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
    public void getUserProfileByToken() {
        GetProfile_RequestModel getProfile_requestModel = new GetProfile_RequestModel();
        getProfile_requestModel
                .setTokenInRequestHeaders(token)
                .sendRequestOfGetProfile()
                .verifyMessageFromResponse(json.readTestData("successMessages.getProfile"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getProfile"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getProfile"))
                .verifyEmailFromResponse(json.readTestData("userData.email"))
                .verifyUserIdFromResponse(json.readTestData("userData.id"));
    }

    @Test  (groups = {"negative"})
    public void getUserProfileEmptyToken() {
        GetProfile_RequestModel getProfile_requestModel = new GetProfile_RequestModel();
        getProfile_requestModel
                .sendRequestOfGetProfile()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getProfile.emptyToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getProfile.emptyToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getProfile"));

    }


}
