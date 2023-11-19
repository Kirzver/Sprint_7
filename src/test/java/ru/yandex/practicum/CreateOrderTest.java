package ru.yandex.practicum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.data.OrderData;

import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderData orderData;

    final static String GET_ORDERS = "/api/v1/orders";

    public CreateOrderTest(OrderData orderData){
        this.orderData = orderData;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name="Тестовые данные: {0}")
    public static Object[][] getOrderData(){
        return new Object[][]{
                {new OrderData("Олег","Алексеев","Ул. Пушкина, д. 29",4,"89992094329",4,"2024-08-24","i wait", List.of("BLACK"))},
                {new OrderData("Юля","Иванова","Проспект Большевиков, дом 302",2,"89992004148",3,"2025-01-11","lets fast",List.of("GREY"))},
                {new OrderData("Дмитрий","Жиглов","Ул. Некрасова, д. 399, кв. 55",3,"89211224500",2,"2023-11-21","go go go", List.of())},
                {new OrderData("Кристина","Молоткова","Английская 3к2 кв 88",4,"89002012222",1,"2027-04-02","Очень жду",List.of("BLACK", "GREY"))},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка статус-кода 201 и сообщения с track - notNull")
    @Step("Отравка POST-запроса для создания заказа по /api/v1/orders")
    public void createOrderTest(){
        ValidatableResponse response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(orderData)
                        .when()
                        .post(GET_ORDERS)
                        .then();
        response.assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", Matchers.notNullValue());

    }



}
