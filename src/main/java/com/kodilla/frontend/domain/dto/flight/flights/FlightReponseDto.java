package com.kodilla.frontend.domain.dto.flight.flights;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.frontend.domain.dto.flight.flights.lists.FlightCarriersDto;
import com.kodilla.frontend.domain.dto.flight.flights.lists.FlightPlacesDto;
import com.kodilla.frontend.domain.dto.flight.flights.lists.FlightQuotesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightReponseDto {
    @JsonProperty("Quotes")
    private FlightQuotesDto[] quotes;

    @JsonProperty("Places")
    private FlightPlacesDto[] places;

    @JsonProperty("Carriers")
    private FlightCarriersDto[] carriers;
}
