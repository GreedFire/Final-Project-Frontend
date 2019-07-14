package com.kodilla.frontend.domain.dto.flight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDto {

    private long id;

    private LocalDateTime departureDate;

    private String origin;

    private String destination;

    private List<FlightCarriersDto> carriers;

    public FlightDto(long id, LocalDateTime departureDate, String origin, String destination) {
        this.id = id;
        this.departureDate = departureDate;
        this.origin = origin;
        this.destination = destination;
    }
}
