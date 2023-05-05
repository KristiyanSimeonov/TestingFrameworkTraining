package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class UserEndPoints {

    //payload means request body
    public static Response postUser(User payload) {
        Response response =
                given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.POST_USER_URL);

        return response;
    }

    public static Response getUser(String userName) {
        Response response = given()
                .pathParam("username", userName)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // /{username} - this is why the key is username, it will replace /{username} with "/" + userName
                .when()
                .get(Routes.GET_USER_URL);

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
                .put(Routes.UPDATE_USER_URL);

        return response;
    }

    public static Response deleteUser(String userName) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                // /{username} - this is why the key is username, it will replace /{username} with "/" + userName
                .pathParam("username", userName)
                .when()
                .delete(Routes.DELETE_USER_URL);

        return response;
    }
}