package com.kodilla.ui.views;

import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class PredictionForm extends FormLayout {


    //IntegerField id = new IntegerField("id");
    //IntegerField userId = new IntegerField("userId");
    //IntegerField matchId = new IntegerField("matchId");
    //ComboBox<MatchDto> match = new ComboBox<>("match");
    TextField winner = new TextField("winner");
    IntegerField homeTeamScore = new IntegerField("homeTeamScore");
    IntegerField awayTeamScore = new IntegerField("awayTeamScore");
    IntegerField points = new IntegerField("points");

    //private BookmakerAppClient client;
    //private MainLayout mainLayout;
    private PredictionDto predictionDto;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    private Binder<PredictionDto> binder = new Binder<PredictionDto>(PredictionDto.class);

    public PredictionForm() {

        addClassName("prediction-form");
        binder.bindInstanceFields(this);

        //match.setItems(matches);
        //match.setItemLabelGenerator(item -> {
        //    return item.getHomeTeam() + " vs. " + item.getAwayTeam();
        //});

        add(winner, homeTeamScore, awayTeamScore, points,
                createButtonsLayout());
    }

    public void setPrediction(PredictionDto predictionDto) {
        this.predictionDto = predictionDto;
        binder.readBean(predictionDto);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, predictionDto)));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {

        try {
            binder.writeBean(predictionDto);
            fireEvent(new SaveEvent(this, predictionDto));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class PredictionFormEvent extends ComponentEvent<PredictionForm> {
        private PredictionDto predictionDto;

        protected PredictionFormEvent(PredictionForm source, PredictionDto predictionDto) {
            super(source, false);
            this.predictionDto = predictionDto;
        }

        public PredictionDto getPrediction() {
            return predictionDto;
        }
    }

    public static class SaveEvent extends PredictionFormEvent {
        SaveEvent(PredictionForm source, PredictionDto contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends PredictionFormEvent {
        DeleteEvent(PredictionForm source, PredictionDto contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends PredictionFormEvent {
        CloseEvent(PredictionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
