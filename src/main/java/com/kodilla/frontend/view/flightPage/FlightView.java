package com.kodilla.frontend.view.flightPage;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.flight.FlightCarriersDto;
import com.kodilla.frontend.domain.dto.flight.FlightDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Route
public class FlightView extends VerticalLayout {
    @Autowired
    private RestTemplate restTemplate;

    private TextField fromSearchBox;
    private TextField whereSearchBox;
    private DatePicker whenSearchBox;
    private Button searchButton;
    private Button historyButton;
    private VerticalLayout searchResultLayout = new VerticalLayout();

    private LocalDate whenDate = LocalDate.now().plusDays(10);

    public FlightView() {
        drawNavigateBar();
        drawSearchMenu();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            URI url = UrlGenerator.flightsSearchURL(fromSearchBox.getValue(), whereSearchBox.getValue(), whenDate);
            final FlightDto response = restTemplate.getForObject(url, FlightDto.class);

            drawSearchResults(response);
        });

        historyButton.addClickListener(e -> {
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    UrlGenerator.FLIGHTHISTORYURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });

            //for now just first element because i need to change all request to List and change method param to List or
            // write new method with list param
            drawSearchResults(response.getBody().get(0));

        });
    }

    private void drawSearchResults(FlightDto response) {

        searchResultLayout.removeAll();
        searchResultLayout.add(FlightSearch.drawFlightResults(response, true));

    }


    private void drawNavigateBar() {
        VerticalLayout menu = NavigateBar.drawNavigateBar();
        add(menu);
    }

    private void drawSearchMenu() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        whereSearchBox = new TextField("Where you want to go?");
        whenSearchBox = new DatePicker("When?");
        fromSearchBox = new TextField("From where?");
        searchButton = new Button("Search");
        searchButton.getStyle().set("margin-top", "35px");
        historyButton = new Button("search history");
        historyButton.getStyle().set("margin-top", "35px");
        searchLayout.add(fromSearchBox);
        searchLayout.add(whereSearchBox);
        searchLayout.add(whenSearchBox);
        searchLayout.add(searchButton);
        searchLayout.add(historyButton);
        searchLayout.getStyle().set("margin", "auto");

        whenSearchBox.addValueChangeListener(event -> {
            whenDate = event.getValue();
        });

        add(searchLayout);
    }
}
