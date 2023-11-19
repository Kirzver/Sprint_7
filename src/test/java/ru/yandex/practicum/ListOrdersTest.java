package ru.yandex.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;


import static io.restassured.RestAssured.given;

public class ListOrdersTest {

    final static String GET_ORDERS = "/api/v1/orders";


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка статус-кода 200 и сообщения c orders - notNull")
    @Step("Отравка GET-запроса для получения списка заказов по /api/v1/orders")
    public void getOrderListTest(){
        given()
                .header("Content-type", "application/json")
                .get(GET_ORDERS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("orders",Matchers.notNullValue());




    }
}
