package com.kodilla.frontend.view.holidayPage;

import com.kodilla.frontend.domain.dto.HolidayDto;
import com.kodilla.frontend.view.flightPage.FlightSearch;
import com.kodilla.frontend.view.hotelPage.HotelSearch;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HolidaySearch {
    public static HorizontalLayout drawHolidayResults(HolidayDto response, String origin, String destination) {
        Div hotelsResults = HotelSearch.drawHotelResults(response.getHotels(), false);
        Div tripFlightsResults = FlightSearch.drawFlightResults(response.getTripFlights(), false, origin, destination);
        Div returnFlightsResults = FlightSearch.drawFlightResults(response.getReturnFlight(), false, destination, origin);

        return new HorizontalLayout(hotelsResults, tripFlightsResults, returnFlightsResults);
    }
}
