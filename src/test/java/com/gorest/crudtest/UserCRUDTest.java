package com.gorest.crudtest;

import com.gorest.constants.EndPoints;
import com.gorest.testbase.TestBase;
import com.gorest.userinfo.UserSteps;
import com.gorest.utils.TestUtils;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

    static String name = "Max" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomName() + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "male";
    static String status = "active";
    static int userId;
    @Steps
    UserSteps userSteps;

    @Title("This will create new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);
    }

    @Title("This will get all user")
    @Test
    public void test002(){
        Response response = given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USERS);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test003() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
    }

    @Title("Update the user information and verify the updated information")
    @Test()
    public void test004() {
        name = name + TestUtils.getRandomValue();
        userSteps.updateStudent(userId, name, email, gender, status);
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(userMap, hasValue(name));

    }

    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test005() {
        userSteps.deleteUser(userId);
        userSteps.getUserById(userId);
    }

}
