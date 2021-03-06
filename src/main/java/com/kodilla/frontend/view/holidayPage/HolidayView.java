package com.kodilla.frontend.view.holidayPage;

import com.kodilla.frontend.NotificationScheduler;
import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.HolidayDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;

@Route
public class HolidayView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayView.class);
    @Autowired
    private RestTemplate restTemplate;
    private TextField fromSearchBox;
    private TextField whereSearchBox;
    private Select<Integer> roomSearchBox;
    private Select<Integer> adultSearchBox;
    private LocalDate whenDate = LocalDate.now().plusDays(10);
    private LocalDate untilDate = LocalDate.now().plusDays(20);
    private VerticalLayout searchResultLayout = new VerticalLayout();
    private Button searchButton;

    public HolidayView() {
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSearchMenu();
        Label notWorking = new Label("THIS SUBPAGE IS NOT WORKING PROPERLY YET. PLEASE GO TO HOTEL OR FLIGHT");
        notWorking.getStyle().set("fonr-size", "40");
        notWorking.getStyle().set("font-weight", "bold");
        notWorking.getStyle().set("color", "red");
        notWorking.getStyle().set("margin", "auto");
        add(notWorking);
        add(searchResultLayout);
        Notification.show("The most searched location is: " + NotificationScheduler.mostSearchedLocation);

        searchButton.addClickListener(e -> {
            searchResultLayout.removeAll();
            LOGGER.info("Searching for holidays");
            URI url = UrlGenerator.holidaySearchURL(roomSearchBox.getValue(), fromSearchBox.getValue(),
                    whereSearchBox.getValue(), whenDate, untilDate, adultSearchBox.getValue());
            final HolidayDto response = restTemplate.getForObject(url, HolidayDto.class);
            if (response != null) {
                drawSearchResults(response);
            } else Notification.show("NO RESULTS", 4000, Notification.Position.MIDDLE);
        });
    }

    private void drawSearchResults(HolidayDto response) {
        LOGGER.info("Drawing results");
        searchResultLayout.add(HolidaySearch.drawHolidayResults(response));
    }

    private void drawSearchMenu() {
        //COMPONENTS
        HorizontalLayout searchLayout = new HorizontalLayout();
        adultSearchBox = new Select<>();
        adultSearchBox.setLabel("Adults");
        adultSearchBox.setItems(1, 2, 3, 4);
        adultSearchBox.setValue(1);
        searchButton = new Button("SEARCH");
        fromSearchBox = new TextField("From where?");
        roomSearchBox = new Select<>();
        roomSearchBox.setLabel("Rooms");
        roomSearchBox.setItems(1, 2);
        roomSearchBox.setValue(1);
        whereSearchBox = new TextField("Where you want to go?");
        DatePicker whenSearchBox;
        DatePicker untilSearchBox;
        whenSearchBox = new DatePicker("When?");
        untilSearchBox = new DatePicker("Until when?");
        whenSearchBox.setPlaceholder(whenDate.toString());
        untilSearchBox.setPlaceholder(untilDate.toString());
        //CSS
        searchButton.getStyle().set("margin-top", "37px");
        searchLayout.getStyle().set("margin", "auto");
        roomSearchBox.getStyle().set("width", "70px");
        adultSearchBox.getStyle().set("width", "70px");

        searchLayout.add(fromSearchBox, whereSearchBox, whenSearchBox, untilSearchBox, roomSearchBox, adultSearchBox,
                searchButton);
        add(searchLayout);

        whenSearchBox.addValueChangeListener(event -> whenDate = event.getValue());
        untilSearchBox.addValueChangeListener(event -> untilDate = event.getValue());
    }


}