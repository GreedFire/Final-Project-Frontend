package com.kodilla.frontend.domain.dto.flight.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodilla.frontend.domain.dto.flight.location.FlightLocationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightLocationResponseDto {

    @JsonProperty("Places")
    FlightLocationDto[] places;
}
