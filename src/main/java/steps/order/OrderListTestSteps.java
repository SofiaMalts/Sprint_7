package steps.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import ru.practicum_services.qa_scooter.base.BaseMethods;
import ru.practicum_services.qa_scooter.responses.order.Orders;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTestSteps {

    public static Response getOrderList(String uri) {
        return BaseMethods.getRequest(uri);
    }

    public static Orders responseToObject(Response response) {
        return response.body().as(Orders.class);
    }

    @Step("Запрос на объект списка заказов")
    public static Orders getOrderListObject(String uri) {
        Response response = getOrderList(uri);
        Orders orderListObject = responseToObject(response);
        return orderListObject;
    }

    @Step("Проверить, что объект ответа не null")
    public static void checkIfResponseIsNotNull(Orders orderList) {
        MatcherAssert.assertThat(orderList, notNullValue());
    }

}
