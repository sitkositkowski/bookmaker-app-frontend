package com.kodilla.ui.views;

import com.kodilla.bookmaker.app.client.BookmakerAppClient;
import com.kodilla.bookmaker.app.dto.MatchDto;
import com.kodilla.bookmaker.app.dto.PredictionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

import java.util.List;
import java.util.stream.Collectors;

//@Route(value = "/v1/form")
public class PredictionForm2 extends FormLayout {

    private BookmakerAppClient client;

    //IntegerField matchId = new IntegerField("Match ID");
    ComboBox<Integer> matchId = new ComboBox<Integer>("Match");
    //IntegerField userId = new IntegerField("User ID");
    ComboBox<Integer> userId = new ComboBox<Integer>("User");
    TextField username = new TextField("Username");
    TextField home_team = new TextField("Home team");
    IntegerField home_team_score = new IntegerField("Home team score");;
    IntegerField away_team_score = new IntegerField("Away team score");
    TextField away_team = new TextField("Away team");
    TextField status = new TextField("Status");
    IntegerField pred_home_team_score = new IntegerField("Predicted home team score");
    IntegerField pred_away_team_score = new IntegerField("Predicted away team score");
    TextField winner = new TextField("Winner");;
    TextField pred_winner = new TextField("Predicted Winner");
    IntegerField points = new IntegerField("Points");;


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    private Binder<PredictionView> binder = new Binder<>(PredictionView.class);
    private PredictionView predictionView = new PredictionView();

    public PredictionForm2(BookmakerAppClient client) {
        this.client = client;
        addClassName("prediction-form");
        binder.bindInstanceFields(this);
        //binder.forField(matchId).bind(PredictionView::getMatchId,PredictionView::setMatchId);
        //binder.forField(userId).bind(PredictionView::getUserId,PredictionView::setUserId);
        //binder.forField(username).bind(PredictionView::getUsername,PredictionView::setMatchId);
        //binder.forField(matchId).bind(PredictionView::getMatchId,PredictionView::setMatchId);
        //binder.forField(matchId).bind(PredictionView::getMatchId,PredictionView::setMatchId);
        //binder.forField(matchId).bind(PredictionView::getMatchId,PredictionView::setMatchId);
        //binder.forField(matchId).bind(PredictionView::getMatchId,PredictionView::setMatchId);
        /*
        status.setItems(Contact.Status.values());
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        */
        username.setReadOnly(true);
        home_team.setReadOnly(true);
        away_team.setReadOnly(true);
        status.setReadOnly(true);
        List<Integer> matches = client.getMatchClient().getMatches().stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());
        matchId.setItems(matches);
        List<Integer> users = client.getUserClient().getUsers().stream()
                .map(u -> u.getId())
                .collect(Collectors.toList());
        userId.setItems(users);
        add(
                matchId,
                //match,
                //user,
                userId,
                status,
                username,
                home_team,
                //home_team_score,
                //away_team_score,
                away_team,
                //status,
                pred_home_team_score,
                pred_away_team_score,
                //winner,
                //pred_winner,
                //points,
                createButtonsLayout()
        );

        userId.addValueChangeListener(evt -> {
            if (!(userId.getValue() == null)) {
                username.setValue(this.client.getUserClient().getUser(userId.getValue()).getName());
            }
        });
        matchId.addValueChangeListener(evt ->{
            if (!(matchId.getValue() == null)) {
                MatchDto matchDto = client.getMatchClient().getMatch(matchId.getValue());
                home_team.setValue(matchDto.getHomeTeam());
                away_team.setValue(matchDto.getAwayTeam());
                status.setValue(matchDto.getStatus());
                if (matchDto.getStatus() == "FINISHED") {
                    pred_home_team_score.setEnabled(false);
                    pred_away_team_score.setEnabled(false);
                    home_team_score.setValue(matchDto.getHomeTeamScore());
                    away_team_score.setValue(matchDto.getAwayTeamScore());
                    winner.setValue(matchDto.getWinner());
                } else {
                    pred_home_team_score.setEnabled(true);
                    pred_away_team_score.setEnabled(true);
                }
            }
        });

    }

    public void setPredictionView(PredictionView predictionView) {
        this.predictionView = predictionView;
        binder.readBean(predictionView);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, predictionView)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (pred_home_team_score.getValue() > pred_away_team_score.getValue()) {
            pred_winner.setValue("HOME_TEAM");
        } else if (pred_away_team_score.getValue() > pred_home_team_score.getValue()) {
            pred_winner.setValue("AWAY_TEAM");
        } else {
            pred_winner.setValue("DRAW");
        }
        try {
            binder.writeBean(predictionView);
            fireEvent(new SaveEvent(this, predictionView));
            System.out.println(predictionView);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class PredictionViewFormEvent extends ComponentEvent<PredictionForm2> {
        private PredictionView predictionView;

        protected PredictionViewFormEvent(PredictionForm2 source, PredictionView predictionView) {
            super(source, false);
            this.predictionView = predictionView;
        }

        public PredictionView getPredictionView() {
            return predictionView;
        }
    }

    public static class SaveEvent extends PredictionViewFormEvent {
        SaveEvent(PredictionForm2 source, PredictionView predictionView) {
            super(source, predictionView);
        }
    }

    public static class DeleteEvent extends PredictionViewFormEvent {
        DeleteEvent(PredictionForm2 source, PredictionView predictionView) {
            super(source, predictionView);
        }

    }

    public static class CloseEvent extends PredictionViewFormEvent {
        CloseEvent(PredictionForm2 source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
