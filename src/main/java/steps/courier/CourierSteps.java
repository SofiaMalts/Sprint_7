package steps.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AssertionsForClassTypes;
import ru.practicum_services.qa_scooter.base.BaseMethods;
import ru.practicum_services.qa_scooter.responses.courier.CourierResponse;

import static constants.ResponseConstants.OK_CODE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class CourierSteps {
    private static final String courierEndpoint = "/api/v1/courier";
    private static final String loginEndpoint = courierEndpoint + "/login";

    @Step("Создать курьера")
    public static Response createNewCourier(Object body) {
        return BaseMethods.postRequest(courierEndpoint, body);
    }

    @Step("Создать курьера без логина")
    public static Response createCourierWithoutLogin(String password, String firstName) {
        String body = "{\"password\": \"" + password + "\", \"firstName\": \"" + firstName + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(courierEndpoint);
    }

    @Step("Создать курьера без пароля")
    public static Response createCourierWithoutPassword(String login, String firstName) {
        String body = "{\"login\": \"" + login + "\", \"firstName\": \"" + firstName + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(courierEndpoint);
    }

    @Step("Создать курьера без имени")
    public static Response createCourierWithoutFirstName(String password, String login) {
        String body = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(courierEndpoint);
    }

    @Step("Авторизоваться как курьер")
    public static Response loginAsExistingCourier(Object body) {
        return BaseMethods.postRequest(loginEndpoint, body);
    }

    public static Response loginWithLoginAndPwd(String password, String login) {
        String body = "{\"login\": \"" + login + "\", \"password\": \"" + password + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(loginEndpoint);
    }

    @Step("Авторизоваться без пароля")
    public static Response loginWithoutPassword(String login) {
        String body = "{\"login\": \"" + login + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(loginEndpoint);
    }

    @Step("Авторизоваться без логина")
    public static Response loginWithoutLogin(String password) {
        String body = "{\"password\": \"" + password + "\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(loginEndpoint);
    }

    public static int getCourierId(Response response) {
        CourierResponse responseAsObject = responseToObject(response);
        return responseAsObject.getId();
    }

    @Step("Проверить, что запрос возвращает \"id\" ")
    public static void checkIdInResponse(Response response) {
        response.then().assertThat().body("id", notNullValue());

    }

    @Step("Удалить созданного для теста курьера")
    public static void clearCourierData(Object body) {
        Response response = BaseMethods.postRequest(loginEndpoint, body);
        if (response.statusCode() == OK_CODE) {
            int id = CourierSteps.getCourierId(response);
            BaseMethods.deleteRequest(courierEndpoint, id);
        }
    }

    @Step("Проверить строку состояния")
    public static void checkResponseStatus(Response response, String expectedStatus) {
        response.then().assertThat().statusLine(expectedStatus);
    }

    public static CourierResponse responseToObject(Response response) {
        return response.body().as(CourierResponse.class);
    }

    @Step("Проверить тело ответа")
    public static void checkResponseBody(Response response, CourierResponse expectedObject) {
        CourierResponse responseAsObject = responseToObject(response);
        AssertionsForClassTypes.assertThat(responseAsObject).usingRecursiveComparison().isEqualTo(expectedObject);
    }

    @Step("Проверить код состояния")
    public static void checkResponseCode(Response response, int expectedCode) {
        response.then().assertThat().statusCode(expectedCode);

    }

    @Step("Проверить ответ")
    public static void verifyResponseData(Response response, int expectedCode, String expectedStatus, CourierResponse expectedObject) {
        checkResponseCode(response, expectedCode);
        checkResponseBody(response, expectedObject);
        checkResponseStatus(response, expectedStatus);
    }

}
