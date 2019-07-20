package com.kodilla.frontend.view.account;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.UserAccount;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.hotelPage.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Route
public class SignInView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

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
            Long id = restTemplate.getForObject(UrlGenerator.getUserIdURL(username.getValue(), password.getValue()), Long.class);
            if(id != null) {
                UserAccount.getInstance().setId(id);
                UserAccount.getInstance().signIn();
                UI.getCurrent().navigate(MainView.class);
            }
            else{
                info.setText("Something Went Wrong!");
            }
        });
    }
}
