package org.ulpgc.dacd.control;

public class Main {
    public static void main(String[] args) {
        ReservationStore reservationStore = new JMSReservationStore(args[0]);
        HotelController hotelController = new HotelController(reservationStore);
        hotelController.execute();
    }
}
