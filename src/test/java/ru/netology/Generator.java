package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@Data

public class Generator {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void makeRegistration(Registr registration) {
        given()
                .spec(requestSpec)
                .body(registration)

                .when()
                .post("/api/system/users")

                .then()
                .statusCode(200);
    }

    public static Registr generateNewValidActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new Registr(login, password, "active"));
        return new Registr(login, password, "active");
    }

    public static Registr generateNewValidBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        makeRegistration(new Registr(login, password, "blocked"));
        return new Registr(login, password, "blocked");
    }

    public static Registr generateNewUserWithInvalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().password();
        String status = "active";
        makeRegistration(new Registr("ivan", password, status));
        return new Registr("roman", password, status);
    }

    public static Registr generateNewUserWithInvalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String status = "active";
        makeRegistration(new Registr(login, "password", status));
        return new Registr(login, "asdfgh", status);
    }
}
