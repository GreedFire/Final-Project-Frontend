package com.kodilla.frontend.view.account;

import com.kodilla.frontend.domain.dto.UserAccount;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.holidayPage.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Route
public class SignInView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    public SignInView(){
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());

        VerticalLayout logInLayout = new VerticalLayout();
        logInLayout.getStyle().set("width", "350px");
        logInLayout.getStyle().set("margin", "auto");
        TextField username = new TextField("Username: ");
        TextField password = new TextField("Password: ");
        Button logIn = new Button("Log In");
        Label info = new Label("Something Went Wrong!");
        info.getStyle().set("color", "red");
        info.setVisible(false);

        logInLayout.add(username, password, logIn, info);
        add(logInLayout);

        logIn.addClickListener(e-> {
            URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/getId")
                    .queryParam("username", username.getValue())
                    .queryParam("password", password.getValue())
                    .build().encode().toUri();
            Long id = restTemplate.getForObject(url, Long.class);
            if(id != null) {
                UserAccount.getInstance().setId(id);
                UserAccount.getInstance().signIn();
                UI.getCurrent().navigate(MainView.class);
            }
            else{
                info.setVisible(true);
            }
        });
    }
}
