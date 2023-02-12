package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import steps.CourierSteps;

import ru.practicum_services.qa_scooter.model.Courier;
import ru.practicum_services.qa_scooter.responses.courier.CourierResponse;

import static constants.ResponseConstants.*;
import static constants.Url.QA_SCOOTER_URL;

public class CreateCourierTest {
    Courier courier = new Courier("Alina123", "MyB1rthD@Y", "Алина");
    Courier courierDuplicate= new Courier("Alina123", "1L0veC@ts!", "Карина");

    private final String courierEndpoint = "/api/v1/courier";
    private final String loginEndpoint = courierEndpoint+"/login";
    private final String badRequestErrorMessage = "Недостаточно данных для создания учетной записи";
    private final String conflictErrorMessage = "Этот логин уже используется. Попробуйте другой.";
    @Before
    public void setUp() {
        RestAssured.baseURI = QA_SCOOTER_URL;
    }
    @Test
    @DisplayName("Проверить создание курьера")
    @Description("Убедиться, что система позволяет создать курьера при верном запросе")
    public void testNewCourierCreation(){
        CourierResponse expectedResponse = new CourierResponse(true);
        Response creationResponse = CourierSteps.createNewCourier(courierEndpoint, courier);
        CourierSteps.verifyResponseData(creationResponse, CREATED_CODE, CREATED_STATUS, expectedResponse);
    }
    @Test
    @DisplayName("Проверить создание курьера с уже существующим в системе логином")
    @Description("Убедиться, что система не позволяет создать курьера, если указанный логин уже сущестует в системе")
    public void testDuplicateCourierCreation(){
        CourierResponse firstExpectedResponse = new CourierResponse(true);
        CourierResponse secondExpectedResponse = new CourierResponse(CONFLICT_CODE, conflictErrorMessage);
        Response firstResponse = CourierSteps.createNewCourier(courierEndpoint, courier);
        CourierSteps.verifyResponseData(firstResponse, CREATED_CODE, CREATED_STATUS, firstExpectedResponse);
        Response secondResponse = CourierSteps.createNewCourier(courierEndpoint, courierDuplicate);
        CourierSteps.verifyResponseData(secondResponse, CONFLICT_CODE, CONFLICT_STATUS, secondExpectedResponse);

    }
    @Test
    @DisplayName("Проверить, курьер может быть создан только при отправке всех обязательных полей")
    @Description("Убедиться, что система не позволяет создать курьера, если в запросе отстутствует хотя быть одно обязательное поле")
    public void testCreationWithoutRequiredFields(){
        CourierResponse expectedResponseOne = new CourierResponse(BAD_REQUEST_CODE, badRequestErrorMessage);
        CourierResponse expectedResponseTwo = new CourierResponse(true);
        Response creationWithoutLogin = CourierSteps.createCourierWithoutLogin(courierEndpoint, courier.getLogin(), courier.getFirstName());
        CourierSteps.verifyResponseData(creationWithoutLogin, BAD_REQUEST_CODE, BAD_REQUEST_STATUS, expectedResponseOne);
        Response creationWithoutPwd = CourierSteps.createCourierWithoutPassword(courierEndpoint, courier.getPassword(), courier.getFirstName());
        CourierSteps.verifyResponseData(creationWithoutPwd, BAD_REQUEST_CODE, BAD_REQUEST_STATUS, expectedResponseOne);
        Response creationWithoutFirstName = CourierSteps.createCourierWithoutFirstName(courierEndpoint, courier.getPassword(), courier.getLogin());
        CourierSteps.verifyResponseData(creationWithoutFirstName, CREATED_CODE, CREATED_STATUS, expectedResponseTwo);

    }


    @After

    public void deleteExistingCourier(){
        CourierSteps.clearCourierData(courierEndpoint, loginEndpoint, courier);
    }

}
