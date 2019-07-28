package com.kodilla.frontend.domain.dto.hotel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelResponseDto {

    @JsonProperty("searchid")
    private String searchId;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("destinationLocation")
    private String destinationLocation;

    @JsonProperty("shareURL")
    private String shareURL;

    @JsonProperty("hotelset")
    private List<HotelDto> hotels;

    @Override
    public String toString() {
        return "HotelDto{" +
                "searchId='" + searchId + '\'' +
                ", currency='" + currency + '\'' +
                ", destinationLocation='" + destinationLocation + '\'' +
                ", shareURL='" + shareURL + '\'' +
                ", hotels=" + hotels +
                '}';
    }

    public HotelResponseDto(String searchId, String currency, String destinationLocation, String shareURL) {
        this.searchId = searchId;
        this.currency = currency;
        this.destinationLocation = destinationLocation;
        this.shareURL = shareURL;
    }
}

