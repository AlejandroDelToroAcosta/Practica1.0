package org.ulpgc.dacd.model;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class ComandSet {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public void execute(String url) {
        System.out.println("Welcome to the Reservation System!");

        Instant checkInDate = Instant.now();

        if (LocalTime.now().isAfter(LocalTime.of(17,0))){
            checkInDate = checkInDate.plus(1, ChronoUnit.DAYS);
        }

        Island chosenIsland = chooseIsland();

        Instant checkOutDate = chooseReservationDate("Check-out", checkInDate);

        long reservationDuration = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        System.out.println("Reservation duration: " + reservationDuration + " days");
        System.out.println("Chosen island: " + chosenIsland.toString());

        displayAverageWeatherData(chosenIsland.getCapital(), checkInDate, checkOutDate, url);

        hotelAdvisor(chosenIsland.getName(), checkOutDate, url);

    }

    private static void displayAverageWeatherData(String island, Instant checkInDate, Instant checkOutDate, String url) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT AVG(clouds) AS avg_clouds, AVG(wind) AS avg_wind, AVG(temperature) AS avg_temperature, AVG(humidity) AS avg_humidity, AVG(pop) AS avg_pop " +
                             "FROM weather " +
                             "WHERE name = ? AND instant BETWEEN ? AND ?")) {

            statement.setString(1, island);
            statement.setString(2, formatInstantAsDate(checkInDate));
            statement.setString(3, formatInstantAsDate(checkOutDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double avgClouds = resultSet.getDouble("avg_clouds");
                    double avgWind = resultSet.getDouble("avg_wind");
                    double avgTemperature = resultSet.getDouble("avg_temperature");
                    double avgHumidity = resultSet.getDouble("avg_humidity");
                    double avgPop = resultSet.getDouble("avg_pop");

                    System.out.println("Average weather data " +
                            " during the reservation period (" + formatInstantAsDate(checkInDate) +
                            " to " + formatInstantAsDate(checkOutDate) + "):");
                    System.out.println("Average Cloudiness: " + avgClouds);
                    System.out.println("Average Wind Speed: " + avgWind);
                    System.out.println("Average Temperature: " + avgTemperature);
                    System.out.println("Average Humidity: " + avgHumidity);
                    System.out.println("Average Probability of Precipitation: " + avgPop);
                    System.out.println("----------------------------------------");
                } else {
                    System.out.println("No weather data available for the reservation period.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Instant chooseReservationDate(String type, Instant chekin) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the " + type + " date:");
        System.out.println("1. In 1 day");
        System.out.println("2. In 2 days");
        System.out.println("3. In 3 days");
        System.out.println("4. In 4 days");

        int option = getUserOption();

        Instant reservationDate;
        switch (option) {

            case 1:
                reservationDate = chekin.plus(1, ChronoUnit.DAYS);
                break;
            case 2:
                reservationDate = chekin.plus(2, ChronoUnit.DAYS);
                break;
            case 3:
                reservationDate = chekin.plus(3, ChronoUnit.DAYS);
                break;
            case 4:
                reservationDate =chekin.plus(4, ChronoUnit.DAYS);
                break;
            case 5:
                reservationDate = chekin.plus(5, ChronoUnit.DAYS);
                break;
            default:
                System.out.println("Invalid option. Selecting today by default.");
                reservationDate = Instant.now();
        }

        System.out.println(type + " date selected: " + formatInstantAsDate(reservationDate));

        return reservationDate;
    }

    private static int getUserOption() {
        System.out.print("Enter the option number: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private static Island chooseIsland() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the island you want to visit:");
        System.out.println("1. Tenerife ");
        System.out.println("2. Gran Canaria ");
        System.out.println("3. Lanzarote ");
        System.out.println("4. Fuerteventura ");
        System.out.println("5. La Palma ");
        System.out.println("6. La Gomera ");
        System.out.println("7. El Hierro ");
        System.out.println("8. La Graciosa ");

        int option = getUserOption();

        switch (option) {
                case 1:
                    return new Island("Tenerife", "Santa Cruz de Tenerife");
                case 2:
                    return new Island("Gran Canaria", "Las Palmas de Gran Canaria");
                case 3:
                    return new Island("Lanzarote", "Arrecife");
                case 4:
                    return new Island("Fuerteventura", "Puerto del Rosario");
                case 5:
                    return new Island("La Palma", "Santa Cruz de La Palma");
                case 6:
                    return new Island("La Gomera", "San Sebastián de La Gomera");
                case 7:
                    return new Island("El Hierro", "Valverde");
                case 8:
                    return new Island("La Graciosa", "Caleta de Sebo");
                default:
                    System.out.println("Not valid option.");
                    return new Island("Tenerife", "Santa Cruz de Tenerife");
        }
    }

    private static String formatInstantAsDate(Instant instant) {
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(dateFormatter);
    }
    private static void hotelAdvisor(String isla, Instant checkOut, String url) {
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT rate, tax, check_in_date, check_out_date, reservation_name " +
                             "FROM hotels WHERE island = ? AND check_out_date = ? ORDER BY rate DESC LIMIT 5")) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedCheckOut = checkOut.atZone(ZoneId.systemDefault()).format(formatter);

            statement.setString(1, isla);
            statement.setString(2, formattedCheckOut);

            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("Recomended hotels in " + isla + ":");
                if (resultSet.next()) {
                    int rate = resultSet.getInt("rate");
                    int tax = resultSet.getInt("tax");
                    String checkInDate = resultSet.getString("check_in_date");
                    String checkOutDate = resultSet.getString("check_out_date");
                    String reservationName = resultSet.getString("reservation_name");

                    long reservationDuration = ChronoUnit.DAYS.between(
                            LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate));

                    int totalPrice = rate * (int) reservationDuration + tax;

                    System.out.println("Hotel: " + reservationName);
                    System.out.println("Price per night: " + rate);
                    System.out.println("Tax: " + tax);
                    System.out.println("CheckIn: " + checkInDate);
                    System.out.println("CheckOut: " + checkOutDate);
                    System.out.println("Final price: " + totalPrice);
                    System.out.println("----------------------------------------");

                }else {
                    System.out.println("No hotels available for the given search.\n");
                    System.out.println("Try with a different check-out date.\n");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}