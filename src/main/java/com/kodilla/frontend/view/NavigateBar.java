package com.kodilla.frontend.view;

import com.kodilla.frontend.view.flightPage.FlightView;
import com.kodilla.frontend.view.hotelPage.MainView;
import com.kodilla.frontend.view.readmePage.ReadMeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class NavigateBar {
    public static VerticalLayout drawNavigateBar(){
        VerticalLayout verticalLayout = new VerticalLayout();
        Image title = new Image("https://i.ibb.co/GsCH9Zp/title.png", "");
        title.getStyle().set("margin", "auto");

        HorizontalLayout menu = new HorizontalLayout();
        Button menuButton1 = new Button("Holiday");
        Button menuButton2 = new Button("Hotels");
        Button menuButton3 = new Button("Flights");
        Button menuButton4 = new Button("README");

        menuButton1.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });

        menuButton2.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });

        menuButton3.addClickListener(e -> {
            UI.getCurrent().navigate(FlightView.class);
        });

        menuButton4.addClickListener(e -> {
            UI.getCurrent().navigate(ReadMeView.class);
        });

        menu.getStyle().set("background", "yellow");
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
