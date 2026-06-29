package testCases;

import apiObjectModels.GetProfile_RequestModel;
import apiObjectModels.Login_RequestModel;
import apiObjectModels.UpdateProfile_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;

public class UpdateProfileTests extends BaseTest {

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
    public void UpdateUserProfileWithRandomData() {
        String name = generateRandomFullName();
        String phone = generateRandomPhone();
        String company = generateRandomCompany();

        new UpdateProfile_RequestModel()
                .prepareRequestBodyOfUpdateProfile(name, phone, company)
                .setTokenInRequestHeaders(token)
                .sendRequestOfUpdateProfile()
                .verifyMessageFromResponse(json.readTestData("successMessages.updateProfile"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.updateProfile"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.updateProfile"));

        new GetProfile_RequestModel()
                .setTokenInRequestHeaders(token)
                .sendRequestOfGetProfile()
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.getProfile"))
                .verifyNameFromResponse(name)
                .verifyPhoneNumberFromResponse(phone)
                .verifyCompanyFromResponse(company);
    }


    @Test(groups = {"negative"})
    public void UpdateUserProfileEmptyName() {
        String phone = generateRandomPhone();
        String company = generateRandomCompany();

        new UpdateProfile_RequestModel()
                .prepareRequestBodyOfUpdateProfile(null, phone, company)
                .setTokenInRequestHeaders(token)
                .sendRequestOfUpdateProfile()
                .verifyMessageFromResponse(json.readTestData("errorMessages.updateProfile.emptyName"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.updateProfile.emptyName"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.updateProfile"));
    }

    @Test(groups = {"negative"})
    public void UpdateUserProfileIncorrectToken() {
        String name = generateRandomFullName();
        String phone = generateRandomPhone();
        String company = generateRandomCompany();

        new UpdateProfile_RequestModel()
                .prepareRequestBodyOfUpdateProfile(name, phone, company)
                .setTokenInRequestHeaders(token + "0")
                .sendRequestOfUpdateProfile()
                .verifyMessageFromResponse(json.readTestData("errorMessages.updateProfile.incorrectToken"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.updateProfile.incorrectToken"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.updateProfile"));
    }
}
