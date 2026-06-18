package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;

public class Login_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public Login_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Values from Response
    @Step
    public Login_ResponseModel verifyMessageFromResponse(String expectedMessage){
       String actualMessage   = getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    @Step
    public Login_ResponseModel verifyNameFromResponse(String name){
        String actualName =getStringValueFromResponse(response,"data.name");
        Assert.assertEquals(actualName,name);
        return this;
    }

    @Step
    public Login_ResponseModel verifyIdFromResponse(String expectedId){
        String actualID = getStringValueFromResponse(response,"data.id");
        Assert.assertEquals(actualID,expectedId);
        return this;
    }


    //Method to Verify Status Code of Response
    @Step
    public Login_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    @Step
    public Login_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }

    //Method to Get Values from Response
    @Step
    public String readTokenFromResponse(){
        String token = getStringValueFromResponse(response,"data.token");
        return token;
    }

}
