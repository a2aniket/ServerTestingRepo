import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APIEndpointTest {

    @BeforeClass
    public void setBaseUri () {

        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testGetUser () {

        Response response = RestAssured.get("/users/2");

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().get("data.first_name"), "Janet");
    }

    @Test
    public void testCreateUser () {

        String requestBody = "{\"name\": \"Sam\", \"job\": \"Project Leader\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users");

        Assert.assertEquals(response.getStatusCode(), 201);

        Assert.assertEquals(response.jsonPath().get("name"), "Sam");

        Assert.assertTrue(response.jsonPath().get("job").toString().contains("Leader"));
    }

    @Test
    public void testUpdateUser () {

        String requestBody = "{\"name\": \"Isha\", \"job\": \"Software Engineer\"}";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put("/users/2");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testDeleteUser () {

        Response response = RestAssured.delete("/users/2");

        Assert.assertEquals(response.getStatusCode(), 204);
    }

    @Test
    public void testGetUsersWithQueryParam () {

        Response response = RestAssured.get("/users?page=1");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void testGetUserWithPathParam () {

        Response response = RestAssured.get("/users/{id}", 3);

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().get("data.last_name"), "Wong");
    }

    @Test
    public void testRegisterUserWithBasicAuth () {

        Response response = RestAssured.given()
                .auth()
                .preemptive()
                .basic("username", "password")
                .post("/register");

        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void testGetNonExistingUser () {

        Response response = RestAssured.get("/users/{id}", 5);

        Assert.assertEquals(response.getStatusCode(), 404);
    }
}