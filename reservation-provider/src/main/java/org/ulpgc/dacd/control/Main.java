package org.ulpgc.dacd.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ReservationStore reservationStore = new JMSReservationStore(args[0]);
                HotelController hotelController = new HotelController(reservationStore);
                hotelController.execute();
            }

        }, 0, 6, TimeUnit.HOURS);
    }
}
