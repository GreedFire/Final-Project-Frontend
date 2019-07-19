package com.kodilla.frontend.view.account;

import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route
public class AccountView extends VerticalLayout {
    public AccountView() {
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

        //CSS
        passwordLayout.getStyle().set("margin", "auto");
        passwordLabel.getStyle().set("font-size", "30px");
        passwordLabel.getStyle().set("font-weight", "bold");

        //ADDING COMPONENTS
        passwordLayout.add(passwordLabel, oldPassword, newPassword, newPasswordRepeat, changePasswordButton);
        add(passwordLayout);

        changePasswordButton.addClickListener(e -> {
            if (newPassword.getValue().equals(newPasswordRepeat.getValue())) {

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

        //CSS
        deleteLabel.getStyle().set("font-size", "30px");
        deleteLabel.getStyle().set("font-weight", "bold");
        deleteAccountButton.getStyle().set("color", "red");

        //ADDING COMPONENTS
        deleteLayout.add(deleteLabel, deletePassword, deletePasswordRepeat, deleteAccountButton);
        add(deleteLayout);

        deleteAccountButton.addClickListener(e -> {
            if (deletePassword.getValue().equals(deletePasswordRepeat.getValue())) {

            }
        });
    }

}
