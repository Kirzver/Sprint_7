package ru.yandex.practicum;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.practicum.config.BeforeAndAfterBaseClass;
import ru.yandex.practicum.data.CourierData;
import ru.yandex.practicum.data.RandomCourier;

import static org.hamcrest.CoreMatchers.is;


import org.hamcrest.Matchers;

public class LoginCourierTest extends BeforeAndAfterBaseClass {

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка статус-кода 200 и сообщения c id - notNull")
    public void courierCanAuthorizationTest(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(courier);
        response.assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id",Matchers.notNullValue());


    }
    @Test
    @DisplayName("Авторизация курьера с некорректным логином")
    @Description("Проверка статус-кода 404 и сообщения о ненайденной учетной записи")
    public void incorrectLoginDuringAuthorizationTest(){
        courierClient.createCourier(courier);
        courier.setLogin(RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.loginCourier(courier);
        response.assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с некорректным паролем")
    @Description("Проверка статус-кода 404 и сообщения о ненайденной учетной записи")
    public void incorrectPasswordDuringAuthorizationTest(){
        courierClient.createCourier(courier);
        courier.setPassword(RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.loginCourier(courier);
        response.assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка статус-кода 400 и сообщения о нехватке данных для входа")
    public void authorizationWithoutLoginTest(){
        CourierData courierData = new CourierData();
        courierData.setPassword(RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.loginCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message",is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка статус-кода 400 и сообщения о нехватке данных для входа")
    public void authorizationWithoutPasswordTest(){
        CourierData courierData = new CourierData();
        courierData.setLogin(RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.loginCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message",is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка статус-кода 400 и сообщения о ненайденной учетной записи")
    public void authorizationDoesNotExistCourierTest(){
        ValidatableResponse response = courierClient.loginCourier(courier);
        response.assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message",is("Учетная запись не найдена"));
    }

}
