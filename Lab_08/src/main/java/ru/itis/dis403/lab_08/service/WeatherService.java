package ru.itis.dis403.lab_08.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import org.springframework.stereotype.Service;
import ru.itis.dis403.lab_08.model.Weather;
import java.io.IOException;
import java.time.Duration;

@Service
public class WeatherService {

    /*
        Подключаемся к брокеру
        Оформляем подписку
        Запрашиваем синхронно сообщение
        Ждем 3 сек. максимум
     */
    public Weather getWeather() {
        String subject = "Weather";
        Weather result = null;
        try (Connection nc = Nats.connect("nats://localhost:4222")) {

            Subscription sub = nc.subscribe(subject);
            Message msg = sub.nextMessage(Duration.ofSeconds(3));
            if (msg != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                result = mapper.readValue(msg.getData(), Weather.class);
                System.out.println("Получено сообщение: " + result);
            } else {
                System.out.println("Сообщение не получено.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
