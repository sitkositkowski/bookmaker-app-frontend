package com.kodilla.bookmaker.app.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PredictionKey {

    private Long userId;
    private Long matchId;
}
