package ru.itis.dis403.lab_08.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Nats;
import ru.itis.dis403.lab_08.model.Weather;

import java.time.LocalDateTime;
import java.util.Random;

public class WeatherPublisher {

    public static void main(String[] args) {

        String subject = "Weather";

        try (Connection nc = Nats.connect("nats://host.docker.internal:4222")) {

            while (true) {

                Random random = new Random();
                Weather weather = Weather.builder()
                        .city("Казань")
                        .temp(10. + random.nextDouble() * 2 - 1)
                        .pressure(744 + random.nextDouble() * 4 - 2)
                        .windSpeed(3 + random.nextDouble() * 4 - 2)
                        .windDirection("СЗ")
                        .dateTime(LocalDateTime.now())
                        .build();

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                byte[] msg = mapper.writeValueAsBytes(weather);

                // Отправка сообщения
                nc.publish(subject, msg);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
