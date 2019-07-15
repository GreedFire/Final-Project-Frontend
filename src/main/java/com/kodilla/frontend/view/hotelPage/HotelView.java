package com.kodilla.frontend.view.hotelPage;

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
import org.springframework.web.util.UriComponentsBuilder;

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

    private Select<Integer> rating;
    private Select<Integer> stars;
    private TextField priceMoreThan;
    private TextField priceLessThan;
    private Button filterButton = new Button("Filter");;

    private String SEARCHID;

    private List<HotelListDto> response;


    public HotelView() {
        drawNavigateBar();
        drawSearchMenu();
        drawHotelFilters();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            response = restTemplate.exchange(
                    prepareQueryUrl(), HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();

            if (response != null) {
                SEARCHID = response.get(0).getHotelResponseId();
                drawSearchResults(response);
                System.out.println(response.size());
            } else {
                Notification.show("SOMETHING WENT WRONG! TRY AGAIN!", 3, Notification.Position.MIDDLE);
            }
        });

        historyButton.addClickListener(x -> {
            response = restTemplate.exchange(
                    "http://localhost:8080/v1/hotels/history", HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();
            drawSearchResults(response);
        });

        filterButton.addClickListener(f -> {
            response = restTemplate.exchange(
                    prepareUrlForFilteredHotels(), HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    }).getBody();

            System.out.println(response.size());
            drawSearchResults(response);

        });
    }

    //WYSWIETL DANE, POUKLADAJ TO W FRONT ENDZIE, ZRÓB TO SAMO DLA LOTÓW

    private URI prepareUrlForFilteredHotels() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/hotels/filter")
                .queryParam("responseId", SEARCHID)
                .queryParam("rating", rating.getValue())
                .queryParam("stars", stars.getValue())
                .queryParam("priceMore", Integer.parseInt(priceMoreThan.getValue()))
                .queryParam("priceLess",  Integer.parseInt(priceLessThan.getValue()))
                .build().encode().toUri();
    }

    public void drawHotelFilters() {
        Div filterNavi = new Div();
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
        priceMoreThan.setValue("80");
        priceLessThan = new TextField("Price (less than): ");
        priceLessThan.setValue("200");
        filterButton.getStyle().set("margin-top", "35px");
        filterLayout.add(rating, stars, priceMoreThan, priceLessThan, filterButton);
        filterNavi.add(filterLayout);
        filterNavi.getStyle().set("margin", "auto");
        add(filterNavi);
    }



    private void drawSearchResults(List<HotelListDto> response) {
        searchResultLayout.removeAll();
        searchResultLayout.add(HotelSearch.drawHotelResults(response, true));

    }

    private URI prepareQueryUrl() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/hotels")
                .queryParam("rooms", roomSearchBox.getValue())
                .queryParam("location", whereSearchBox.getValue())
                .queryParam("checkin", whenDate)
                .queryParam("checkout", untilDate)
                .queryParam("adults", adultSearchBox.getValue())
                .build().encode().toUri();
    }

    private void drawNavigateBar() {
        VerticalLayout menu = NavigateBar.drawNavigateBar();
        add(menu);
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
        searchButton = new Button("Search");
        searchButton.getStyle().set("margin-top", "35px");
        historyButton = new Button("search history");
        historyButton.getStyle().set("margin-top", "35px");
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