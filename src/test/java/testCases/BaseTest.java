package testCases;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.AllureReportHelper;

import java.io.File;
import java.io.IOException;

import static utils.PropertiesReader.loadConfigurationsIntoSystemProperties;

public class BaseTest {

    @BeforeSuite (alwaysRun = true)
    public void beforeSuite() {
        //Load All Properties and save it into System
        //Test Comment
        loadConfigurationsIntoSystemProperties();

        //Clear Old Allure Results before Every Run
        File file = new File("target/allure-results");
        AllureReportHelper.deleteOldFiles(file);
    }

    @AfterSuite (alwaysRun = true)
    public void afterSuite() throws IOException {
        // Open Allure Report Automatically After Run
        Runtime.getRuntime().exec(System.getProperty("user.dir")+"/Open_Allure_Report.bat");
    }

}
