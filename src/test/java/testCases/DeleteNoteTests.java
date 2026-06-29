package testCases;

import apiObjectModels.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.util.List;

import static utils.DataGenerator.generateItemFromList;
import static utils.DataGenerator.generateRandomFullName;

public class DeleteNoteTests extends BaseTest {
    //Variables
    String token;
    String noteID;

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

    @BeforeMethod(dependsOnMethods = "loginWithExistingEmail", alwaysRun = true)
    public void createNewNoteWithRandomData() {
        String title = generateRandomFullName();
        String description = generateRandomFullName();
        String category = generateItemFromList(List.of("Personal", "Home", "Work"));

        noteID =
                new CreateNote_RequestModel()
                        .prepareRequestBodyForCreateNote(title, description, category)
                        .setTokenInRequestHeaders(token)
                        .sendRequestOfCreateNote()
                        .readNoteIdFromResponse();
    }

    @Test(groups = {"positive"})
    public void deleteNoteByID() {
        new DeleteNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID)
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.deleteNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.deleteNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));
    }

    @Test(groups = {"positive"})
    public void deleteMultipleNotesByID() {
        List<String> allNotesIDs;

        allNotesIDs =
                new GetAllNotes_RequestModel()
                        .setTokenInRequestHeaders(token)
                        .sendRequestOfGetNote()
                        .verifyMessageFromResponse(json.readTestData("successMessages.getAllNotes"))
                        .getAllIDsOfNotes();

        for (String id : allNotesIDs) {
            new DeleteNote_RequestModel()
                    .setTokenInRequestHeaders(token)
                    .setNoteIdAsPathParameter(id)
                    .sendRequestOfDeleteNote()
                    .verifyResponseStatusCode(json.readTestData("successStatusCodes.deleteNote"));
        }
    }

    @Test(groups = {"negative"})
    public void deleteNoteByInvalidID() {

        new DeleteNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID + "0")
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.deleteNote.incorrectId"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.deleteNote.incorrectId"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));
    }

    @Test(groups = {"negative"})
    public void getNoteByIDAfterDelete() {

        new DeleteNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIdAsPathParameter(noteID)
                .sendRequestOfDeleteNote()
                .verifyMessageFromResponse(json.readTestData("successMessages.deleteNote"))
                .verifyResponseStatusCode(json.readTestData("successStatusCodes.deleteNote"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.deleteNote"));

        new GetNote_RequestModel()
                .setTokenInRequestHeaders(token)
                .setNoteIDInRequestParameter(noteID)
                .sendRequestOfGetNote()
                .verifyMessageFromResponse(json.readTestData("errorMessages.getNote.invalidID"))
                .verifyResponseStatusCode(json.readTestData("errorStatusCodes.getNote.invalidID"))
                .verifyResponseTimeLessThanTimeout(json.readTestData("timeOut.getNote"));
    }
}
