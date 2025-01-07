package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Tag("API-TEST")
@DisplayName("Тестирование Post")
public class PostLoginTests extends TestBase {
    @Test
    @DisplayName("Проверка Api Post Login")
    public void testPostRegistration() {
        String bodyJSON = "{\n" +
                "  \"email\": \"eve.holt@reqres.in\",\n" +
                "  \"password\": \"cityslicka\"\n" +
                "}";
         given()
                .body(bodyJSON)
                .contentType(ContentType.JSON)
                .when()
                .log().uri()
                .post("login")
                .then()
                .log().body()
                .statusCode(200);

        Response response = given()
                .body(bodyJSON)
                .contentType(ContentType.JSON)
                .when()
                .post("https://reqres.in/api/login");
        if (response.jsonPath().get("token").toString().isEmpty()) {
            System.out.println("Поле 'token' пустое");
        } else {
            System.out.println("Поле 'token' не пустое");
        }
    }

    @Test
    @DisplayName("Проверка Api Post Login Ошибка UserNotFound")
    public void testPostRegistrationUserNotFound() {
        String bodyJSON = "{\n" +
                "  \"email\": \"eve.holt@reqres\",\n" +
                "  \"password\": \"cityslicka\"\n" +
                "}";
        given()
                .body(bodyJSON)
                .contentType(ContentType.JSON)
                .when()
                .log().uri()
                .post("login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", equalTo("user not found"));
    }

    @Test
    @DisplayName("Проверка Api Post Login Ошибка MissingPassword")
    public void testPostRegistrationMissingPassword() {
        String bodyJSON = "{\n" +
                "  \"email\": \"peter@klaven\"\n" +
                "}";

        given()
                .body(bodyJSON)
                .contentType(ContentType.JSON)
                .when()
                .log().uri()
                .post("login")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
