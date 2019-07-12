package com.kodilla.frontend.domain.dto.flight.flights.lists;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightQuotesDto {

    @JsonProperty("MinPrice")
    private BigDecimal price;

    @JsonProperty("OutBoundLeg")
    private OutBoundLegDto outBoundLeg;



}
