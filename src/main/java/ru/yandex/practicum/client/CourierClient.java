package ru.yandex.practicum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.data.CourierData;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String CREATE_COURIER = "api/v1/courier";
    private static final String LOGIN_COURIER = "api/v1/courier/login";
    private static final String DELETE_COURIER = "api/v1/courier/";

    @Step("Отправка POST-запроса для создания курьера по api/v1/courier")
    public ValidatableResponse createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then();
    }
    @Step("Отправка POST-запроса для авторизации курьера по api/v1/courier/login")
    public ValidatableResponse loginCourier(CourierData courierCredentials){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courierCredentials)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Отправка DELETE-запроса для удаления курьера по api/v1/courier/+id")
    public void deleteCourier(int id){
        given()
                .spec(requestSpecification())
                .and()
                .when()
                .delete(DELETE_COURIER + id)
                .then();
    }
}
