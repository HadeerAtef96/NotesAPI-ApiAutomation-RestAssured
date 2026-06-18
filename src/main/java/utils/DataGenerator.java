package utils;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.util.List;
import java.util.Random;

import static utils.LogHelper.logInfoStep;

public class DataGenerator {

    static Faker faker = new Faker();

    public static String generateRandomEmailByTimeStamp() {
        //Get Random Email by TimeStamp
        String email = "test" + System.currentTimeMillis() + "@gmail.com";
        logInfoStep("Generating Random Email [%s]".formatted(email));
        return email;
    }

    public static String generateRandomEmail() {
        //Get Random Email by Java Faker
        String email = faker.internet().emailAddress();
        logInfoStep("Generating Random Email [%s]".formatted(email));
        return email;    }

    public static String generateRandomFullName() {
        String name = faker.name().fullName();
        logInfoStep("Generating Random Full Name [%s]".formatted(name));
        return name;
    }

    public static String generateRandomName() {
        String name = faker.name().firstName();
        logInfoStep("Generating Random Name [%s]".formatted(name));
        return name;
    }

    public static String generateRandomPassword() {

        String password = faker.internet().password();
        logInfoStep("Generating Random Password [%s]".formatted(password));
        return password;
    }

    public static String generateRandomPhone() {
        int phone = faker.number().numberBetween(10000000,1000000000);
        logInfoStep("Generating Random phone [%s]".formatted(phone));
        return Integer.toString(phone);
    }

    public static String generateRandomCompany() {
        String company = faker.company().industry()+"!!";
        logInfoStep("Generating Random Company [%s]".formatted(company));
        return company;
    }

    public static String generateItemFromList(List<String> list)
    {
        int randomIndex = new Random().nextInt(list.size());
        return list.get(randomIndex);
    }
}

