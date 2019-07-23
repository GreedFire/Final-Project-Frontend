package com.kodilla.frontend.view.account;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.hotelPage.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Route
public class SignInView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInView.class);

    public SignInView(){
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSignInForm();
    }

    private void drawSignInForm(){
        VerticalLayout logInLayout = new VerticalLayout();
        logInLayout.getStyle().set("width", "350px");
        logInLayout.getStyle().set("margin", "auto");
        TextField username = new TextField("Username: ");
        TextField password = new TextField("Password: ");
        Button logIn = new Button("Log In");
        Label info = new Label();
        info.getStyle().set("color", "red");

        password.setRequiredIndicatorVisible(true);
        username.setRequiredIndicatorVisible(true);
        password.setMinLength(3);
        username.setMinLength(3);

        logInLayout.add(username, password, logIn, info);
        add(logInLayout);

        logIn.addClickListener(e-> {
            LOGGER.info("Trying to log in user");
            Long id = restTemplate.getForObject(UrlGenerator.getUserIdURL(username.getValue(), password.getValue()), Long.class);
            if(id != null) {
                System.out.println("UI ID: " + id);
                UI.getCurrent().setId(Long.toString(id));
                RestTemplate restTemplate = new RestTemplate();
                if(UI.getCurrent().getId().isPresent()){
                    long id2 = Long.parseLong(UI.getCurrent().getId().get());
                            restTemplate.put(UrlGenerator.userSignInURL(id2) ,null);
                }

                UI.getCurrent().navigate(MainView.class);
                LOGGER.info("Logged in user with id: " + id);
            }
            else{
                info.setText("Something Went Wrong!");
            }
        });
    }
}
