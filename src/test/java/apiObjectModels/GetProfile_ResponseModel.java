package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;

public class GetProfile_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public GetProfile_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Values from Response
    @Step
    public GetProfile_ResponseModel verifyMessageFromResponse (String expectedMessage){
        String actualMessage =getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    @Step
    public GetProfile_ResponseModel verifyUserIdFromResponse (String expectedId){
        String actualId=getStringValueFromResponse(response,"data.id");
        Assert.assertEquals(actualId,expectedId);
        return this;
    }

    @Step
    public GetProfile_ResponseModel verifyEmailFromResponse (String expectedEmail){
        String actualEmail =getStringValueFromResponse(response,"data.email");
        Assert.assertEquals(actualEmail,expectedEmail);
        return this;
    }

    @Step
    public GetProfile_ResponseModel verifyNameFromResponse (String expectedName){
        String actualName =getStringValueFromResponse(response,"data.name");
        Assert.assertEquals(actualName,expectedName);
        return this;
    }

    @Step
    public GetProfile_ResponseModel verifyPhoneNumberFromResponse (String expectedPhone){
        String actualPhone =getStringValueFromResponse(response,"data.phone");
        Assert.assertEquals(actualPhone,expectedPhone);
        return this;
    }

    @Step
    public GetProfile_ResponseModel verifyCompanyFromResponse (String expectedCompany){
        String actualCompany =getStringValueFromResponse(response,"data.company");
        Assert.assertEquals(actualCompany,expectedCompany);
        return this;
    }

    //Method to Verify Status Code of Response
    @Step
    public GetProfile_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    public GetProfile_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }

    //Method to Get Values from Response

}
