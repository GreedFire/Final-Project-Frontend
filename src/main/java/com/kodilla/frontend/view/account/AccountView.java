package com.kodilla.frontend.view.account;

import com.kodilla.frontend.UrlGenerator;
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
        changePasswordInfo.getStyle().set("font-size", "20px");
        changePasswordInfo.getStyle().set("font-weight", "bold");

        //FORMATTING
        oldPassword.setRequiredIndicatorVisible(true);
        newPassword.setRequiredIndicatorVisible(true);
        newPasswordRepeat.setRequiredIndicatorVisible(true);
        oldPassword.setMinLength(3);
        newPassword.setMinLength(3);
        newPasswordRepeat.setMinLength(3);

        //ADDING COMPONENTS
        passwordLayout.add(passwordLabel, oldPassword, newPassword, newPasswordRepeat, changePasswordButton, changePasswordInfo);
        add(passwordLayout);

        changePasswordButton.addClickListener(e -> {
            if (!UserAccount.isInstanceNull() && newPassword.getValue().equals(newPasswordRepeat.getValue())) {
                Boolean authenticate = restTemplate.getForObject(UrlGenerator.userloggedIntURL(UserAccount.getInstance().getId()), Boolean.class);
                if (authenticate != null && authenticate) {
                    restTemplate.put(UrlGenerator.passwordChangeURL(oldPassword.getValue(), newPassword.getValue()), null);
                    Boolean isNewPasswordOk = restTemplate.getForObject(UrlGenerator.checkNewPasswordURL(UserAccount.getInstance().getId(), newPassword.getValue()), Boolean.class);
                    if(isNewPasswordOk != null && isNewPasswordOk) {
                        changePasswordInfo.getStyle().set("color", "green");
                        changePasswordInfo.setText("Successfully changed password");
                    }
                    else{
                        changePasswordInfo.getStyle().set("color", "red");
                        changePasswordInfo.setText("Old password is wrong!");
                    }
                } else {
                    changePasswordInfo.getStyle().set("color", "red");
                    changePasswordInfo.setText("Not logged In");
                }
            } else {
                changePasswordInfo.getStyle().set("color", "red");
                changePasswordInfo.setText("Password not match");
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
        deleteUserInfo.getStyle().set("font-size", "20px");
        deleteUserInfo.getStyle().set("font-weight", "bold");

        //FORMATTING
        deletePassword.setRequiredIndicatorVisible(true);
        deletePasswordRepeat.setRequiredIndicatorVisible(true);
        deletePassword.setMinLength(3);
        deletePasswordRepeat.setMinLength(3);

        //ADDING COMPONENTS
        deleteLayout.add(deleteLabel, deletePassword, deletePasswordRepeat, deleteAccountButton, deleteUserInfo);
        add(deleteLayout);

        deleteAccountButton.addClickListener(e -> {
            if (!UserAccount.isInstanceNull() && deletePassword.getValue().equals(deletePasswordRepeat.getValue())) {
                Boolean authenticate = restTemplate.getForObject(UrlGenerator.userloggedIntURL(UserAccount.getInstance().getId()), Boolean.class);
                if (authenticate != null && authenticate) {
                    restTemplate.delete(UrlGenerator.accountDeleteURL(UserAccount.getInstance().getId(), deletePassword.getValue()));
                    deleteUserInfo.getStyle().set("color", "green");
                    deleteUserInfo.setText("Succesfully deleted Account");
                    UserAccount.getInstance().signOut();
                    UI.getCurrent().navigate(MainView.class);
                } else {
                    deleteUserInfo.getStyle().set("color", "red");
                    deleteUserInfo.setText("Something went wrong");
                }
            } else {
                deleteUserInfo.getStyle().set("color", "red");
                deleteUserInfo.setText("Password not match");
            }
        });
    }

}
