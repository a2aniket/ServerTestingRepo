import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APIEndpointValidationTest {

    @Test
    public void testGetUser() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .when()
                .get("/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"));
    }

    @Test
    public void testCreateUser() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Sam\", \"job\": \"Project Leader\"}")
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("Sam"))
                .body("job", containsString("Leader"));
    }

    @Test
    public void testUpdateUser() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"Isha\", \"job\": \"Software Engineer\"}")
                .when()
                .put("/users/2")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .when()
                .delete("/users/2")
                .then()
                .assertThat()
                .statusCode(204);
    }

    @Test
    public void testGetUsersWithQueryParams() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .param("page", 2)
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data", not(empty()));
    }

    @Test
    public void testGetUserWithPathVariable() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .pathParam("id", 3)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.last_name", equalTo("Wong"));
    }

    @Test
    public void testRegisterUserWithBasicAuth() {
        RestAssured.baseURI = "https://reqres.in/api";
        AuthenticationScheme authScheme = new BasicAuthScheme().builder().username("username").password("password").build();
        given()
                .auth().basic("username", "password")
                .when()
                .post("/register")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void testGetNonExistentUser() {
        RestAssured.baseURI = "https://reqres.in/api";
        given()
                .pathParam("id", 4)
                .when()
                .get("/users/{id}")
                .then()
                .assertThat()
                .statusCode(404);
    }
}