package com.kodilla.frontend.view;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.UserAccount;
import com.kodilla.frontend.view.account.AccountView;
import com.kodilla.frontend.view.account.SignInView;
import com.kodilla.frontend.view.account.SignUpView;
import com.kodilla.frontend.view.flightPage.FlightView;
import com.kodilla.frontend.view.holidayPage.HolidayView;
import com.kodilla.frontend.view.hotelPage.MainView;
import com.kodilla.frontend.view.readmePage.ReadMeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class NavigateBar extends VerticalLayout {

    private Button signIn = new Button("SIGN IN");
    private Button signUp = new Button("SIGN UP");
    private Button account = new Button("ACCOUNT");
    private Button signOut = new Button("SIGN OUT");

    public static HorizontalLayout drawImage() {
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image(UrlGenerator.LOGO, "logo");
        Image leftPalm = new Image(UrlGenerator.LEFT_PALM_LOGO, "left-palm");
        Image rightPalm = new Image(UrlGenerator.RIGHT_PALM_LOGO, "right-palm");
        logoLayout.getStyle().set("margin", "auto");
        logoLayout.add(leftPalm, logo, rightPalm);

        return logoLayout;
    }

    public static HorizontalLayout drawNavigateBar() {
        //COMPONENTS
        HorizontalLayout menu = new HorizontalLayout();
        menu.setWidthFull();
        Button menuButton1 = new Button("HOTELS");
        Button menuButton2 = new Button("FLIGHTS");
        Button menuButton3 = new Button("HOLIDAY");
        Button menuButton4 = new Button(">>>APP INFO<<<");

        //CSS
        menuButton1.getStyle().set("background", "none");
        menuButton2.getStyle().set("background", "none");
        menuButton3.getStyle().set("background", "none");
        menuButton4.getStyle().set("background", "none");

        menuButton4.getStyle().set("margin-right", "auto");
        menu.getStyle().set("background", "#e8ebef");
        menu.getStyle().set("margin", "auto");


        menuButton1.addClickListener(e -> {
            UI.getCurrent().navigate(MainView.class);
        });

        menuButton2.addClickListener(e -> {
            UI.getCurrent().navigate(FlightView.class);
        });

        menuButton3.addClickListener(e -> {
            UI.getCurrent().navigate(HolidayView.class);
        });

        menuButton4.addClickListener(e -> {
            UI.getCurrent().navigate(ReadMeView.class);

        });

        menu.add(menuButton1, menuButton2, menuButton3, menuButton4);
        return menu;
    }

    public HorizontalLayout drawAccountNavigateBar() {
        signIn.getStyle().set("background", "none");
        signUp.getStyle().set("background", "none");
        account.getStyle().set("background", "none");
        signOut.getStyle().set("background", "none");


        signIn.addClickListener(e -> {
            UI.getCurrent().navigate(SignInView.class);
        });

        signUp.addClickListener(e -> {
            UI.getCurrent().navigate(SignUpView.class);
        });

        account.addClickListener(e -> {
            UI.getCurrent().navigate(AccountView.class);
        });

        signOut.addClickListener(e -> {
            UserAccount.getInstance().signOut();
        });

        return new HorizontalLayout(signOut, signIn, signUp, account);
    }

    public Button getSignIn() {
        return signIn;
    }

    public Button getSignUp() {
        return signUp;
    }

    public Button getAccount() {
        return account;
    }

    public Button getSignOut() {
        return signOut;
    }
}
