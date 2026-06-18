package utils;

import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Step;

import java.io.File;
import java.io.IOException;

import static utils.LogHelper.logErrorStep;
import static utils.LogHelper.logInfoStep;

public class JsonReader {
    String filePath ;

    public JsonReader (String filePath )
    {
        this.filePath= filePath;
    }

    public String readTestData(String jsonPath){
        try{

            File file = new File(filePath);

            String value = null;
            try {
                value = JsonPath.parse(file).read(jsonPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            logInfoStep("Getting Test Data [%s] by Json Path [%s]".formatted(value,jsonPath));

            return value;
        }catch (Exception e){
            logErrorStep("Failed to get Test Data by Json Path [%s]".formatted(jsonPath));
            return null;
        }


    }
}
