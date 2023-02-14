package steps.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum_services.qa_scooter.base.BaseMethods;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTestSteps {
    @Step("Создать заказ")
    public static Response createOrder(String uri, Object body) {
        return BaseMethods.postRequest(uri, body);
    }

    @Step("Проверить, что запрос возвращает \"track\" ")
    public static void checkTrackInResponse(Response response) {
        response.then().assertThat().body("track", notNullValue());
    }

    @Step("Проверить строку состояния")
    public static void checkResponseStatus(Response response, String expectedStatus) {
        response.then().assertThat().statusLine(expectedStatus);
    }

    @Step("Проверить код состояния")
    public static void checkResponseCode(Response response, int expectedCode) {
        response.then().assertThat().statusCode(expectedCode);
    }

    @Step("Проверить ответ")
    public static void verifyResponseData(Response response, int expectedCode, String expectedStatus) {
        checkResponseCode(response, expectedCode);
        checkResponseStatus(response, expectedStatus);
    }

}
