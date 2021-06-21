package com.kodilla.ui;

import com.kodilla.bookmaker.app.client.BookmakerAppClient;
import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.PredictionView;
import com.kodilla.bookmaker.app.dto.RankingRecord;
import com.kodilla.bookmaker.app.mapper.Mapper;
import com.kodilla.ui.views.PredictionForm;
import com.kodilla.ui.views.PredictionForm2;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Predictions List | BookmakerApp")
public class PredictionLayout extends VerticalLayout {

    private BookmakerAppClient client;
    private Mapper mapper = new Mapper();
    private Grid<PredictionView> grid = new Grid<>(PredictionView.class);
    private PredictionForm2 form;

    public PredictionLayout(BookmakerAppClient client) {
        this.client = client;

        addClassName("list-view");
        setSizeFull();

        configureGrid();
        //form.setVisible(true);
        form =  new PredictionForm2(client);
        form.addListener(PredictionForm2.SaveEvent.class, this::savePrediction);
        form.addListener(PredictionForm2.DeleteEvent.class, this::deletePrediction);
        form.addListener(PredictionForm2.CloseEvent.class, e -> closeEditor());



        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(content);
        updateList();

        closeEditor();
    }

    private void configureGrid() {
        grid.addClassName("prediction-grid");
        grid.setSizeFull();
        grid.setColumns("matchId",
        "userId","username","home_team","home_team_score","away_team_score","away_team","status","pred_home_team_score",
        "pred_away_team_score", "winner", "pred_winner", "points");

        grid.asSingleSelect().addValueChangeListener(event ->
                editPrediction(event.getValue()));
    }

    public void editPrediction(PredictionView predictionView) {
        if (predictionView == null) {
            closeEditor();
        } else {
            form.setPredictionView(predictionView);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setPredictionView(null);
        form.setVisible(false);
        removeClassName("editing");
    }


    private void updateList() {
        grid.setItems(client.getViewClient().getPredictions());
    }

    private void updatePrediction(PredictionForm2.SaveEvent event) {
        PredictionDto predictionDto = mapper.mapToDto(event.getPredictionView());
        client.getPredictionClient().updatePrediction(predictionDto);
        updateList();
        closeEditor();
    }



    private void savePrediction(PredictionForm2.SaveEvent event) {
        PredictionView predictionViewFromEvent = event.getPredictionView();
        if (!(client.getPredictionClient().getPrediction(predictionViewFromEvent.getUserId(),predictionViewFromEvent.getMatchId()).getMatchId()==null)) {
            updatePrediction(event);
        } else {
            PredictionDto predictionDto = mapper.mapToDto(predictionViewFromEvent);
            PredictionDto predictionDto2 = client.getPredictionClient().createPrediction(predictionDto);
        }
        updateList();
        closeEditor();
    }

    private void deletePrediction(PredictionForm2.DeleteEvent event) {
        System.out.println(event.getPredictionView());
        PredictionDto predictionDto = mapper.mapToDto(event.getPredictionView());
        System.out.println(predictionDto);
        //client.getPredictionClient().deletePrediction(predictionDto.getUserId(),predictionDto.getMatchId());
        updateList();
        closeEditor();
    }
}
