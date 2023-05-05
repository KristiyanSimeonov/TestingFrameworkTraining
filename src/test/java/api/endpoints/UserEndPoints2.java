package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.ResourceBundle;
import static io.restassured.RestAssured.given;

public class UserEndPoints2 {

    public static String getURLFromPropertiesFile(String key) {
        // first we get the routes.properties file
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        // then we pass a key, so we can get the value of the key in String format
        return routes.getString(key);
    }

    //payload means request body
    public static Response postUser(User payload) {
        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post(getURLFromPropertiesFile("POST_USER_URL"));

        return response;
    }

    public static Response getUser(String userName) {
        Response response = given()
                .pathParam("username", userName)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // /{username} - this is why the key is username, it will replace /{username} with "/" + userName
                .when()
                .get(getURLFromPropertiesFile("GET_USER_URL"));

        System.out.println(Routes.GET_USER_URL);

        return response;
    }

    public static Response updateUser(String userName, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // /{username} - this is why the key is username, it will replace /{username} with "/" + userName
                .pathParam("username", userName)
                .body(payload)
                .when()
                .put(getURLFromPropertiesFile("UPDATE_USER_URL"));

        return response;
    }

    public static Response deleteUser(String userName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // /{username} - this is why the key is username, it will replace /{username} with "/" + userName
                .pathParam("username", userName)
                .when()
                .delete(getURLFromPropertiesFile("DELETE_USER_URL"));

        return response;
    }
}