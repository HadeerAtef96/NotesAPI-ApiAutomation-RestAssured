package apiObjectModels;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;

import static utils.APIsManager.*;

public class GetNote_ResponseModel {
    //Variables
    Response response;

    //Constructor
    public GetNote_ResponseModel(Response response) {
        this.response = response;
    }

    //Method to Verify Values from Response
    @Step
    public GetNote_ResponseModel verifyMessageFromResponse(String expectedMessage){
        String actualMessage   = getStringValueFromResponse(response,"message");
        Assert.assertEquals(actualMessage,expectedMessage);
        return this;
    }

    //Method to Verify Status Code of Response
    @Step
    public GetNote_ResponseModel verifyResponseStatusCode(String expectedCode) {
        String actualCode = getStatusCodeFromResponse(response);
        Assert.assertEquals(actualCode, expectedCode);

        return this;
    }

    //Method to Verify Timeout of API Response
    @Step
    public GetNote_ResponseModel verifyResponseTimeLessThanTimeout(String expectedTimeOut) {
        long actualTimeOut = getResponseTimeFromResponse(response);
        long maximumExpectedTime = Long.parseLong(expectedTimeOut);

        Assert.assertTrue(actualTimeOut < maximumExpectedTime);
        return this;
    }

    //Method to Verify Values from Response
    @Step
    public GetNote_ResponseModel verifyTitleFromResponse (String expectedTitle) {
        String actualTitle = getStringValueFromResponse(response,"data.title");
        Assert.assertEquals(actualTitle, expectedTitle);
        return this;
    }

    @Step
    public GetNote_ResponseModel verifyDescriptionFromResponse (String expectedDescription) {
        String actualDesc = getStringValueFromResponse(response,"data.description");
        Assert.assertEquals(actualDesc, expectedDescription);
        return this;
    }

    @Step
    public GetNote_ResponseModel verifyCategoryFromResponse (String expectedCategory) {
        String actualCategory = getStringValueFromResponse(response,"data.category");
        Assert.assertEquals(actualCategory, expectedCategory);
        return this;
    }

    @Step
    public GetNote_ResponseModel verifyCompletedFlagFromResponse (String expectedFlag) {
        boolean actualFlag = getBooleanValueFromResponse(response,"data.completed");
        boolean flag = Boolean.parseBoolean(expectedFlag);

        Assert.assertEquals(actualFlag, flag);
        return this;
    }


}
