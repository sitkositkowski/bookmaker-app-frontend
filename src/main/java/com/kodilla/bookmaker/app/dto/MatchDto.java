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
public class MatchDto {

    private int id;
    private String status;
    private String homeTeam;
    private String awayTeam;
    private String winner;
    private String duration;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}

