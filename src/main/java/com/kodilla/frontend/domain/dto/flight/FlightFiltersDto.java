package com.kodilla.frontend.domain.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FlightFiltersDto {
    private String carrierClass;
    private BigDecimal priceMoreThan;
    private BigDecimal priceLessThan;
}
