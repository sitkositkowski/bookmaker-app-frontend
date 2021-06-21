package com.kodilla.bookmaker.app.mapper;

import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.PredictionView;

public class Mapper {

    public PredictionDto mapToDto(PredictionView predictionView) {
        return PredictionDto.builder()
                .matchId(predictionView.getMatchId())
                .userId(predictionView.getUserId())
                .homeTeamScore(predictionView.getPred_home_team_score())
                .awayTeamScore(predictionView.getPred_away_team_score())
                .winner(predictionView.getPred_winner())
                .points(predictionView.getPoints())
                .build();
    }

}
