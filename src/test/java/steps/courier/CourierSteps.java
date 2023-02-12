package steps.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum_services.qa_scooter.base.BaseMethods;
import ru.practicum_services.qa_scooter.responses.courier.CourierResponse;

import static constants.ResponseConstants.OK_CODE;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.notNullValue;
import org.hamcrest.MatcherAssert;


public class CourierSteps {
    @Step("Создать курьера")
    public static Response createNewCourier(String uri, Object body){
        return BaseMethods.postRequest(uri, body);
    }
    @Step("Создать курьера без логина")
    public static Response createCourierWithoutLogin(String uri, String password, String firstName){
        String body = "{\"password\": \""+password+"\", \"firstName\": \""+firstName+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }
    @Step("Создать курьера без пароля")
    public static Response createCourierWithoutPassword(String uri, String login, String firstName){
        String body = "{\"login\": \""+login+"\", \"firstName\": \""+firstName+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }
    @Step("Создать курьера без имени")
    public static Response createCourierWithoutFirstName(String uri, String password, String login){
        String body = "{\"login\": \""+login+"\", \"password\": \""+password+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }
    @Step("Авторизоваться как курьер")
    public static Response loginAsExistingCourier(String uri, Object body){
        return BaseMethods.postRequest(uri, body);
    }

    public static Response loginWithLoginAndPwd (String uri, String password, String login){
        String body = "{\"login\": \""+login+"\", \"password\": \""+password+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }

    @Step("Авторизоваться без пароля")
    public static Response loginWithoutPassword(String uri, String login){
        String body = "{\"login\": \""+login+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }
    @Step("Авторизоваться без логина")
    public static Response loginWithoutLogin(String uri, String password){
        String body = "{\"password\": \""+password+"\"}";
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post(uri);
    }


    public static int getCourierId(Response response){
        CourierResponse responseAsObject = responseToObject(response);
        return responseAsObject.getId();
    }
    @Step ("Проверить, что запрос возвращает \"id\" ")
    public static void checkIdInResponse(Response response){
       int id = getCourierId(response);
       MatcherAssert.assertThat(id, notNullValue());
    }

    @Step("Удалить созданного для теста курьера")
    public static void clearCourierData(String courierEndpoint, String loginEndpoint, Object body) {
        Response response = BaseMethods.postRequest(loginEndpoint, body);
        if (response.statusCode() == OK_CODE) {
            int id = CourierSteps.getCourierId(response);
            BaseMethods.deleteRequest(courierEndpoint, id);
        }
    }
    @Step("Проверить строку состояния")
    public static void checkResponseStatus(Response response, String expectedStatus){
        response.then().assertThat().statusLine(expectedStatus);
    }
    public static CourierResponse responseToObject(Response response){
        return response.body().as(CourierResponse.class);
    }
    @Step("Проверить тело ответа")
    public static void checkResponseBody(Response response, CourierResponse expectedObject){
        CourierResponse responseAsObject = responseToObject(response);
        assertThat(responseAsObject).usingRecursiveComparison().isEqualTo(expectedObject);
    }
    @Step("Проверить код состояния")
    public static void checkResponseCode(Response response, int expectedCode){
        response.then().assertThat().statusCode(expectedCode);

    }
    @Step("Проверить ответ")
    public static void verifyResponseData(Response response, int expectedCode, String expectedStatus, CourierResponse expectedObject){

        checkResponseCode(response, expectedCode);
        checkResponseBody(response, expectedObject);
        checkResponseStatus(response, expectedStatus);
    }


}
