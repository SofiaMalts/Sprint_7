package tests.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import steps.order.CreateOrderTestSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum_services.qa_scooter.model.Order;


import java.util.List;

import static constants.ResponseConstants.CREATED_CODE;
import static constants.ResponseConstants.CREATED_STATUS;
import static constants.Url.QA_SCOOTER_URL;


@RunWith(Parameterized.class)
public class CreateOrderTest  {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public CreateOrderTest (String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {"anna", "hanna", "7998 street", "metro", "+7734892742375", 5, "2020-06-06", "comment", List.of("BLACK", "GREY")},
                {"mam", "nna", "7998 street", "metro", "+7734892742375", 5, "2020-06-06", "comment", List.of("BLACK")},
                {"pam", "han", "7998 street", "metro", "+7734892742375", 5, "2020-06-06", "comment", List.of("GREY")},
                {"pam", "han", "7998 street", "metro", "+7734892742375", 5, "2020-06-06", "comment", List.of()},

        };

    }

    @Before
    public void setUp() {
        RestAssured.baseURI = QA_SCOOTER_URL;
    }

    private final String ordersEndpoint = "/api/v1/orders";


        @Test
        @DisplayName("Проверить создание заказа.")
        @Description("Убедиться, что система позволяет создать заказ и возвращает \"track\" в ответе.")
        public void testCreateOrder(){
            Order order = new Order(firstName, lastName,address, metroStation, phone, rentTime, deliveryDate, comment, color);
            Response response = CreateOrderTestSteps.createOrder(ordersEndpoint,order);
            CreateOrderTestSteps.verifyResponseData(response, CREATED_CODE, CREATED_STATUS);
            CreateOrderTestSteps.checkTrackInResponse(response);
        }

}
