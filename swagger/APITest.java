import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APIEndpointValidationTest {
    
    @BeforeClass
    public void setBaseURI() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetUser() {
        given().
        when().
            get("/users/2").
        then().
            assertThat().
                statusCode(200).
                body("data.first_name", equalTo("Janet"));
    }

    @Test
    public void testCreateUser() {
        given().
            contentType(ContentType.JSON).
            body("{\"name\": \"Sam\", \"job\": \"Project Leader\"}").
        when().
            post("/users").
        then().
            assertThat().
                statusCode(201).
                body("name", equalTo("Sam")).
                body("job", containsString("Leader"));
    }

    @Test
    public void testUpdateUser() {
        given().
            contentType(ContentType.JSON).
            body("{\"name\": \"Isha\", \"job\": \"Software Engineer\"}").
        when().
            put("/users/2").
        then().
            assertThat().
                statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        given().
        when().
            delete("/users/2").
        then().
            assertThat().
                statusCode(204);
    }

    @Test
    public void testGetUsersWithQueryParams() {
        given().
            queryParam("page", "2").
        when().
            get("/users").
        then().
            assertThat().
                statusCode(200).
                body("data", not(empty()));
    }

    @Test
    public void testGetUserWithPathParams() {
        given().
            pathParam("id", "3").
        when().
            get("/users/{id}").
        then().
            assertThat().
                statusCode(200).
                body("data.last_name", equalTo("Wong"));
    }

    @Test
    public void testRegisterWithBasicAuth() {
        given().
            auth().
            preemptive().
            basic("username", "password").
        when().
            post("/register").
        then().
            assertThat().
                statusCode(400);
    }

    @Test
    public void testGetNonExistingUser() {
        given().
            pathParam("id", "4").
        when().
            get("/users/{id}").
        then().
            assertThat().
                statusCode(404);
    }
}