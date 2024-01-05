package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.dacd.model.Hotel;

import javax.jms.*;
import java.time.Instant;

public class JMSReservationStore implements ReservationStore{
    private final String url;
    private static final String topicName = "reservation.Hotel";

    public JMSReservationStore(String url) {
        this.url = url;
    }

    @Override
    public void save(Hotel hotel) {
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

            String json = gson.toJson(hotel);
            TextMessage message = session.createTextMessage(json);

            producer.send(message);

            System.out.println("Hotel sent to broker:" + json);

            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
