package com.kodilla.frontend.view;

import com.kodilla.frontend.view.account.AccountView;
import com.kodilla.frontend.view.account.SignUpView;
import com.kodilla.frontend.view.flightPage.FlightView;
import com.kodilla.frontend.view.holidayPage.MainView;
import com.kodilla.frontend.view.hotelPage.HotelView;
import com.kodilla.frontend.view.readmePage.ReadMeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


public class NavigateBar extends HorizontalLayout{

    @Autowired
    RestTemplate restTemplate;

    private Button signIn;
    private Button signUp;
    private Button account;
    private LoginOverlay loginOverlay;

    public HorizontalLayout drawImage(){
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("https://i.ibb.co/XZ8KLch/Untitled.png", "logo");
        Image leftPalm = new Image("https://i.ibb.co/gtsdn3p/palm-left.png", "left-lalm");
        Image rightPalm = new Image("https://i.ibb.co/28mQZZR/palm-right.png", "right-palm");
        logoLayout.getStyle().set("margin", "auto");
        logoLayout.add(leftPalm, logo, rightPalm);

        return logoLayout;
    }

    public HorizontalLayout drawNavigateBar() {
        //COMPONENTS
        HorizontalLayout menu = new HorizontalLayout();
        menu.setWidthFull();
        Button menuButton1 = new Button("HOLIDAY");
        Button menuButton2 = new Button("HOTELS");
        Button menuButton3 = new Button("FLIGHTS");
        Button menuButton4 = new Button(">>>APP INFO<<<");
        signIn = new Button("SIGN IN");
        signUp = new Button("SIGN UP");
        account = new Button("ACCOUNT");

        //CSS
        menuButton1.getStyle().set("background", "none");
        menuButton2.getStyle().set("background", "none");
        menuButton3.getStyle().set("background", "none");
        menuButton4.getStyle().set("background", "none");
        signIn.getStyle().set("background", "none");
        signUp.getStyle().set("background", "none");
        account.getStyle().set("background", "none");
        signIn.getStyle().set("margin-left", "auto");
        menu.getStyle().set("background", "#e8ebef");
        menu.getStyle().set("margin", "auto");


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

        signIn.addClickListener(e -> {
            loginOverlay.setOpened(true);
        });

        signUp.addClickListener(e -> {
            UI.getCurrent().navigate(SignUpView.class);
        });

        account.addClickListener(e -> {
            UI.getCurrent().navigate(AccountView.class);
        });

        menu.add(menuButton1, menuButton2, menuButton3, menuButton4, signIn, signUp, account);
        drawLoginOverlay();
        return menu;
    }

    private void drawLoginOverlay(){
        loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Travel App");
        loginOverlay.setDescription("");
        loginOverlay.addLoginListener(e ->{
            boolean isAuthenticated = authenticate(e);
            if (isAuthenticated) {
                loginOverlay.setOpened(false);
            } else {
                loginOverlay.setError(true);
            }
        });
    }

    public boolean authenticate(AbstractLogin.LoginEvent loginEvent){
        return true; //HERE CHANGE
    }
}
