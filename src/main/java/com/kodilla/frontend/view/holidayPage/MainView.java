package com.kodilla.frontend.view.holidayPage;

import com.kodilla.frontend.domain.dto.hotel.HotelListDto;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.hotelPage.HotelSearch;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private TextField whereSearchBox;
    private DatePicker whenSearchBox;
    private DatePicker untilSearchBox;
    private TextField fromSearchBox;
    private Select<Integer> roomSearchBox;
    private Select<Integer> adultSearchBox;
    private LocalDate whenDate = LocalDate.now().plusDays(10);
    private LocalDate untilDate = LocalDate.now().plusDays(20);
    private VerticalLayout searchResultLayout = new VerticalLayout();
    private Button searchButton;
    private Button historyButton;

    public MainView() {
        drawNavigateBar();
        drawSearchMenu();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            ResponseEntity<List<HotelListDto>> response = restTemplate.exchange(
                    prepareQueryUrl(), HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    });
            drawSearchResults(response.getBody());
        });

        historyButton.addClickListener(e -> {
            ResponseEntity<List<HotelListDto>> response = restTemplate.exchange(
                    "http://localhost:8080/v1/hotels/history", HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
                    });

            drawSearchResults(response.getBody());

        });

    }

    private void drawSearchResults(List<HotelListDto> response) {
        searchResultLayout.removeAll();
        searchResultLayout.add(HotelSearch.drawHotelResults(response));
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
        fromSearchBox = new TextField("From where?");
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
        searchLayout.add(fromSearchBox);
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


