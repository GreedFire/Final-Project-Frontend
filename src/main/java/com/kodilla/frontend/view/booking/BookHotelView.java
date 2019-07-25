package com.kodilla.frontend.view.booking;

import com.kodilla.frontend.domain.dto.hotel.HotelListDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Route
public class BookHotelView extends VerticalLayout {
    private static HotelListDto hotel;

    public BookHotelView(){
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        Div result = new Div();
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
        HorizontalLayout priceAndBookLayout = new HorizontalLayout(phone, price);
        priceAndBookLayout.getStyle().set("margin-top", "50px");
        hotelFormatting.add(name, new HorizontalLayout(adress), new HorizontalLayout(stars, rating), priceAndBookLayout);
        imageAndInfo.add(hotelImage, hotelFormatting);
        div.add(imageAndInfo);
        result.add(div);
        add(result);
        result.getStyle().set("margin", "auto");


        NumberField cardID = new NumberField("Card number(false): ");
        NumberField cvc = new NumberField("CVC(false): ");
        cardID.setMinLength(16);
        cardID.setMaxLength(16);
        cvc.setMinLength(3);
        cvc.setMaxLength(3);
        Button pay = new Button("PAY");
        VerticalLayout verticalLayout = new VerticalLayout(cardID, cvc, pay);
        Div div3 = new Div(verticalLayout);
        div3.getStyle().set("margin", "auto");
        add(div3);


        pay.addClickListener(e -> {
            if (cardID.getValue() != null && cvc.getValue() != null) {
                try {
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
                    contentStream.moveTextPositionByAmount(50, 500);
                    contentStream.showText(" Price: " + hotel.getPrice());
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
                    contentStream.moveTextPositionByAmount(50, 600);
                    contentStream.showText("Hotel:  " + hotel.getName());
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
                    contentStream.moveTextPositionByAmount(100, 700);
                    contentStream.showText("EXAMPLE Invoice " + LocalDate.now());
                    contentStream.endText();
                    contentStream.close();
                    document.save("C:/travelApp_Hotel_Invoice.pdf");
                    document.close();

                    File file = new File("C:/travelApp_Hotel_Invoice.pdf");
                    if (file.toString().endsWith(".pdf"))
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
                    else {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                    }

                } catch (IOException exception) {
                    System.out.println("No hotel pdf File: " + exception);
                }
            } else Notification.show("Wrong card or cvc number!", 4000, Notification.Position.MIDDLE);
        });


    }

    public static void setHotel(HotelListDto hotel) {
        BookHotelView.hotel = hotel;
    }
}
