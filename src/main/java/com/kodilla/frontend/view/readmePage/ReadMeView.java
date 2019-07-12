package com.kodilla.frontend.view.readmePage;

import com.kodilla.frontend.view.NavigateBar;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class ReadMeView extends VerticalLayout {

    public ReadMeView(){
        VerticalLayout menu = NavigateBar.drawNavigateBar();
        Label info = new Label("INFORMATION ABOUT APP:");
        Label text = new Label();
        text.setText("Hey this app is my lastest project from Kodilla Course." +
                "I picked to do travel app. Unfortunately there is no good and free external API so I am limited to demo versions. Some data can be \n" +
                "generated automatically because of this." + "Sometimes you need to search for results twice maybe more. It depends on external API not my app.");

        Label email = new Label("Here is contact with me: greedofiro@gmail.com");

        add(menu);
        add(info);
        add(text);
        add(email);
    }


}
