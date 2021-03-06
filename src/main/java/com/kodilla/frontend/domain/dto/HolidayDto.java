package com.kodilla.frontend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.frontend.domain.dto.flight.FlightDto;
import com.kodilla.frontend.domain.dto.hotel.HotelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayDto {

    private List<HotelDto> hotels;
    private List<FlightDto> tripFlights;
    private List<FlightDto> returnFlight;
}
