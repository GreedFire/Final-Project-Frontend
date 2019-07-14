package com.kodilla.frontend.domain.dto.flight;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightCarriersDto {

    private long id;

    private int carrierId;

    private String carrierName;

    private BigDecimal price;

    private LocalDateTime departureDate;


}
