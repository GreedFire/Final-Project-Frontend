package com.kodilla.frontend.view.account;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.UserDto;
import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


@Route
public class SignUpView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpView.class);

    public SignUpView() {
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSignUpForm();
    }

    private void drawSignUpForm(){
        UserDto userDto = new UserDto();
        FormLayout layoutWithBinder = new FormLayout();

        Binder<UserDto> binder = new Binder<>();

        // Create the fields
        TextField username = new TextField();
        username.setLabel("Username: ");
        username.setValueChangeMode(ValueChangeMode.EAGER);
        PasswordField password = new PasswordField();
        password.setLabel("Password: ");
        password.setValueChangeMode(ValueChangeMode.EAGER);
        TextField firstName = new TextField();
        firstName.setLabel("Firstname: ");
        firstName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField lastName = new TextField();
        lastName.setLabel("Lastname: ");
        lastName.setValueChangeMode(ValueChangeMode.EAGER);
        TextField email = new TextField();
        email.setLabel("Email: ");
        email.setValueChangeMode(ValueChangeMode.EAGER);
        DatePicker birthDate = new DatePicker();
        birthDate.setLabel("Birth Date: ");
        Button register = new Button("Register");
        Button reset = new Button("Reset");
        Label info = new Label("");
        HorizontalLayout actions = new HorizontalLayout();

        //CSS
        layoutWithBinder.getStyle().set("width", "350px");
        layoutWithBinder.getStyle().set("margin", "auto");
        username.setMaxWidth("200px");
        password.setMaxWidth("200px");
        firstName.setMaxWidth("200px");
        lastName.setMaxWidth("200px");
        email.setMaxWidth("200px");
        birthDate.setMaxWidth("200px");
        info.getStyle().set("margin", "auto");

        // First name and last name are required fields
        password.setRequiredIndicatorVisible(true);
        username.setRequiredIndicatorVisible(true);
        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.forField(username)
                .withValidator(new StringLengthValidator(
                        "Please add the username", 3, 14))
                .bind(UserDto::getUsername, UserDto::setUsername);
        binder.forField(password)
                .withValidator(new StringLengthValidator(
                        "Please add the username", 3, 14))
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

        actions.add(register, reset);
        layoutWithBinder.add(username, password, email, firstName, lastName, birthDate, actions);
        add(layoutWithBinder, info);

        register.addClickListener(event -> {

            try {
                binder.writeBean(userDto);
            } catch (ValidationException e) {
                System.out.println("Failed to bind data in register tab: " + e);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);
            LOGGER.info("Creating user");
            Boolean authenticate = restTemplate.postForObject(UrlGenerator.CREATE_USER_URL, request, Boolean.class);
            if (authenticate != null && authenticate) {
                info.setText("Succefully registered");
                info.getStyle().set("color", "green");
                layoutWithBinder.setVisible(false);
            } else {
                info.setText("USER EXISTS");
                info.getStyle().set("color", "red");
            }
        });

        reset.addClickListener(event -> {
            binder.readBean(null);
            info.getStyle().set("color", "black");
        });

    }


}
