package com.kodilla.frontend.view;

import com.kodilla.frontend.domain.dto.UserAccount;
import com.kodilla.frontend.view.account.AccountView;
import com.kodilla.frontend.view.account.SignInView;
import com.kodilla.frontend.view.account.SignUpView;
import com.kodilla.frontend.view.flightPage.FlightView;
import com.kodilla.frontend.view.holidayPage.MainView;
import com.kodilla.frontend.view.hotelPage.HotelView;
import com.kodilla.frontend.view.readmePage.ReadMeView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@VaadinSessionScope
public class NavigateBar extends HorizontalLayout{

    private static Button signIn = new Button("SIGN IN");
    private static Button signUp = new Button("SIGN UP");
    private static Button account = new Button("ACCOUNT");
    private static Button signOut = new Button("SIGN OUT");


    public static HorizontalLayout drawImage(){
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("https://i.ibb.co/XZ8KLch/Untitled.png", "logo");
        Image leftPalm = new Image("https://i.ibb.co/gtsdn3p/palm-left.png", "left-palm");
        Image rightPalm = new Image("https://i.ibb.co/28mQZZR/palm-right.png", "right-palm");
        logoLayout.getStyle().set("margin", "auto");
        logoLayout.add(leftPalm, logo, rightPalm);

        return logoLayout;
    }

    public static HorizontalLayout drawNavigateBar() {
        //COMPONENTS
        HorizontalLayout menu = new HorizontalLayout();
        menu.setWidthFull();
        Button menuButton1 = new Button("HOLIDAY");
        Button menuButton2 = new Button("HOTELS");
        Button menuButton3 = new Button("FLIGHTS");
        Button menuButton4 = new Button(">>>APP INFO<<<");

        //CSS
        menuButton1.getStyle().set("background", "none");
        menuButton2.getStyle().set("background", "none");
        menuButton3.getStyle().set("background", "none");
        menuButton4.getStyle().set("background", "none");
        signIn.getStyle().set("background", "none");
        signUp.getStyle().set("background", "none");
        account.getStyle().set("background", "none");
        signOut.getStyle().set("background", "none");
        menuButton4.getStyle().set("margin-right", "auto");
        menu.getStyle().set("background", "#e8ebef");
        menu.getStyle().set("margin", "auto");
        hideSignOutButton();
        hideAccountButton();


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

        menu.add(menuButton1, menuButton2, menuButton3, menuButton4, signOut ,signIn, signUp, account);
        return menu;
    }

    public static void hideSignOutButton(){
        signOut.setVisible(false);
    }

    public static void showSignOutButton(){
        signOut.setVisible(true);
    }

    public static void hideSignInButton(){
        signIn.setVisible(false);
    }

    public static void showSignInButton(){
        signIn.setVisible(true);
    }

    public static void hideAccountButton(){
        account.setVisible(false);
    }

    public static void showAccountButton(){
        account.setVisible(true);
    }
}
