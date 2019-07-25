package com.kodilla.frontend.view.flightPage;

import com.kodilla.frontend.domain.dto.flight.FlightCarriersDto;
import com.kodilla.frontend.domain.dto.flight.FlightDto;
import com.kodilla.frontend.view.account.SignInView;
import com.kodilla.frontend.view.booking.BookFlightView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlightSearch {
    public static Div drawFlightResults(List<FlightDto> response, boolean marginAuto, String origin, String destination) {
        Div resultDiv = new Div();
        for (FlightDto dto : response) {
            if (marginAuto)
                resultDiv.getStyle().set("margin", "auto");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (FlightCarriersDto carrier : dto.getCarriers()) {
                //COMPONENTS
                Div carrierDiv = new Div();
                Button bookButton = new Button("Book");
                Label carrierId = new Label("Carrier ID: " + carrier.getCarrierId());
                Label carrierName = new Label("Carrier name: " + carrier.getCarrierName());
                Label carrierClass = new Label("Class: " + carrier.getCarrierClass());
                Label carrierDate = new Label("Departure: " + carrier.getDepartureDate().format(formatter));
                Label carrierPrice = new Label(carrier.getPrice().intValueExact() + "$");
                HorizontalLayout idAndNameLayout = new HorizontalLayout(carrierId, carrierName);
                HorizontalLayout priceAndBook = new HorizontalLayout(carrierPrice, bookButton);
                VerticalLayout carrierLayout = new VerticalLayout(idAndNameLayout, carrierClass, carrierDate, priceAndBook);
                carrierDiv.add(carrierLayout);
                resultDiv.add(carrierDiv);
                //CSS
                carrierPrice.getStyle().set("font-size", "30px");
                carrierPrice.getStyle().set("font-weight", "bold");
                carrierDate.getStyle().set("font-size", "20px");
                carrierDate.getStyle().set("font-weight", "bold");
                carrierClass.getStyle().set("font-weight", "bold");
                carrierName.getStyle().set("font-weight", "bold");
                carrierId.getStyle().set("font-weight", "bold");
                carrierDiv.getStyle().set("margin-bottom", "5px");
                carrierDiv.getStyle().set("margin", "auto");
                carrierDiv.getStyle().set("width", "400px");
                carrierDiv.getStyle().set("border-style", "solid");
                carrierDiv.getStyle().set("border-width", "2px");

                bookButton.addClickListener(e -> {
                    if (UI.getCurrent().getId().isPresent()) {
                        long userId = Long.parseLong(UI.getCurrent().getId().get());
                        if (userId != 0) {
                            BookFlightView.setCarrier(carrier);
                            UI.getCurrent().navigate(BookFlightView.class);
                        } else {
                            UI.getCurrent().navigate(SignInView.class);
                        }
                    } else UI.getCurrent().navigate(SignInView.class);
                });
            }
        }

        return resultDiv;
    }
}
