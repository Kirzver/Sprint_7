package ru.yandex.practicum.data;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practicum.data.CourierData;

public class RandomCourier {

    public static CourierData getRandomCourier() {
        String login = RandomStringUtils.randomAlphabetic(5,10);
        String password = RandomStringUtils.randomAlphabetic(4,10);
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierData(login, password, firstName);
    }

    public static String getRandomLoginOrPasswordOrName(){
        return RandomStringUtils.randomAlphabetic(4,10);
    }


}
