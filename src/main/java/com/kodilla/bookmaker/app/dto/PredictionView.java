package com.kodilla.bookmaker.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionView {

    private Integer matchId;
    private Integer userId;
    private String username;
    private String home_team;
    private Integer home_team_score;
    private Integer away_team_score;
    private String away_team;
    private String status;
    private Integer pred_home_team_score;
    private Integer pred_away_team_score;
    private String winner;
    private String pred_winner;
    private Integer points;
}
