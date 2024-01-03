package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonObject resultObject = jsonObject.getAsJsonObject("result");

            JsonArray ratesArray = resultObject.getAsJsonArray("rates");

            hotel = new Hotel(ratesArray,reservation);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }
}