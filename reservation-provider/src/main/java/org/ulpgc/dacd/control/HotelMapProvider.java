package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Reservation;

import java.io.IOException;

public class HotelMapProvider implements HotelProvider {
    public Hotel getHotel(Reservation reservation) {
        Hotel hotel = null;
        try {
            String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + reservation.getApikey() +
                    "&chk_in=" + reservation.getCheckIn() + "&chk_out=" + reservation.getCheckOut();
            String jsonString = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
            System.out.println(jsonString);
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);

            if (jsonElement != null && !jsonElement.isJsonNull() && jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonObject resultObject = jsonObject.getAsJsonObject("result");

                JsonArray ratesArray = resultObject.getAsJsonArray("rates");

                hotel = new Hotel(ratesArray, reservation);
            } else {
                // Manejar el caso en que el jsonElement es nulo o no es un objeto JSON válido
                System.out.println("La respuesta JSON no es válida o está vacía.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }
}