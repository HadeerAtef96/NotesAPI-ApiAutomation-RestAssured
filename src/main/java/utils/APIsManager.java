package utils;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static utils.LogHelper.*;

public class APIsManager {

    public static Response sendApiRequest(String apiMethod, String url, String contentTypeValue, Map<String, Object> requestBody, Map<String, Object> headers) {
        try {
            Response response;

            //Prepare Request with given
            RequestSpecification request = RestAssured.given().filter(AllureReportHelper.logApiRequestsToAllureReport());

            //Set Content Type of Request
            request.contentType(contentTypeValue + "; charset=utf-8");

            //Set the Body of Request either Json or Form Data
            if(contentTypeValue != null){
                switch (contentTypeValue) {
                    case "application/json":
                        request.body(requestBody);
                        break;

                    case "application/x-www-form-urlencoded":
                        request.formParams(requestBody);
                        break;
                }
            }

            //Set the Headers for the Request
            if (headers != null)
                request.headers(headers);

            // Set the Method Type and URL
            switch (apiMethod) {
                case "post":
                    response = request.when().post(url);
                    break;

                case "put":
                    response = request.when().put(url);
                    break;

                case "patch":
                    response = request.when().patch(url);
                    break;

                case "delete":
                    response = request.when().delete(url);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unsupported API Method: " + apiMethod);
            }

            logInfoStep("Sending [%s] Request for URL [%s]".formatted(apiMethod, url));

            //Print Request Body
            if (requestBody == null) {
                logCustomStep(LogHelper.API,"Request Body : " + "{}");
            } else {
                logCustomStep(LogHelper.API,"Request Body : " + requestBody.toString());
            }

            //Print Response Body
            logCustomStep(LogHelper.API,"Response Body : " + response.getBody().asString());

            return response;

        } catch (Exception e) {
            logErrorStep("Failed to send [%s] Request for URL [%s].formatted(apiMethod, url)",e);
            return null;
        }
    }

    public static Response sendGetRequest(String url, String parameterType, Map<String, Object> requestParameters, Map<String, Object> headers) {
        try{
            Response response;

            //Prepare Request with given
            RequestSpecification request = RestAssured.given().filter(AllureReportHelper.logApiRequestsToAllureReport());

            //Set the Parameters with Request
            if (parameterType != null) {
                switch (parameterType) {
                    case "queryParameter":
                        request.queryParams(requestParameters);
                        break;

                    case "pathParameter":
                        request.pathParams(requestParameters);
                        break;
                }
            }

            //Set the Headers for the Request
            if (headers != null)
                request.headers(headers);

            //Send the Get Request
            response = request.when().get(url);

            logInfoStep("Sending Get Request for URL [%s]".formatted(url));

            //Print Request Body
            if (requestParameters == null) {
                logCustomStep(LogHelper.API,"Request Parameters : " + "{}");
            } else {
                logCustomStep(LogHelper.API,"Request Parameters : " + requestParameters.toString());
            }

            //Print Response Body
            logCustomStep(LogHelper.API,"Response Body : " + response.getBody().asString());

            return response;
        }catch (Exception e){
            logErrorStep("Failed to send Get Request for URL [%s].formatted(url)",e);
            return null;
        }

    }

    public static String getStatusCodeFromResponse(Response response){
        try{
            int code = response.getStatusCode();
            logInfoStep("Getting Status Code [%d] from Response".formatted(code));
            return String.valueOf(code);

        }catch (Exception e){
            logErrorStep("Failed to Get Status Code from Response",e);
            return null;
        }
    }

    public static long getResponseTimeFromResponse(Response response){
        try{
            long time = response.getTime();
            logInfoStep("Getting Time [%d] from Response".formatted(time));
            return time;

        }catch (Exception e){
            logErrorStep("Failed to Get the Time from Response",e);
            return 0;
        }
    }

    public static String getStringValueFromResponse(Response response, String jsonPathForKey) {
        try {
            String value = JsonPath.read(response.getBody().asString(), jsonPathForKey);
            logInfoStep("Getting String Value [%s] from JsonPath [%s] from API Response".formatted(value, jsonPathForKey));
            return value;

        } catch (Exception e) {
            logErrorStep("Failed to get the value from JsonPath [%s]".formatted(jsonPathForKey));
            return null;
        }
    }

    public static List<String> getListOfStringFromResponse(Response response, String jsonPathForKey) {
        try {
            List<String> value = JsonPath.read(response.getBody().asString(), jsonPathForKey);
            logInfoStep("Getting List of Object [%s] from JsonPath [%s] from API Response".formatted(value, jsonPathForKey));
            return value;

        } catch (Exception e) {
            logErrorStep("Failed to get the value from JsonPath [%s]".formatted(jsonPathForKey));
            return null;
        }
    }

    public static boolean getBooleanValueFromResponse(Response response, String jsonPathForKey) {
        try {
            boolean value = JsonPath.read(response.getBody().asString(), jsonPathForKey);
            logInfoStep("Getting String Value [%s] from JsonPath [%s] from API Response".formatted(value, jsonPathForKey));
            return value;

        } catch (Exception e) {
            logErrorStep("Failed to get the value from JsonPath [%s]".formatted(jsonPathForKey));
            return false;
        }
    }

    public static int getIntegerValueFromResponse(Response response, String jsonPathForKey) {
        try {
            int value = JsonPath.read(response.getBody().asString(), jsonPathForKey);
            logInfoStep("Getting String Value [%s] from JsonPath [%s] from API Response".formatted(value, jsonPathForKey));
            return value;

        } catch (Exception e) {
            logErrorStep("Failed to get the value from JsonPath [%s]".formatted(jsonPathForKey));
            return 0;
        }
    }
}
