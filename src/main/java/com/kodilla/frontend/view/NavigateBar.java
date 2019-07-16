package com.kodilla.frontend.view;

import com.kodilla.frontend.view.flightPage.FlightView;
import com.kodilla.frontend.view.holidayPage.MainView;
import com.kodilla.frontend.view.hotelPage.HotelView;
import com.kodilla.frontend.view.readmePage.ReadMeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class NavigateBar {
    public static VerticalLayout drawNavigateBar() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Image title = new Image("https://i.ibb.co/XZ8KLch/Untitled.png", "");
        title.getStyle().set("margin", "auto");

        HorizontalLayout menu = new HorizontalLayout();
        menu.setWidthFull();
        Button menuButton1 = new Button("HOLIDAY");
        Button menuButton2 = new Button("HOTELS");
        Button menuButton3 = new Button("FLIGHTS");
        Button menuButton4 = new Button(">>>APP INFO<<<");

        menuButton1.getStyle().set("background", "none");
        menuButton2.getStyle().set("background", "none");
        menuButton3.getStyle().set("background", "none");
        menuButton4.getStyle().set("background", "none");

        menuButton1.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });

        menuButton2.addClickListener(e -> {
            UI.getCurrent().navigate(HotelView.class);
        });

        menuButton3.addClickListener(e -> {
            UI.getCurrent().navigate(FlightView.class);
        });

        menuButton4.addClickListener(e -> {
            UI.getCurrent().navigate(ReadMeView.class);
        });

        menu.getStyle().set("background", "#e8ebef");
        menu.getStyle().set("margin", "auto");
        menu.add(menuButton1);
        menu.add(menuButton2);
        menu.add(menuButton3);
        menu.add(menuButton4);

        verticalLayout.add(title);
        verticalLayout.add(menu);

        return verticalLayout;
    }
}
