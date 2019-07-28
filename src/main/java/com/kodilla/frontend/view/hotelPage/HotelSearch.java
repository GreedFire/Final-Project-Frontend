package com.kodilla.frontend.view.hotelPage;

import com.kodilla.frontend.domain.dto.hotel.HotelDto;
import com.kodilla.frontend.view.account.SignInView;
import com.kodilla.frontend.view.booking.BookHotelView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class HotelSearch {

    public static Div drawHotelResults(List<HotelDto> response, boolean marginAuto) {
        Div result = new Div();
        if(marginAuto)
             result.getStyle().set("margin", "auto");
        for (HotelDto hotel : response) {

            Div div = new Div();
            div.getStyle().set("margin-bottom", "5px");
            div.getStyle().set("margin", "auto");
            div.getStyle().set("width", "600px");
            div.getStyle().set("border-style", "solid");
            div.getStyle().set("border-width", "2px");
            div.getStyle().set("border-width", "2px");
            Image hotelImage = new Image("https://www.kayak.pl" + hotel.getThumburl(), "");
            Label name = new Label(hotel.getName());
            name.getStyle().set("font-size", "20px");
            name.getStyle().set("font-weight", "bold");
            name.getStyle().set("padding-right", "2px");
            Label adress = new Label(hotel.getDisplayaddress());
            adress.getStyle().set("margin-top", "-20px");
            String starsSum = "";
            StringBuilder stringBuilder = new StringBuilder(starsSum);
            for (int i = 0; i < hotel.getStars(); i++) {
                stringBuilder.append("★");
                starsSum = stringBuilder.toString();
            }
            Label stars = new Label(starsSum);
            stars.getStyle().set("padding-right", "2px");
            stars.getStyle().set("margin-top", "-20px");
            Label rating = new Label("User ratings: " + hotel.getUserRating());
            rating.getStyle().set("margin-top", "-20px");
            rating.getStyle().set("font-size", "15px");
            rating.getStyle().set("padding-right", "2px");
            Label phone = new Label("Phone: " + hotel.getPhone());
            phone.getStyle().set("padding-right", "2px");
            phone.getStyle().set("margin-top", "20px");
            Label price = new Label((int) Double.parseDouble(hotel.getPrice()) + "$");
            price.getStyle().set("padding-right", "2px");
            price.getStyle().set("font-size", "30px");
            price.getStyle().set("font-weight", "bold");
            HorizontalLayout imageAndInfo = new HorizontalLayout();
            VerticalLayout hotelFormatting = new VerticalLayout();
            Button bookButton = new Button("BOOK");
            HorizontalLayout priceAndBookLayout = new HorizontalLayout(phone, price, bookButton);
            priceAndBookLayout.getStyle().set("margin-top", "50px");
            hotelFormatting.add(name, new HorizontalLayout(adress), new HorizontalLayout(stars, rating), priceAndBookLayout);
            imageAndInfo.add(hotelImage, hotelFormatting);
            div.add(imageAndInfo);
            result.add(div);

            bookButton.addClickListener(e -> {
                if(UI.getCurrent().getId().isPresent()) {
                    long userId = Long.parseLong(UI.getCurrent().getId().get());
                    if (userId != 0) {
                        BookHotelView.setHotel(hotel);
                        UI.getCurrent().navigate(BookHotelView.class);
                    } else {
                        UI.getCurrent().navigate(SignInView.class);
                    }
                }
                else UI.getCurrent().navigate(SignInView.class);
            });
        }

        return result;
    }

}
