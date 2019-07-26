package com.kodilla.frontend.view.account;

import com.kodilla.frontend.UrlGenerator;
import com.kodilla.frontend.domain.dto.DeviceDto;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.hotelPage.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@Route
public class SignInView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignInView.class);
    @Autowired
    private RestTemplate restTemplate;

    public SignInView() {
        add(NavigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawSignInForm();
    }

    private void drawSignInForm() {
        VerticalLayout logInLayout = new VerticalLayout();
        logInLayout.getStyle().set("width", "350px");
        logInLayout.getStyle().set("margin", "auto");
        TextField username = new TextField("Username: ");
        PasswordField password = new PasswordField("Password: ");
        Button logIn = new Button("Log In");
        Label info = new Label();
        info.getStyle().set("color", "red");

        password.setRequiredIndicatorVisible(true);
        username.setRequiredIndicatorVisible(true);
        password.setMinLength(3);
        username.setMinLength(3);

        logInLayout.add(username, password, logIn, info);
        add(logInLayout);

        logIn.addClickListener(e -> {
            LOGGER.info("Trying to log in user");
            Long id = restTemplate.getForObject(UrlGenerator.getUserIdURL(username.getValue(), password.getValue()), Long.class);
            if (id != null) {
                UI.getCurrent().setId(Long.toString(id));
                if (UI.getCurrent().getId().isPresent()) {
                    long id2 = Long.parseLong(UI.getCurrent().getId().get());
                    restTemplate.put(UrlGenerator.userSignInURL(id2), null);
                    checkAndSaveUserDevice();
                }

                UI.getCurrent().navigate(MainView.class);
                LOGGER.info("Logged in user with id: " + id);
            } else {
                info.setText("Something Went Wrong!");
            }
        });
    }

    private void checkAndSaveUserDevice() {
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));
            String systemipaddress = sc.readLine().trim();
            InetAddress inetAddress = InetAddress.getLocalHost();
            DeviceDto deviceDto = new DeviceDto(
                    systemipaddress,
                    inetAddress.getHostName(),
                    System.getProperty("os.name")
            );
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<DeviceDto> request = new HttpEntity<>(deviceDto, headers);
            restTemplate.postForObject(UrlGenerator.SAVE_DEVICE, request, Void.class);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host exception: " + e);
        } catch (IOException e) {
            System.out.println("IOException after reading IP Adress: " + e);
        }
    }
}
