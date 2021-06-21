package com.kodilla.ui;

import com.kodilla.bookmaker.app.client.BookmakerAppClient;
import com.kodilla.bookmaker.app.dto.MatchDto;
import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.RankingRecord;
import com.kodilla.bookmaker.app.dto.UserDto;
import com.kodilla.ui.views.PredictionForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ranking", layout = MainLayout.class)
@PageTitle("Ranking | Booking App")
public class RankingLayout extends VerticalLayout {

    private BookmakerAppClient client;
    private Grid<RankingRecord> grid = new Grid<>(RankingRecord.class);


    public RankingLayout(BookmakerAppClient client) {
        this.client = client;

        addClassName("ranking-view");
        setSizeFull();

        configureGridRanking();

        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateRanking();

        //closeEditor();
    }



    private void configureGridRanking() {
        grid.addClassName("ranking-grid");
        grid.setSizeFull();
        grid.setColumns("name", "points",  "predictions");
    }


    private void updateRanking() {
        grid.setItems(client.getViewClient().getRanking());
    }

}
