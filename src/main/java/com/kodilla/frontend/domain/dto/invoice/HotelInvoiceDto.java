package com.kodilla.frontend.domain.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelInvoiceDto {
    private long id;
    private LocalDate date;
    private BigDecimal price;
    private long hotelId;
    private long userId;

    public HotelInvoiceDto(LocalDate date, BigDecimal price, long hotelId, long userId) {
        this.date = date;
        this.price = price;
        this.hotelId = hotelId;
        this.userId = userId;
    }
}
