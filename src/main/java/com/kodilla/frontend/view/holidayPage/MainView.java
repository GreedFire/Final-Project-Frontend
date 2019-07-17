package com.kodilla.frontend.view.holidayPage;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.HolidayDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;

@Route
public class MainView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private NavigateBar navigateBar = new NavigateBar();

    private TextField fromSearchBox;
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

    public MainView() {
        add(navigateBar.drawImage());
        add(navigateBar.drawNavigateBar());
        drawSearchMenu();
        add(searchResultLayout);

        searchButton.addClickListener(e -> {
            URI url = UrlGenerator.holidaySearchURL(roomSearchBox.getValue(), fromSearchBox.getValue(),
                    whereSearchBox.getValue(), whenDate, untilDate, adultSearchBox.getValue());
            final HolidayDto response = restTemplate.getForObject(url, HolidayDto.class);
            drawSearchResults(response);

        });

        historyButton.addClickListener(e -> {
//            ResponseEntity<List<HotelListDto>> response = restTemplate.exchange(
//                    "http://localhost:8080/v1/hotels/history", HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelListDto>>() {
//                    });
//
//            drawSearchResults(response.getBody());
        });

    }

    private void drawSearchResults(HolidayDto response) {
        searchResultLayout.removeAll();
        searchResultLayout.add(HolidaySearch.drawHolidayResults(response));
    }

    private void drawSearchMenu() {
        //COMPONENTS
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

        searchLayout.add(fromSearchBox, whereSearchBox, whenSearchBox, untilSearchBox, roomSearchBox, adultSearchBox,
                searchButton, historyButton);
        add(searchLayout);

        whenSearchBox.addValueChangeListener(event -> {
            whenDate = event.getValue();
        });

        untilSearchBox.addValueChangeListener(event -> {
            untilDate = event.getValue();
        });
    }
}


