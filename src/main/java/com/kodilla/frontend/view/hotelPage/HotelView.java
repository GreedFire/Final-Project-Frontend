package com.kodilla.frontend.view.hotelPage;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.hotel.HotelListDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Route
public class HotelView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private TextField whereSearchBox;
    private DatePicker whenSearchBox;
    private DatePicker untilSearchBox;
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


    public HotelView() {
        drawNavigateBar();
        drawSearchMenu();
        drawHotelFilters();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            URI preparedUrlForHotelSearch = UrlGenerator.hotelsSearchURL(roomSearchBox.getValue(),
                    whereSearchBox.getValue(), whenDate, untilDate, adultSearchBox.getValue());
            List<HotelListDto> response = restTemplate.exchange(
                    preparedUrlForHotelSearch, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();

            if (response != null)
                SEARCHID = response.get(0).getHotelResponseId();
            filterNavi.setVisible(true);
            drawSearchResults(response);
        });

        historyButton.addClickListener(e -> {
            List<HotelListDto> response = restTemplate.exchange(
                    UrlGenerator.HOTELHISTORYURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();
            drawSearchResults(response);
        });

        filterButton.addClickListener(e -> {
            URI preparedUrlForFilteredHotels = UrlGenerator.filterHotelsURL(SEARCHID, rating.getValue(),
                    stars.getValue(), Integer.parseInt(priceMoreThan.getValue()),
                    Integer.parseInt(priceLessThan.getValue()));

            List<HotelListDto> response = restTemplate.exchange(
                    preparedUrlForFilteredHotels, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();
            drawSearchResults(response);
        });
    }

    private void drawSearchResults(List<HotelListDto> response) {
        if (response != null) {
            searchResultLayout.removeAll();
            searchResultLayout.add(HotelSearch.drawHotelResults(response, true));
        } else {
            Notification.show("SOMETHING WENT WRONG! TRY AGAIN!", 3, Notification.Position.MIDDLE);
        }
    }


    private void drawNavigateBar() {
        VerticalLayout menu = NavigateBar.drawNavigateBar();
        add(menu);
    }


    public void drawHotelFilters() {
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
        filterButton.getStyle().set("margin-top", "37px");
        filterLayout.add(rating, stars, priceMoreThan, priceLessThan, filterButton);
        filterNavi.add(filterLayout);
        filterNavi.getStyle().set("margin", "auto");
        add(filterNavi);
    }


    private void drawSearchMenu() {
        HorizontalLayout searchLayout = new HorizontalLayout();
        whereSearchBox = new TextField("Where you want to go?");
        whenSearchBox = new DatePicker("When?");
        untilSearchBox = new DatePicker("Until when?");
        whenSearchBox.setPlaceholder(whenDate.toString());
        untilSearchBox.setPlaceholder(untilDate.toString());
        roomSearchBox = new Select<>();
        roomSearchBox.setLabel("Rooms");
        roomSearchBox.setItems(1, 2);
        roomSearchBox.setValue(1);
        roomSearchBox.getStyle().set("width", "70px");
        adultSearchBox = new Select<>();
        adultSearchBox.setLabel("Adults");
        adultSearchBox.setItems(1, 2, 3, 4);
        adultSearchBox.setValue(1);
        adultSearchBox.getStyle().set("width", "70px");
        searchButton = new Button("SEARCH");
        searchButton.getStyle().set("margin-top", "37px");
        historyButton = new Button("SEARCH HISTORY");
        historyButton.getStyle().set("margin-top", "37px");
        searchLayout.add(whereSearchBox);
        searchLayout.add(whenSearchBox);
        searchLayout.add(untilSearchBox);
        searchLayout.add(roomSearchBox);
        searchLayout.add(adultSearchBox);
        searchLayout.add(searchButton);
        searchLayout.add(historyButton);
        searchLayout.getStyle().set("margin", "auto");

        whenSearchBox.addValueChangeListener(event -> {
            whenDate = event.getValue();
        });

        untilSearchBox.addValueChangeListener(event -> {
            untilDate = event.getValue();
        });
        add(searchLayout);
    }


}