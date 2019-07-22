package com.kodilla.frontend.view;

import com.kodilla.frontend.UserAccount;
import com.kodilla.frontend.view.account.AccountView;
import com.kodilla.frontend.view.account.SignInView;
import com.kodilla.frontend.view.account.SignUpView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AccountBar {

    private Button signIn = new Button("SIGN IN");
    private Button signUp = new Button("SIGN UP");
    private Button account = new Button("ACCOUNT");
    private Button signOut = new Button("SIGN OUT");

    public HorizontalLayout drawAccountNavigateBar() {
        signIn.getStyle().set("background", "none");
        signUp.getStyle().set("background", "none");
        account.getStyle().set("background", "none");
        signOut.getStyle().set("background", "none");

        account.setVisible(!UserAccount.isInstanceNull());
        signOut.setVisible(!UserAccount.isInstanceNull());
        signIn.setVisible(UserAccount.isInstanceNull());
        signUp.setVisible(UserAccount.isInstanceNull());

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
            UI.getCurrent().getPage().reload();
        });

        return new HorizontalLayout(signOut, signIn, signUp, account);
    }

}
