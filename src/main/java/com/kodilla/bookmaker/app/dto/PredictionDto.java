package com.kodilla.bookmaker.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionDto {

    //private PredictionKey id;
    private Integer userId;
    private Integer matchId;
    private String winner;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Integer points;
}

