package com.kodilla.frontend.domain.dto.hotel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto {

    @JsonProperty("searchid")
    private String searchId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("destinationLocation")
    private String destinationLocation;

    @JsonProperty("shareURL")
    private String shareURL;

    @JsonProperty("hotelset")
    private HotelSetDto[] hotels;

    @Override
    public String toString() {
        return "HotelDto{" +
                "searchId='" + searchId + '\'' +
                ", currency='" + currency + '\'' +
                ", destinationLocation='" + destinationLocation + '\'' +
                ", shareURL='" + shareURL + '\'' +
                ", hotelSet=" + Arrays.toString(hotels) +
                '}';
    }
}
