package com.kodilla.frontend;

import com.kodilla.frontend.domain.dto.hotel.HotelLiteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationScheduler.class);

    public static String mostSearchedLocation = "";

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedDelay = 30000)
    public void getInfo(){
        HotelLiteDto hotelLiteDto = restTemplate.getForObject(UrlGenerator.MOST_INTERESTED_LOCATION, HotelLiteDto.class);
        if(hotelLiteDto != null)
            mostSearchedLocation = hotelLiteDto.getDestinationLocation();
        LOGGER.info("Updated info about the most-searched location");

    }

}
