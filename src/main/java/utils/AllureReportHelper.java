package utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import java.io.File;

import static utils.LogHelper.logErrorStep;

public class AllureReportHelper {

    public static void deleteOldFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        if (filesList != null) {
            for(File file : filesList) {
                if (!file.getName().equals("file.gitkeep")) {
                    if (file.isFile()) {
                        file.delete();
                    } else {
                        deleteOldFiles(file);
                    }
                }
            }
        }
    }

    public static AllureRestAssured logApiRequestsToAllureReport(){
        try {
            return new AllureRestAssured()
                    .setRequestAttachmentName("Request Details")
                    .setResponseAttachmentName("Response Details");
        }catch (Exception e){
            logErrorStep("Failed to Log API Requests to Allure Report", e);
            return null;
        }
    }
}
