package ru.yandex.practicum.config;


import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import ru.yandex.practicum.client.CourierClient;
import ru.yandex.practicum.data.CourierData;
import ru.yandex.practicum.data.RandomCourier;


public class BeforeAndAfterBaseClass {

    public CourierClient courierClient;
    public CourierData courier;

    @Before
    public void setup() {
        courierClient = new CourierClient();
        courier = RandomCourier.getRandomCourier();
    }

    @After
    public void cleanUp(){
        ValidatableResponse response= courierClient.loginCourier(courier);
        if(response.extract().statusCode() == 200)
            courierClient.deleteCourier(response.extract().path("id"));

    }


}
