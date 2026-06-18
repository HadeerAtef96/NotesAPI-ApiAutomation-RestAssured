package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;

public class UpdateNote_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public UpdateNote_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Values from Response
    @Step
    public UpdateNote_ResponseModel verifyMessageFromResponse(String expectedMessage){
        String actualMessage  = getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    //Method to Verify Status Code of Response
    @Step
    public UpdateNote_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    @Step
    public UpdateNote_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }


}
