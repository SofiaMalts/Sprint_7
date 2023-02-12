package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import steps.OrderListTestSteps;
import org.junit.Before;
import org.junit.Test;
import ru.practicum_services.qa_scooter.responses.order.Orders;

import static constants.Url.QA_SCOOTER_URL;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = QA_SCOOTER_URL;
    }

    @Test
    @DisplayName("Проверить получение списка заказов")
    public void getOrderListTest(){
        Orders orderList = OrderListTestSteps.getOrderListObject("/api/v1/orders?courierId=1");
        OrderListTestSteps.checkIfResponseIsNotNull(orderList);
    }

}
