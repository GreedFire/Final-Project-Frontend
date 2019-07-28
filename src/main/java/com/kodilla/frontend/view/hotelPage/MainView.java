package com.kodilla.frontend.view.hotelPage;

import com.kodilla.frontend.NotificationScheduler;
import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.hotel.HotelFiltersDto;
import com.kodilla.frontend.domain.dto.hotel.HotelDto;
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
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

    private TextField whereSearchBox;

    private Select<Integer> roomSearchBox;
    private Select<Integer> adultSearchBox;
    private LocalDate whenDate = LocalDate.now().plusDays(10);
    private LocalDate untilDate = LocalDate.now().plusDays(20);
    private VerticalLayout searchResultLayout = new VerticalLayout();
    private Button searchButton;
    private Button historyButton;
    private Div filterNavi;
    private Select<Integer> rating;
    private Select<Integer> stars;
    private TextField priceMoreThan;
    private TextField priceLessThan;
    private Button filterButton = new Button("Filter");
    private String SEARCHID;

    public MainView() {
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSearchMenu();
        drawHotelFilters();
        add(searchResultLayout);
        Notification.show("The most searched location is: " + NotificationScheduler.mostSearchedLocation);

        searchButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Searching hotels");
            URI preparedUrlForHotelSearch = UrlGenerator.hotelsSearchURL(roomSearchBox.getValue(),
                    whereSearchBox.getValue(), whenDate, untilDate, adultSearchBox.getValue());
            List<HotelDto> response = restTemplate.exchange(
                    preparedUrlForHotelSearch, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelDto>>() {
                    }).getBody();

            if (response != null && !response.isEmpty()){
                SEARCHID = response.get(0).getHotelResponseId();
                filterNavi.setVisible(true);
                drawSearchResults(response);
            } else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });

        historyButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Searching hotels in history");
            List<HotelDto> response = restTemplate.exchange(
                    UrlGenerator.HOTEL_HISTORY_URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelDto>>() {
                    }).getBody();
            if (response != null && !response.isEmpty())
                drawSearchResults(response);
            else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });

        filterButton.addClickListener(e -> {
            searchResultLayout.removeAll();

            LOGGER.info("Saving filters");
            HotelFiltersDto filters = new HotelFiltersDto(rating.getValue(), stars.getValue(), new BigDecimal(priceMoreThan.getValue()), new BigDecimal(priceLessThan.getValue()));
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<HotelFiltersDto> request = new HttpEntity<>(filters, headers);
            restTemplate.postForObject(UrlGenerator.SAVE_HOTEL_FILTERS, request, Void.class);

            LOGGER.info("Filtering hotels");
            URI preparedUrlForFilteredHotels = UrlGenerator.filterHotelsURL(SEARCHID, rating.getValue(),
                    stars.getValue(), Integer.parseInt(priceMoreThan.getValue()),
                    Integer.parseInt(priceLessThan.getValue()));

            List<HotelDto> response = restTemplate.exchange(
                    preparedUrlForFilteredHotels, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelDto>>() {
                    }).getBody();
            if (response != null && !response.isEmpty())
                drawSearchResults(response);
            else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });
    }

    private void drawSearchResults(List<HotelDto> response) {
        if (response != null) {
            LOGGER.info("Drawing results");
            searchResultLayout.add(HotelSearch.drawHotelResults(response, true));
        } else {
            Notification.show("SOMETHING WENT WRONG! TRY AGAIN!", 4000, Notification.Position.MIDDLE);
        }
    }


    private void drawHotelFilters() {
        //COMPONENTS
        filterNavi = new Div();
        filterNavi.setVisible(false);
        HorizontalLayout filterLayout = new HorizontalLayout();
        rating = new Select<>();
        rating.setLabel("Rating(at least): ");
        rating.setItems(60, 65, 70, 75, 80, 85, 90, 95, 100);
        rating.setValue(80);
        stars = new Select<>();
        stars.setLabel("Stars(at least): ");
        stars.setItems(1, 2, 3, 4, 5);
        stars.setValue(4);
        priceMoreThan = new TextField("Price (more than): ");
        priceMoreThan.setValue("0");
        priceLessThan = new TextField("Price (less than): ");
        priceLessThan.setValue("20000");
        //CSS
        filterButton.getStyle().set("margin-top", "37px");
        filterNavi.getStyle().set("margin", "auto");
        filterNavi.add(filterLayout);

        filterLayout.add(rating, stars, priceMoreThan, priceLessThan, filterButton);
        add(filterNavi);
    }


    private void drawSearchMenu() {
        //COMPONENTS
        HorizontalLayout searchLayout = new HorizontalLayout();
        whereSearchBox = new TextField("Where you want to go?");
        DatePicker whenSearchBox;
        DatePicker untilSearchBox;
        whenSearchBox = new DatePicker("When?");
        untilSearchBox = new DatePicker("Until when?");
        whenSearchBox.setPlaceholder(whenDate.toString());
        untilSearchBox.setPlaceholder(untilDate.toString());
        roomSearchBox = new Select<>();
        roomSearchBox.setLabel("Rooms");
        roomSearchBox.setItems(1, 2);
        roomSearchBox.setValue(1);
        adultSearchBox = new Select<>();
        adultSearchBox.setLabel("Adults");
        adultSearchBox.setItems(1, 2, 3, 4);
        adultSearchBox.setValue(1);
        searchButton = new Button("SEARCH");
        historyButton = new Button("SEARCH HISTORY");
        //CSS
        roomSearchBox.getStyle().set("width", "70px");
        adultSearchBox.getStyle().set("width", "70px");
        searchButton.getStyle().set("margin-top", "37px");
        historyButton.getStyle().set("margin-top", "37px");
        searchLayout.getStyle().set("margin", "auto");

        searchLayout.add(whereSearchBox, whenSearchBox, untilSearchBox, roomSearchBox, adultSearchBox, searchButton, historyButton);
        add(searchLayout);

        whenSearchBox.addValueChangeListener(event -> whenDate = event.getValue());
        untilSearchBox.addValueChangeListener(event -> untilDate = event.getValue());

    }
}


