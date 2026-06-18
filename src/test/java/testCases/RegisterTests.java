package testCases;

import apiObjectModels.Register_RequestModel;
import org.testng.annotations.Test;
import utils.JsonReader;

import static utils.DataGenerator.*;
import static utils.DataGenerator.generateRandomFullName;

public class RegisterTests extends BaseTest {
    //Variables
    //Read Test Data from JsonFile
    JsonReader json = new JsonReader("src/test/resources/TestData.json");

    @Test  (groups = {"positive"})
    public void registerNewUserWithGeneratedRandomData() {

        String randomEmail = generateRandomEmail();
        Register_RequestModel registerRequestModel = new Register_RequestModel();

        registerRequestModel
                .prepareRequestBodyOfRegister(generateRandomFullName(), randomEmail, generateRandomPassword())
                .sendRequestOfRegister()
                .verifyEmailFromResponse(randomEmail)
                .verifyMessageFromResponse(json.readTestData("successMessages.register"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.register"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"));
    }

    @Test  (groups = {"negative"})
    public void registerNewUserWithDuplicatedEmail() {
        Register_RequestModel registerRequestModel = new Register_RequestModel();

        registerRequestModel
                .prepareRequestBodyOfRegister(generateRandomFullName(), json.readTestData("userData.email"), generateRandomPassword())
                .sendRequestOfRegister()
                .verifyMessageFromResponse(json.readTestData("errorMessages.register.duplicatedEmail"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.register.duplicatedEmail"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"));
    }

    @Test  (groups = {"negative"})
    public void registerNewUserWithIncorrectFormatName() {

        String randomEmail = generateRandomEmail();
        Register_RequestModel registerRequestModel = new Register_RequestModel();

        registerRequestModel
                .prepareRequestBodyOfRegister("Hh", randomEmail, generateRandomPassword())
                .sendRequestOfRegister()
                .verifyMessageFromResponse(json.readTestData("errorMessages.register.incorrectFormatName"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.register.incorrectFormatName"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"));
    }
    @Test  (groups = {"negative"})
    public void registerNewUserWithIncorrectFormatEmail() {

        Register_RequestModel registerRequestModel = new Register_RequestModel();

        registerRequestModel
                .prepareRequestBodyOfRegister(generateRandomFullName() , "Hadeer.com", generateRandomPassword())
                .sendRequestOfRegister()
                .verifyMessageFromResponse(json.readTestData("errorMessages.register.incorrectFormatEmail"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.register.incorrectFormatEmail"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"));
    }

    @Test  (groups = {"negative"})
    public void registerNewUserWithIncorrectFormatPassword() {

        String randomEmail = generateRandomEmail();
        Register_RequestModel registerRequestModel = new Register_RequestModel();

        registerRequestModel
                .prepareRequestBodyOfRegister(generateRandomFullName(), randomEmail, "123")
                .sendRequestOfRegister()
                .verifyMessageFromResponse(json.readTestData("errorMessages.register.incorrectFormatPassword"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.register.incorrectFormatPassword"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.register"));
    }




}


