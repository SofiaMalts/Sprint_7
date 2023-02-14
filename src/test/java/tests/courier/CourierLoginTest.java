package tests.courier;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum_services.qa_scooter.model.Courier;
import ru.practicum_services.qa_scooter.responses.courier.CourierResponse;
import steps.courier.CourierSteps;

import static constants.ResponseConstants.*;
import static constants.Url.QA_SCOOTER_URL;


public class CourierLoginTest {
    private final String badRequestErrorMessage = "Недостаточно данных для входа";
    private final String notFoundErrorMessage = "Учетная запись не найдена";
    Courier validCourier = new Courier("Alina123", "MyB1rthD@Y", "Алина");
    Courier invalidCourier = new Courier("Karina321", "1L0veC@ts!", "Карина");

    @Before
    public void setUp() {
        RestAssured.baseURI = QA_SCOOTER_URL;
    }

    @Test
    @DisplayName("Проверить, что курьер может авторизоваться")
    @Description("Убедиться, что пользователь может авторизироваться, если логин и пароль указан правильно. Убедиться, что система возвращает id авторизованного пользователя.")
    public void testLogin() {
        CourierSteps.createNewCourier(validCourier);
        Response response = CourierSteps.loginAsExistingCourier(validCourier);
        CourierSteps.checkIdInResponse(response);
    }

    @Test
    @DisplayName("Проверить, что для авторизации нужно передать все обязательные поля")
    @Description("Убедиться, что система не позволяет авторизоваться, если одно из обязательных полей отсутствует")
    public void testLoginWithoutRequiredFields() {
        CourierSteps.createNewCourier(validCourier);
        CourierResponse expectedResponse = new CourierResponse(BAD_REQUEST_CODE, badRequestErrorMessage);
        Response response = CourierSteps.loginWithoutPassword(validCourier.getLogin());
        CourierSteps.verifyResponseData(response, BAD_REQUEST_CODE, BAD_REQUEST_STATUS, expectedResponse);
        CourierSteps.loginWithoutLogin(validCourier.getPassword());
        CourierSteps.verifyResponseData(response, BAD_REQUEST_CODE, BAD_REQUEST_STATUS, expectedResponse);

    }

    @Test
    @DisplayName("Проверить, авторизацию с некорректными логином или паролем")
    @Description("Убедиться, что система не позволяет авторизоваться, если в запросе отправлены некорректные логин или пароль")
    public void testLoginWithInvalidCredentials() {
        CourierSteps.createNewCourier(validCourier);
        testLoginWithInvalidPassword();
        testLoginWithInvalidLogin();
    }

    @Step("Проверить авторизацию с некорректным паролем")
    public void testLoginWithInvalidPassword() {
        CourierResponse expectedResponse = new CourierResponse(NOT_FOUND_CODE, notFoundErrorMessage);
        Response response = CourierSteps.loginWithLoginAndPwd(invalidCourier.getPassword(), validCourier.getLogin());
        CourierSteps.verifyResponseData(response, NOT_FOUND_CODE, NOT_FOUND_STATUS, expectedResponse);
    }

    @Step("Проверить авторизацию с некорректным логином")
    public void testLoginWithInvalidLogin() {
        CourierResponse expectedResponse = new CourierResponse(NOT_FOUND_CODE, notFoundErrorMessage);
        Response response = CourierSteps.loginWithLoginAndPwd(validCourier.getPassword(), invalidCourier.getLogin());
        CourierSteps.verifyResponseData(response, NOT_FOUND_CODE, NOT_FOUND_STATUS, expectedResponse);
    }

    @Test
    @DisplayName("Проверить логин несущестующего пользователя")
    @Description("Убедится, что система возвращает ошибку при попытке авторизироваться за несуществующего пользователя.")
    public void testLoginAsNonExistentCourier() {
        CourierResponse expectedResponse = new CourierResponse(NOT_FOUND_CODE, notFoundErrorMessage);
        Response response = CourierSteps.loginAsExistingCourier(validCourier);
        CourierSteps.verifyResponseData(response, NOT_FOUND_CODE, NOT_FOUND_STATUS, expectedResponse);
    }

    @After
    public void deleteExistingCourier() {
        CourierSteps.clearCourierData(validCourier);
    }
}
