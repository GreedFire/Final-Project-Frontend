package com.kodilla.frontend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeviceDto {
    private String ipAddress;
    private String hostName;
    private String OS;
}
