package com.kodilla.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

@CssImport("./styles/shared-styles.css")
@PWA(
        name = "BookmakerApp",
        shortName = "BKMKR")
public class MainLayout extends AppLayout {


    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Bookmaker Game");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.expand(logo);

        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);

        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);



    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Predictions list", PredictionLayout.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Ranking", RankingLayout.class)
        ));

        //addToDrawer(new VerticalLayout(listLink));
    }
}
