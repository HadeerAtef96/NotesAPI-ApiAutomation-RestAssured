package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;

public class UpdateProfile_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public UpdateProfile_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Message from Response
    @Step
    public UpdateProfile_ResponseModel verifyMessageFromResponse(String expectedMessage){
        String actualMessage   = getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    //Method to Verify Status Code of Response
    @Step
    public UpdateProfile_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    @Step
    public UpdateProfile_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }

    @Step
    public UpdateProfile_ResponseModel verifyNoteIdFromResponse (String noteID) {
        String actualId = getStringValueFromResponse(response,"data.id");
        Assert.assertEquals(actualId, noteID);
        return this;
    }

}
