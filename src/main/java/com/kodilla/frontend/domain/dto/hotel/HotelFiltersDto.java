package com.kodilla.frontend.domain.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelFiltersDto {
    private int rating;
    private int stars;
    private BigDecimal priceMoreThan;
    private BigDecimal priceLessThan;
}
