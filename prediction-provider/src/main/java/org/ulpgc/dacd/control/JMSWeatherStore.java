package org.ulpgc.dacd.control;

import com.google.gson.*;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.model.Weather;

import javax.jms.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class JMSWeatherStore implements WeatherStore {

    public static String url;

    public static String topicName;

    public JMSWeatherStore(String url, String topicName) {
        this.url = url;
        this.topicName = topicName;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(formatter.format(src)))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, typeOfT, context) ->
                    Instant.from(formatter.parse(json.getAsString())))
            .create();

    public void save(Weather weather) {
        if (weather != null) {
            try {
                System.out.println("hola");
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Topic topic = session.createTopic(topicName);

                MessageProducer producer = session.createProducer(topic);

                try {
                    String jsonString = gson.toJson(weather);
                    System.out.println(jsonString);

                    ObjectMessage message = session.createObjectMessage();
                    message.setObject(jsonString);

                    producer.send(message);

                    System.out.println("Weather information sent to topic " + topicName);
                    System.out.println("Message sent: " + message);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error during JSON serialization: " + e.getMessage());
                } finally {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
