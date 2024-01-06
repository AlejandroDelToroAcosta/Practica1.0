package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Hotel;
import org.ulpgc.dacd.model.Reservation;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private ReservationStore reservationStore;

    public HotelController(ReservationStore reservationStore) {
        this.reservationStore = reservationStore;
    }

    public void execute(){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        if (time.isAfter(LocalTime.of(17,0))){
            date = date.plusDays(1);
        }
        for (int i = 0; i < 2; i++) {
            String checkIn = date.format(DateTimeFormatter.ISO_DATE);
            String checkOut = date.plusDays(1).format(DateTimeFormatter.ISO_DATE);


            Reservation laPardela = new Reservation(checkIn, checkOut, "La Pardela", "g3360203-d25244761", "La Graciosa");
            Reservation apartmentsLaGraciosa = new Reservation(checkIn, checkOut, "Apartments la Graciosa", "g3360203-d3411835", "La Graciosa");
            Reservation evitaBeach = new Reservation(checkIn, checkOut, "Evita Beach", "g1190272-d2645782", "La Graciosa");


            Reservation barceloFuerteventuraMar = new Reservation(checkIn, checkOut, "Barceló Fuerteventura Mar", "g658907-d255145", "Fuerteventura");
            Reservation fuerteventuraPrinces = new Reservation(checkIn, checkOut, "Fuerteventura Princes", "g673234-d500267", "Fuerteventura");
            Reservation meliaFuerteventura = new Reservation(checkIn, checkOut, "Melia Fuerteventura", "g783945-d237051", "Fuerteventura");


            //tenerife
            Reservation jardinTropical = new Reservation(checkIn, checkOut, "Jardín Tropical", "g662606-d248458","Tenerife");
            Reservation hotelBotanico = new Reservation(checkIn, checkOut, "Hotel Botánico", "g187481-d252888","Tenerife");
            Reservation h10Atlantic = new Reservation(checkIn, checkOut, "H10 Atlantic", "g315919-d15327583","Tenerife");


            //elhierro

            Reservation puntaGrande = new Reservation(checkIn, checkOut, "Punta Grande", "g2139290-d627753", "El Hierro");
            Reservation paradorElHierro = new Reservation(checkIn, checkOut, "Parador El Hierro", "g187474-d277394", "El Hierro");
            Reservation apartamentosLosVerodes = new Reservation(checkIn, checkOut, "Apartamentos Los Verodes", "g2139290-d2010097", "El Hierro");


//Gran Cnaria


            Reservation lopesanBaobabResort = new Reservation(checkIn, checkOut, "Lopesan Baobab Resort", "g2089121-d1488268", "Gran Canaria");
            Reservation lopesanCostaMeloneras = new Reservation(checkIn, checkOut, "Lopesan Costa Meloneras", "g2089121-d241729", "Gran Canaria");
            Reservation aboraBuenaventura = new Reservation(checkIn, checkOut, "Abora Buenaventura", "g562819-d289606", "Gran Canaria");

            //Lanarote

            Reservation h10RubiconPalace = new Reservation(checkIn, checkOut, "H10 Rubicón Palace", "g652121-d289259", "Lanzarote");
            Reservation hotelLancelot = new Reservation(checkIn, checkOut, "Hotel Lancelot", "g187478-d273097", "Lanzarote");
            Reservation hotelBeatrizCostaSpa = new Reservation(checkIn, checkOut, "Hotel Beatriz Costa Spa", "g659633-d291300", "Lanzarote");


            //lapalma


            Reservation h10TaburientePlaya = new Reservation(checkIn, checkOut, "H10 Taburiente Playa", "g659966-d289252", "La Palma");
            Reservation paradorDeLaPalma = new Reservation(checkIn, checkOut, "Parador De La Palma", "g642213-d482745", "La Palma");
            Reservation bananaGardenLaPalma = new Reservation(checkIn, checkOut, "Banana Garden La Palma", "g187476-d24074151", "La Palma");


            Reservation hotelTorredelConde = new Reservation(checkIn, checkOut, "Hotel Torre Del Conde", "g187470-d566709","La Gomera");
            Reservation hotelVillaGomera = new Reservation(checkIn, checkOut, "Hotel Villa Gomera", "g187470-d614341","La Gomera");
            Reservation hotelGranRey = new Reservation(checkIn, checkOut, "Hotel Gran Rey", "g674782-d616495","La Gomera");

            List<Reservation> reservations = List.of(laPardela, evitaBeach, apartmentsLaGraciosa, barceloFuerteventuraMar,
                    fuerteventuraPrinces, meliaFuerteventura, h10Atlantic, jardinTropical, hotelBotanico,
                    puntaGrande, apartamentosLosVerodes, paradorElHierro,
                    lopesanBaobabResort, lopesanCostaMeloneras, aboraBuenaventura,
                    h10RubiconPalace, hotelLancelot, hotelBeatrizCostaSpa,
                    hotelTorredelConde, hotelVillaGomera, hotelGranRey);

            ArrayList<Hotel> hotelArrayList = new ArrayList<>();
            getHotelCall(reservations, hotelArrayList);
            saveCall(hotelArrayList);

        }
    }

    private void saveCall(ArrayList<Hotel> hotelArrayList) {
        for (Hotel iteredHotel:hotelArrayList){
            reservationStore.save(iteredHotel);
        }
    }


    private ArrayList<Hotel> getHotelCall(List<Reservation> reservations, ArrayList<Hotel> hotels) {
        HotelProvider hotelProvider = new HotelMapProvider();
        for (Reservation reservation : reservations) {
            Hotel hotel = hotelProvider.getHotel(reservation);
            hotels.add(hotel);
        }
        System.out.println("Lista:"+hotels);
        return hotels;
    }
}
