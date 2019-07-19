package com.kodilla.frontend.view.account;

import com.kodilla.frontend.domain.dto.UserAccount;
import com.kodilla.frontend.view.NavigateBar;
import com.kodilla.frontend.view.holidayPage.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Route
public class AccountView extends VerticalLayout {

    @Autowired
    private RestTemplate restTemplate;

    public AccountView() {
        NavigateBar navigateBar = new NavigateBar();
        add(navigateBar.drawAccountNavigateBar());
        add(NavigateBar.drawImage());
        add(NavigateBar.drawNavigateBar());
        drawPasswordChangeSettings();
        drawDeleteAccountSettings();
    }

    private void drawPasswordChangeSettings() {
        //COMPONENTS
        VerticalLayout passwordLayout = new VerticalLayout();
        Label passwordLabel = new Label("Change password: ");
        PasswordField oldPassword = new PasswordField("Old password: ");
        PasswordField newPassword = new PasswordField("New password: ");
        PasswordField newPasswordRepeat = new PasswordField("Repeat new password: ");
        Button changePasswordButton = new Button("Change password");
        Label changePasswordInfo = new Label();

        //CSS
        passwordLayout.getStyle().set("margin", "auto");
        passwordLabel.getStyle().set("font-size", "30px");
        passwordLabel.getStyle().set("font-weight", "bold");
        changePasswordInfo.setVisible(false);
        changePasswordInfo.getStyle().set("font-size", "20px");
        changePasswordInfo.getStyle().set("font-weight", "bold");

        //ADDING COMPONENTS
        passwordLayout.add(passwordLabel, oldPassword, newPassword, newPasswordRepeat, changePasswordButton, changePasswordInfo);
        add(passwordLayout);

        changePasswordButton.addClickListener(e -> {
            if (UserAccount.getInstance() != null && newPassword.getValue().equals(newPasswordRepeat.getValue())) {
                URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/loggedIn")
                        .queryParam("id", UserAccount.getInstance().getId())
                        .build().encode().toUri();
                Boolean authenticate = restTemplate.getForObject(url, Boolean.class);
                if (authenticate != null && authenticate) {
                    URI url2 = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/passwordChange")
                            .queryParam("id", UserAccount.getInstance().getId())
                            .queryParam("oldPassword", oldPassword.getValue())
                            .queryParam("newPassword", newPassword.getValue())
                            .build().encode().toUri();
                    restTemplate.put(url2, null);
                    changePasswordInfo.getStyle().set("color", "green");
                    changePasswordInfo.setText("Successfully changed password");
                    changePasswordInfo.setVisible(true);
                } else {
                    changePasswordInfo.getStyle().set("color", "red");
                    changePasswordInfo.setText("Something went wrong!");
                    changePasswordInfo.setVisible(true);
                }
            } else {
                changePasswordInfo.getStyle().set("color", "red");
                changePasswordInfo.setText("Something went wrong!");
                changePasswordInfo.setVisible(true);
            }
        });
    }

    private void drawDeleteAccountSettings() {
        //COMPONENTS
        VerticalLayout deleteLayout = new VerticalLayout();
        Label deleteLabel = new Label("Delete Account: ");
        PasswordField deletePassword = new PasswordField("Password: ");
        PasswordField deletePasswordRepeat = new PasswordField("Repeat password: ");
        Button deleteAccountButton = new Button("DELETE ACCOUNT");
        Label deleteUserInfo = new Label();

        //CSS
        deleteLabel.getStyle().set("font-size", "30px");
        deleteLabel.getStyle().set("font-weight", "bold");
        deleteAccountButton.getStyle().set("color", "red");
        deleteUserInfo.setVisible(false);
        deleteUserInfo.getStyle().set("font-size", "20px");
        deleteUserInfo.getStyle().set("font-weight", "bold");

        //ADDING COMPONENTS
        deleteLayout.add(deleteLabel, deletePassword, deletePasswordRepeat, deleteAccountButton, deleteUserInfo);
        add(deleteLayout);

        deleteAccountButton.addClickListener(e -> {
            if (UserAccount.getInstance() != null && deletePassword.getValue().equals(deletePasswordRepeat.getValue())) {
                URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/loggedIn")
                        .queryParam("id", UserAccount.getInstance().getId())
                        .build().encode().toUri();
                Boolean authenticate = restTemplate.getForObject(url, Boolean.class);
                if (authenticate != null && authenticate) {
                    URI url2 = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/users/delete")
                            .queryParam("id", UserAccount.getInstance().getId())
                            .queryParam("password", deletePassword.getValue())
                            .build().encode().toUri();
                    restTemplate.delete(url2);
                    deleteUserInfo.getStyle().set("color", "green");
                    deleteUserInfo.setText("Succesfully deleted Account");
                    deleteUserInfo.setVisible(true);
                    UserAccount.getInstance().signOut();
                    UI.getCurrent().navigate(MainView.class);
                } else {
                    deleteUserInfo.getStyle().set("color", "red");
                    deleteUserInfo.setText("Something went wrong");
                    deleteUserInfo.setVisible(true);
                }
            } else {
                deleteUserInfo.getStyle().set("color", "red");
                deleteUserInfo.setText("Something went wrong");
                deleteUserInfo.setVisible(true);
            }
        });
    }

}
