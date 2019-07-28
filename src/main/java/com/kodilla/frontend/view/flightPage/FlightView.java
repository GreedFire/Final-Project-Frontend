package com.kodilla.frontend.view.flightPage;

import com.kodilla.frontend.NotificationScheduler;
import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.FlightFiltersDto;
import com.kodilla.frontend.domain.dto.flight.FlightDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Route
public class FlightView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightView.class);

    private TextField fromSearchBox;
    private TextField whereSearchBox;
    private Button searchButton;
    private Button historyButton;
    private VerticalLayout searchResultLayout = new VerticalLayout();
    private LocalDate whenDate = LocalDate.now().plusDays(10);

    private Div filterNavi;
    private Select<String> carrierClass;
    private TextField priceMoreThan;
    private TextField priceLessThan;
    private Button filterButton = new Button("Filter");

    private long SEARCHID;

    public FlightView() {
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSearchMenu();
        drawFlightFilters();
        add(searchResultLayout);
        Notification.show("The most searched location is: " + NotificationScheduler.mostSearchedLocation);

        searchButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Searching for flights");
            URI url = UrlGenerator.flightsSearchURL(fromSearchBox.getValue(), whereSearchBox.getValue(), whenDate);
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });
            if (response.getBody() != null && response.getBody().size() > 0) {
                SEARCHID = response.getBody().get(0).getId();
                filterNavi.setVisible(true);
                drawSearchResults(response.getBody());
            } else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);

        });

        historyButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Searching for flights history");
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    UrlGenerator.FLIGHT_HISTORY_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });
            if (response.getBody() != null && response.getBody().size() > 0) {
                drawSearchResults(response.getBody());
            } else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });

        filterButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Save flights filters");
            FlightFiltersDto filters = new FlightFiltersDto(carrierClass.getValue(), new BigDecimal(priceMoreThan.getValue()), new BigDecimal(priceLessThan.getValue()));
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<FlightFiltersDto> request = new HttpEntity<>(filters, headers);
            restTemplate.postForObject(UrlGenerator.SAVE_FLIGHT_FILTERS, request, Void.class);

            LOGGER.info("Filtering flights");
            URI url = UrlGenerator.filterFlightsURL(SEARCHID, carrierClass.getValue(),
                    Integer.parseInt(priceMoreThan.getValue()), Integer.parseInt(priceLessThan.getValue()));
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });

            if (response.getBody() != null && response.getBody().size() > 0) {
                drawSearchResults(response.getBody());
            } else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });


    }

    private void drawSearchResults(List<FlightDto> response) {
        LOGGER.info("Drawing results");
        searchResultLayout.add(FlightSearch.drawFlightResults(response, true));
    }

    private void drawSearchMenu() {
        //COMPONENTS
        HorizontalLayout searchLayout = new HorizontalLayout();
        DatePicker whenSearchBox;
        whereSearchBox = new TextField("Where you want to go?");
        whenSearchBox = new DatePicker("When?");
        fromSearchBox = new TextField("From where?");
        searchButton = new Button("SEARCH");
        historyButton = new Button("SEARCH HISTORY");
        //CSS
        searchButton.getStyle().set("margin-top", "37px");
        historyButton.getStyle().set("margin-top", "37px");
        searchLayout.getStyle().set("margin", "auto");

        searchLayout.add(fromSearchBox, whereSearchBox, whenSearchBox, searchButton, historyButton);
        add(searchLayout);

        whenSearchBox.addValueChangeListener(event -> whenDate = event.getValue());
    }

    private void drawFlightFilters() {
        //COMPONENTS
        filterNavi = new Div();
        HorizontalLayout filterLayout = new HorizontalLayout();
        carrierClass = new Select<>();
        carrierClass.setLabel("Carrier Class: ");
        carrierClass.setItems("economic", "business", "first");
        carrierClass.setValue("economic");
        priceMoreThan = new TextField("Price (more than): ");
        priceMoreThan.setValue("0");
        priceLessThan = new TextField("Price (less than): ");
        priceLessThan.setValue("20000");
        //CSS
        filterNavi.setVisible(false);
        filterButton.getStyle().set("margin-top", "37px");
        filterNavi.getStyle().set("margin", "auto");

        filterNavi.add(filterLayout);
        filterLayout.add(carrierClass, priceMoreThan, priceLessThan, filterButton);
        add(filterNavi);
    }
}
