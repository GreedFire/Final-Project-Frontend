package com.kodilla.frontend.view.hotelPage;

import com.kodilla.frontend.domain.dto.hotel.HotelSetDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

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
    private Button searchButton;
    private LocalDate whenDate = LocalDate.now().plusDays(10);
    private LocalDate untilDate = LocalDate.now().plusDays(20);
    private VerticalLayout searchResultLayout = new VerticalLayout();

    public MainView(){
        drawNavigateBar();
        drawSearchMenu();

        searchButton.addClickListener(e -> {
            final HotelSetDto[] response = restTemplate.getForObject(prepareQueryUrl(), HotelSetDto[].class);
            drawSearchResults(response);
        });


    }

    private void drawSearchResults(HotelSetDto[] response){
        searchResultLayout.removeAll();
        searchResultLayout = HotelSearch.drawHotelResults(response);
        add(searchResultLayout);
    }

    private URI prepareQueryUrl(){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/hotels")
                .queryParam("rooms", roomSearchBox.getValue())
                .queryParam("location", whereSearchBox.getValue())
                .queryParam("checkin", whenDate)
                .queryParam("checkout", untilDate)
                .queryParam("adults", adultSearchBox.getValue())
                .build().encode().toUri();
    }



    private void drawNavigateBar(){
        VerticalLayout menu = NavigateBar.drawNavigateBar();
        add(menu);
    }

    private void drawSearchMenu(){
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
        searchLayout.add(whereSearchBox);
        searchLayout.add(whenSearchBox);
        searchLayout.add(untilSearchBox);
        searchLayout.add(fromSearchBox);
        searchLayout.add(roomSearchBox);
        searchLayout.add(adultSearchBox);
        searchLayout.add(searchButton);
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


