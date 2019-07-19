package com.kodilla.frontend.view.flightPage;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.flight.FlightDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
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

    private Div filterNavi;
    private Select<String> carrierClass;
    private TextField priceMoreThan;
    private TextField priceLessThan;
    private Button filterButton = new Button("Filter");

    private long SEARCHID;

    public FlightView() {
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSearchMenu();
        drawFlightFilters();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            URI url = UrlGenerator.flightsSearchURL(fromSearchBox.getValue(), whereSearchBox.getValue(), whenDate);
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });
            if (response.getBody() != null && response.getBody().size() > 0) {
                SEARCHID = response.getBody().get(0).getId();
            }
            filterNavi.setVisible(true);
            drawSearchResults(response.getBody());
        });

        historyButton.addClickListener(e -> {
           ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                   UrlGenerator.FLIGHTHISTORYURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });

           //for now just first element because i need to change all request to List and change method param to List or
           // write new method with list param
            drawSearchResults(response.getBody());


        });

        filterButton.addClickListener(e -> {
            URI url = UrlGenerator.filterFlightsURL(SEARCHID, carrierClass.getValue(),
                    Integer.parseInt(priceMoreThan.getValue()), Integer.parseInt(priceLessThan.getValue()));
            ResponseEntity<List<FlightDto>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<FlightDto>>() {
                    });

            drawSearchResults(response.getBody());
        });


    }

    private void drawSearchResults(List<FlightDto> response) {
        searchResultLayout.removeAll();
        searchResultLayout.add(FlightSearch.drawFlightResults(response, true));
    }

    private void drawSearchMenu() {
        //COMPONENTS
        HorizontalLayout searchLayout = new HorizontalLayout();
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

        whenSearchBox.addValueChangeListener(event -> {
            whenDate = event.getValue();
        });
    }

    public void drawFlightFilters() {
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
