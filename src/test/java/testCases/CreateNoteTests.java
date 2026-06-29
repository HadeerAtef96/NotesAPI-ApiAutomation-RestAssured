package testCases;

import apiObjectModels.CreateNote_RequestModel;
import apiObjectModels.Login_RequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.util.List;

import static utils.DataGenerator.*;

public class CreateNoteTests extends BaseTest {
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
    public void createNewNoteWithStaticData() {
        String noteID =
                new CreateNote_RequestModel()
                        .prepareRequestBodyForCreateNote(
                                json.readTestData("noteInfo.title"),
                                json.readTestData("noteInfo.description"),
                                json.readTestData("noteInfo.category"))
                        .setTokenInRequestHeaders(token)
                        .sendRequestOfCreateNote()
                        .verifyMessageFromResponse(json.readTestData("successMessages.createNote"))
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.createNote"))
                        .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.createNote"))
                        .verifyCompletedFlagFromResponse(json.readTestData("noteInfo.completed"))
                        .verifyUserIdFromResponse(json.readTestData("userData.id"))
                        .readNoteIdFromResponse();
    }

    @Test(groups = {"positive"})
    public void createNewNoteWithRandomData() {

        String title = generateRandomFullName();
        String description = generateRandomFullName();
        String category = generateItemFromList(List.of("Personal", "Home", "Work"));

        String noteID =
                new CreateNote_RequestModel()
                        .prepareRequestBodyForCreateNote(title, description, category)
                        .setTokenInRequestHeaders(token)
                        .sendRequestOfCreateNote()
                        .verifyMessageFromResponse(json.readTestData("successMessages.createNote"))
                        .verifyResponseStatusCode(json.readTestData("successStatusCodes.createNote"))
                        .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.createNote"))
                        .verifyCompletedFlagFromResponse(json.readTestData("noteInfo.completed"))
                        .verifyUserIdFromResponse(json.readTestData("userData.id"))
                        .readNoteIdFromResponse();
    }

    @Test(groups = {"negative"})
    public void createNewNoteWithEmptyTitle() {
        new CreateNote_RequestModel()
                .prepareRequestBodyForCreateNote(
                        null,
                        json.readTestData("noteInfo.description"),
                        json.readTestData("noteInfo.category"))
                .setTokenInRequestHeaders(token)
                .sendRequestOfCreateNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.createNote.emptyTitle"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.createNote.emptyTitle"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.createNote"));

    }

    @Test(groups = {"negative"})
    public void createNewNoteWithUndefineCategory() {
        String category = generateRandomName();

        new CreateNote_RequestModel()
                .prepareRequestBodyForCreateNote(
                        json.readTestData("noteInfo.title"),
                        json.readTestData("noteInfo.description"),
                        category)
                .setTokenInRequestHeaders(token)
                .sendRequestOfCreateNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.createNote.undefineCategory"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.createNote.emptyTitle"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.createNote"));

    }
}
