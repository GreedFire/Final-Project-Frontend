package com.kodilla.frontend.view.account;

import com.google.gson.Gson;
import com.kodilla.frontend.domain.dto.UserDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Route
public class SignUpView extends VerticalLayout {

    @Autowired
    RestTemplate restTemplate;

    public SignUpView() {
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawImage());
        add(navigateBar.drawNavigateBar());

        UserDto userDto = new UserDto();
        FormLayout layoutWithBinder = new FormLayout();
        layoutWithBinder.getStyle().set("width", "350px");
        layoutWithBinder.getStyle().set("margin", "auto");
        Binder<UserDto> binder = new Binder<>();

// Create the fields
        TextField username = new TextField();
        username.setLabel("Username: ");
        username.setMaxWidth("200px");
        username.setValueChangeMode(ValueChangeMode.EAGER);
        PasswordField password = new PasswordField();
        password.setLabel("Password: ");
        password.setMaxWidth("200px");
        password.setValueChangeMode(ValueChangeMode.EAGER);
        TextField firstName = new TextField();
        firstName.setLabel("Firstname: ");
        firstName.setMaxWidth("200px");
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField lastName = new TextField();
        lastName.setLabel("Lastname: ");
        lastName.setMaxWidth("200px");
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField email = new TextField();
        email.setLabel("Email: ");
        email.setMaxWidth("200px");
        email.setValueChangeMode(ValueChangeMode.EAGER);
        DatePicker birthDate = new DatePicker();
        birthDate.setLabel("Birth Date: ");
        birthDate.setMaxWidth("200px");
        Button register = new Button("Register");
        Button reset = new Button("Reset");

        // Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(register, reset);

        layoutWithBinder.add(username, password, email, firstName, lastName, birthDate, actions);

// First name and last name are required fields
        password.setRequiredIndicatorVisible(true);
        username.setRequiredIndicatorVisible(true);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.forField(username)
                .withValidator(new StringLengthValidator(
                        "Please add the username", 2, 14))
                .bind(UserDto::getUsername, UserDto::setUsername);
        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Please add the username", 2, 14))
                .bind(UserDto::getPassword, UserDto::setPassword);
        binder.forField(firstName)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, 30))
                .bind(UserDto::getFirstname, UserDto::setFirstname);
        binder.forField(lastName)
                .withValidator(new StringLengthValidator(
                        "Please add the last name", 1, 30))
                .bind(UserDto::getLastname, UserDto::setLastname);
        binder.forField(email)
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(UserDto::getEmail, UserDto::setEmail);

        binder.bind(birthDate, UserDto::getBirthdate, UserDto::setBirthdate);

        register.addClickListener(event -> {
            try {
                binder.writeBean(userDto);
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("USERDTO: " + userDto);
            URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users")
                    .build().encode().toUri();
            Gson gson = new Gson();
            String json = gson.toJson(userDto, UserDto.class);
            System.out.println("JSON: " + json);
            Boolean authenticate = restTemplate.postForObject(url, json, Boolean.class);
            if (authenticate) {
                System.out.println("Everything ok");
            } else System.out.println("Something is wrong");
        });
        reset.addClickListener(event -> {
            binder.readBean(null);
        });

        add(layoutWithBinder);

        // ask for postObject - 415 ||| maybe i need map it to json and from it to object on backend?
        // ask for putObject, DeleteObject
        // ask for carriers - flight stackoverflow
        // strategy design pattern

    }
}
