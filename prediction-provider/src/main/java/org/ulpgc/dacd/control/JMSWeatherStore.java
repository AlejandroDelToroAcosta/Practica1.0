package org.ulpgc.dacd.control;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.model.Weather;
import org.ulpgc.dacd.control.WeatherStore;


import javax.jms.*;
import java.time.Instant;


public class JMSWeatherStore implements WeatherStore {
    private final String url;
    private static String topicName = "prediction.Weather";

    public JMSWeatherStore(String url) {
        this.url = url;
    }

    @Override
    public void save(Weather weather) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topicName);

            MessageProducer producer = session.createProducer(destination);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                            context.serialize(src.toString()))
                    .create();

            String json = gson.toJson(weather);
            TextMessage message = session.createTextMessage(json);

            producer.send(message);

            System.out.println("Weather sent to broker:" + json);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}