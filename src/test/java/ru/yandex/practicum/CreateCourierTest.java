package ru.yandex.practicum;

import static org.hamcrest.CoreMatchers.is;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.practicum.config.BeforeAndAfterBaseClass;
import ru.yandex.practicum.data.CourierData;
import ru.yandex.practicum.data.RandomCourier;


public class CreateCourierTest extends BeforeAndAfterBaseClass {


    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка статус-кода 200 и сообщения ok - true")
    public void isPossibleToCreateACourierTest() {
       ValidatableResponse response = courierClient.createCourier(courier);
       response.assertThat()
               .statusCode(HttpStatus.SC_CREATED)
               .body("ok", is(true));

    }

    @Test
    @DisplayName("Созданих двух одинаковых курьеров")
    @Description("Проверка статус-кода 409 и сообщения об использовании логина")
    public void cannotCreateTwoIdenticalCouriersTest(){
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание двух курьеров с одинаковыми логинами")
    @Description("Проверка статус-кода 409 и сообщения об использовании логина")
    public void cannotCreateTwoIdenticalLoginTest(){
        courierClient.createCourier(courier);
        CourierData randomCourierTwo = RandomCourier.getRandomCourier();
        randomCourierTwo.setLogin(courier.getLogin());
        ValidatableResponse response = courierClient.createCourier(randomCourierTwo);
        response.assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка статус-кода 400 и сообщения о нехватке данных")
    public void createCourierWithoutLoginTest() {
        CourierData courierData = new CourierData(null,RandomCourier.getRandomLoginOrPasswordOrName(),
                RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка статус-кода 400 и сообщения о нехватке данных")
    public void createCourierWithoutPasswordTest() {
        CourierData courierData = new CourierData(RandomCourier.getRandomLoginOrPasswordOrName(), null,
                RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без имени")
    @Description("Проверка статус-кода 400 и сообщения о нехватке данных")
    public void createCourierWithoutFirstNameTest() {
        CourierData courierData = new CourierData(RandomCourier.getRandomLoginOrPasswordOrName(),
                RandomCourier.getRandomLoginOrPasswordOrName(), null);
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без логина и пароля")
    @Description("Проверка статус-кода 400 и ответа о нехватке данных")
    public void createCourierWithoutLoginAndPasswordTest() {
        CourierData courierData = new CourierData(null, null, RandomCourier.getRandomLoginOrPasswordOrName());
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без логина и имени")
    @Description("Проверка статус-кода 400 и ответа о нехватке данных")
    public void createCourierWithoutLoginAndFirstNameTest() {
        CourierData courierData = new CourierData(null, RandomCourier.getRandomLoginOrPasswordOrName(), null);
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание курьера без пароля и имени")
    @Description("Проверка статус-кода 400 и ответа о нехватке данных")
    public void createCourierWithoutPasswordAndFirstNameTest() {
        CourierData courierData = new CourierData(RandomCourier.getRandomLoginOrPasswordOrName(), null, null);
        ValidatableResponse response = courierClient.createCourier(courierData);
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }




}
