package utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class LogHelper {
    static Logger log = LogManager.getLogger();

    public static void logInfoStep(String message){
        log.info(message);
    }

    public static void logErrorStep(String message){
        log.error(message);
        Assert.fail(message);
    }

    public static void logErrorStep(String message,Exception e) {
        log.error(message);
        Assert.fail(message,e);
    }

    public static void logCustomStep(Level customType, String message){
        log.log(customType , message);
    }

    public static Level API = Level.forName("API", 350);


}
