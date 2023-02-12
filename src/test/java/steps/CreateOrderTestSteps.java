package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import ru.practicum_services.qa_scooter.base.BaseMethods;
import ru.practicum_services.qa_scooter.responses.order.OrderResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTestSteps {
    @Step("Создать заказ")
public static Response createOrder(String uri, Object body){
    return BaseMethods.postRequest(uri,body);
}
    public static OrderResponse responseToObject(Response response){
        return response.body().as(OrderResponse.class);
    }

    public static int getTrack(Response response){
        OrderResponse responseAsObject = responseToObject(response);
        return responseAsObject.getTrack();
    }
    @Step("Проверить, что запрос возвращает \"track\" ")
    public static void checkTrackInResponse(Response response) {
        int id = getTrack(response);
        MatcherAssert.assertThat(id, notNullValue());
    }
    @Step("Проверить строку состояния")
    public static void checkResponseStatus(Response response, String expectedStatus){
        response.then().assertThat().statusLine(expectedStatus);
    }
    @Step("Проверить тело ответа")
    public static void checkResponseBody(Response response, OrderResponse expectedObject){
        OrderResponse responseAsObject = responseToObject(response);
        assertThat(responseAsObject).usingRecursiveComparison().isEqualTo(expectedObject);
    }
    @Step("Проверить код состояния")
    public static void checkResponseCode(Response response, int expectedCode){
        response.then().assertThat().statusCode(expectedCode);

    }
    @Step("Проверить ответ")
    public static void verifyResponseData(Response response, int expectedCode, String expectedStatus){
        checkResponseCode(response, expectedCode);
        checkResponseStatus(response, expectedStatus);
    }

    public static void printResponseData(Response response) {
        System.out.println(response.statusCode());
        System.out.println(response.statusLine());
        OrderResponse responseAsObject = responseToObject(response);
        System.out.println(responseAsObject.getTrack());

    }
}
