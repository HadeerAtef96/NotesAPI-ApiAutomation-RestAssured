package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;
import static utils.LogHelper.logErrorStep;

public class CreateNote_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public CreateNote_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Values from Response
    @Step
    public CreateNote_ResponseModel verifyMessageFromResponse(String expectedMessage){
        String actualMessage   = getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    //Method to Verify Status Code of Response
    @Step
    public CreateNote_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    @Step
    public CreateNote_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }

    //Method to Verify Values from Response
    @Step
    public CreateNote_ResponseModel verifyUserIdFromResponse (String userID) {
        String actualId = getStringValueFromResponse(response,"data.user_id");
        Assert.assertEquals(actualId, userID);
        return this;
    }

    @Step
    public CreateNote_ResponseModel verifyCompletedFlagFromResponse (String expectedFlag) {
        boolean actualFlag = getBooleanValueFromResponse(response,"data.completed");
        boolean flag = Boolean.parseBoolean(expectedFlag);

        Assert.assertEquals(actualFlag, flag);
        return this;
    }

    //Method to Get Values from Response
    @Step
    public String readNoteIdFromResponse(){
        return getStringValueFromResponse(response,"data.id");
    }

}
