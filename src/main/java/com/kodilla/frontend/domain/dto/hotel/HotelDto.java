package com.kodilla.frontend.domain.dto.hotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("userrating")
    private String userRating;

    @JsonProperty("price")
    private String price;

    @JsonProperty("stars")
    private int stars;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("displayaddress")
    private String displayaddress;

    @JsonProperty("thumburl")
    private String thumburl;

    private String HotelResponseId;
}
