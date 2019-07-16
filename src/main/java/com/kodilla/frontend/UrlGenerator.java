package com.kodilla.frontend;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

public class UrlGenerator {
    public final static String HOTELHISTORYURL = "http://localhost:8080/v1/hotels/history";
    public final static String FLIGHTHISTORYURL = "http://localhost:8080/v1/flights/history";

    public static URI hotelsSearchURL(int rooms, String where, LocalDate when, LocalDate until, int adult) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/hotels")
                .queryParam("rooms", rooms)
                .queryParam("location", where)
                .queryParam("checkin", when)
                .queryParam("checkout", until)
                .queryParam("adults", adult)
                .build().encode().toUri();
    }

    public static URI filterHotelsURL(String searchId, int rating, int stars, int priceMoreThan, int priceLessThan) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/hotels/filter")
                .queryParam("responseId", searchId)
                .queryParam("rating", rating)
                .queryParam("stars", stars)
                .queryParam("priceMore", priceMoreThan)
                .queryParam("priceLess", priceLessThan)
                .build().encode().toUri();
    }

    public static URI filterFlightsURL(long searchId, String carrierClass, int priceMoreThan, int priceLessThan){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/flights/filter")
                .queryParam("responseId", searchId)
                .queryParam("carrierClass", carrierClass)
                .queryParam("priceMoreThan", priceMoreThan)
                .queryParam("priceLessThan", priceLessThan)
                .build().encode().toUri();
    }

    public static URI holidaySearchURL(int room, String origin, String destination, LocalDate when, LocalDate until, int adults) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/holiday")
                .queryParam("rooms", room)
                .queryParam("originPlace", origin)
                .queryParam("destinationPlace", destination)
                .queryParam("checkin", when)
                .queryParam("checkout", until)
                .queryParam("adults", adults)
                .build().encode().toUri();
    }

    public static URI flightsSearchURL(String origin, String destination, LocalDate when) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/flights")
                .queryParam("originPlace", origin)
                .queryParam("destinationPlace", destination)
                .queryParam("outboundPartialDate", when)
                .build().encode().toUri();
    }

}
