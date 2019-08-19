package com.kodilla.frontend.view.booking;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.invoice.FlightInvoiceDto;
import com.kodilla.frontend.domain.dto.flight.FlightCarriersDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Route
public class BookFlightView extends VerticalLayout {
    @Autowired
    private RestTemplate restTemplate;

    private static FlightCarriersDto carrier;

    public BookFlightView() {
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Div carrierDiv = new Div();
        carrierDiv.getStyle().set("margin-bottom", "5px");
        carrierDiv.getStyle().set("margin", "auto");
        carrierDiv.getStyle().set("width", "400px");
        carrierDiv.getStyle().set("border-style", "solid");
        carrierDiv.getStyle().set("border-width", "2px");
        carrierDiv.getStyle().set("border-width", "2px");

        Label carrierId = new Label("Carrier ID: " + carrier.getCarrierId());
        Label carrierName = new Label("Carrier name: " + carrier.getCarrierName());
        Label carrierClass = new Label("Class: " + carrier.getCarrierClass());
        Label carrierDate = new Label("Departure: " + carrier.getDepartureDate().format(formatter));
        Label carrierPrice = new Label(carrier.getPrice().intValueExact() + "$");
        carrierPrice.getStyle().set("font-size", "30px");
        carrierPrice.getStyle().set("font-weight", "bold");
        carrierDate.getStyle().set("font-size", "20px");
        carrierDate.getStyle().set("font-weight", "bold");
        carrierClass.getStyle().set("font-weight", "bold");
        carrierName.getStyle().set("font-weight", "bold");
        carrierId.getStyle().set("font-weight", "bold");
        HorizontalLayout idAndNameLayout = new HorizontalLayout(carrierId, carrierName);
        HorizontalLayout priceAndBook = new HorizontalLayout(carrierPrice);
        VerticalLayout carrierLayout = new VerticalLayout(idAndNameLayout, carrierClass, carrierDate, priceAndBook);
        carrierDiv.add(carrierLayout);
        add(carrierDiv);

        NumberField cardID = new NumberField("Card number(false): ");
        NumberField cvc = new NumberField("CVC(false): ");
        cardID.setMinLength(16);
        cardID.setMaxLength(16);
        cvc.setMinLength(3);
        cvc.setMaxLength(3);
        Button pay = new Button("PAY");
        VerticalLayout verticalLayout = new VerticalLayout(cardID, cvc, pay);
        Div div = new Div(verticalLayout);
        div.getStyle().set("margin", "auto");
        add(div);


        pay.addClickListener(e -> {
            if (cardID.getValue() != null && cvc.getValue() != null) {
                FlightInvoiceDto invoice = new FlightInvoiceDto(
                        LocalDate.now(),
                        carrier.getPrice(),
                        carrier.getId(),
                        Long.parseLong(UI.getCurrent().getId().get())
                );

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<FlightInvoiceDto> request = new HttpEntity<>(invoice, headers);
                restTemplate.postForObject(UrlGenerator.SAVE_FLIGHT_INVOICE, request, Void.class);

                try {
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage();
                    document.addPage(page);

                    PDPageContentStream contentStream = new PDPageContentStream(document, page);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
                    contentStream.moveTextPositionByAmount(50, 500);
                    contentStream.showText("Departure Date:  " + carrier.getDepartureDate() + " Price: " + carrier.getPrice());
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
                    contentStream.moveTextPositionByAmount(50, 600);
                    contentStream.showText("Carrier:  " + carrier.getCarrierName() + " class: " + carrier.getCarrierClass());
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 30);
                    contentStream.moveTextPositionByAmount(100, 700);
                    contentStream.showText("EXAMPLE Invoice " + LocalDate.now());
                    contentStream.endText();

                    contentStream.close();

                    document.save("D:/travelApp_flight_Invoice.pdf");
                    document.close();

                    File file = new File("D:/travelApp_flight_Invoice.pdf");
                    if (file.toString().endsWith(".pdf"))
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
                    else {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                    }

                } catch (IOException exception) {
                    System.out.println("No flight pdf file: " + exception);
                }
            } else Notification.show("Wrong card or cvc number!", 4000, Notification.Position.MIDDLE);
        });
    }

    public static void setCarrier(FlightCarriersDto carriera) {
        carrier = carriera;
    }
}
