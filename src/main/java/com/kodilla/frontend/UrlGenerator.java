package com.kodilla.frontend;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

public class UrlGenerator {
    public final static String HOTEL_HISTORY_URL = "http://localhost:8080/v1/hotels/history";
    public final static String FLIGHT_HISTORY_URL = "http://localhost:8080/v1/flights/history";
    public final static String LOGO = "https://i.ibb.co/XZ8KLch/Untitled.png";
    public final static String LEFT_PALM_LOGO = "https://i.ibb.co/gtsdn3p/palm-left.png";
    public final static String RIGHT_PALM_LOGO = "https://i.ibb.co/28mQZZR/palm-right.png";
    public final static String CREATE_USER_URL = "http://localhost:8080/v1/users";

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

    public static URI userSignInURL(long id){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/signIn")
                .queryParam("userId", id)
                .build().encode().toUri();
    }

    public static URI userSignOutURL(long id){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/signOut")
                .queryParam("userId", id)
                .build().encode().toUri();
    }

    public static URI userloggedIntURL(long id){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/loggedIn")
                .queryParam("id", id)
                .build().encode().toUri();
    }

    public static URI passwordChangeURL(String oldPassword, String newPassword){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/passwordChange")
                .queryParam("id", UserAccount.getInstance().getId())
                .queryParam("oldPassword", oldPassword)
                .queryParam("newPassword", newPassword)
                .build().encode().toUri();
    }

    public static URI accountDeleteURL(long id, String password){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/delete")
                .queryParam("id", id)
                .queryParam("password", password)
                .build().encode().toUri();

    }

    public static URI getUserIdURL(String username, String password){
        return  UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/getId")
                .queryParam("username", username)
                .queryParam("password", password)
                .build().encode().toUri();
    }

    public static URI checkNewPasswordURL(long id, String newPassword){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/checkNewPassword")
                .queryParam("id", id)
                .queryParam("newPassword", newPassword)
                .build().encode().toUri();

    }


}
