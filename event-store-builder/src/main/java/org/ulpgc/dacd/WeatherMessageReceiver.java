package org.ulpgc.dacd;



import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.model.Weather;

import javax.jms.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.ulpgc.dacd.model.Weather.ss;
import static org.ulpgc.dacd.model.Weather.ts;

public class WeatherMessageReceiver implements MessageReceiver {
    public static String url;
    public static String topicName;
    public static String clientId = "client1";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;


    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, typeOfSrc, context) ->
                    new JsonPrimitive(formatter.format(src)))
            .registerTypeAdapter(Instant.class, (JsonDeserializer<Instant>) (json, typeOfT, context) ->
                    Instant.from(formatter.parse(json.getAsString())))
            .create();

    public WeatherMessageReceiver(String url, String topicName) {
        this.url = url;
        this.topicName = topicName;
    }

    @Override
    public List<Weather> receive() {
        Connection connection = null;
        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);

            MessageConsumer consumer = session.createDurableSubscriber(topic, clientId);

            List<Weather> weatherList = Collections.synchronizedList(new ArrayList<>());

            consumer.setMessageListener(message -> {

                if (message instanceof ObjectMessage) {
                    ObjectMessage objectMessage = (ObjectMessage) message;
                    try {
                        String json = (String) objectMessage.getObject();
                        Weather weather = gson.fromJson(json, Weather.class);
                        synchronized (weatherList) {
                            weatherList.add(weather);

                        }
                        System.out.println(weatherList);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread.sleep(30000);


            return weatherList;


        } catch (JMSException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Cerrar la conexión en el bloque finally para asegurarse de que se cierre
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();  // O manejar la excepción de una manera significativa para tu aplicación
                }
            }
        }
    }

}