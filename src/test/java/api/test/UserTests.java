package api.test;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.equalTo;


public class UserTests {

    private Faker faker;
    private User userPayload;
    public Logger logger; // for logs

    @BeforeClass // this method will execute before we start all the test methods
    public void setup() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        userPayload.setUserStatus(0);

        //LOGGER
        // this.getClass() because the same class name will be displayed in the log file
        logger =  LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1) // we prioritize this test
    public void testPostUser() {
        logger.info("****** Creating user *****");

        Response response = UserEndPoints2.postUser(userPayload);

        Assert.assertEquals(response.getStatusCode(), 200);

        logger.info("****** User is created *****");
    }

    @Test(priority = 2)
    public void testGetUserByName() {
        logger.info("****** Reading user info *****");

        String userName = userPayload.getUsername();
        Response response = UserEndPoints2.getUser(userName);

        response.then().statusCode(200);

        logger.info("****** User info is displayed *****");
    }

    @Test(priority = 3)
    public void testUpdateUserByName() {
        logger.info("****** Update user *****");

        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints2.updateUser(userPayload.getUsername(), userPayload);

        Assert.assertEquals(response.getStatusCode(), 200);

        //Checking data after update
        response = UserEndPoints2.getUser(userPayload.getUsername());
        response.then().body("firstName", equalTo(userPayload.getFirstName()));
        response.then().body("lastName", equalTo(userPayload.getLastName()));
        response.then().body("email", equalTo(userPayload.getEmail()));

        logger.info("****** USER IS UPDATED *****");
    }

    @Test(priority = 4)
    public void testDeleteUserByName() {
        logger.info("****** Deleting User *****");

        String userName = userPayload.getUsername();
        Response response = UserEndPoints2.deleteUser(userName);

        response.then().statusCode(200);

        logger.info("****** USER IS Deleted *****");
    }
}
