package com.kodilla.bookmaker.app.client.service;

import com.kodilla.bookmaker.app.config.BookmakerAppClientConfig;
import com.kodilla.bookmaker.app.dto.MatchDto;
import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class PredictionClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.kodilla.bookmaker.app.client.BookmakerAppClient.class);

    private final RestTemplate restTemplate;
    private final BookmakerAppClientConfig bookmakerAppClientConfig;


    public List<PredictionDto> getPredictions() {
        URI url = buildUrlPrediction();
        System.out.println(url);
        try {
            PredictionDto[] boardsResponse = restTemplate.getForObject(url, PredictionDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new PredictionDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public PredictionDto getPrediction(int userId, int matchId) {
        URI url = buildUrlDeletePrediction(userId, matchId);
        System.out.println(url);
        try {
            PredictionDto boardsResponse = restTemplate.getForObject(url, PredictionDto.class);
            return ofNullable(boardsResponse).orElse(new PredictionDto());
        } catch (RestClientException e) {
            return new PredictionDto();
        }
    }

    public PredictionDto createPrediction(PredictionDto predictionDto) {
        URI url = buildUrlPrediction();
        try {
            System.out.println(url);
            PredictionDto boardsResponse = restTemplate.postForObject(url, predictionDto,PredictionDto.class);
            System.out.println(boardsResponse);
            return boardsResponse;
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
            return new PredictionDto();
        }
    }

    public void updatePrediction(PredictionDto predictionDto) {
        URI url = buildUrlPrediction();
        try {
            System.out.println(url);
            restTemplate.put(url,predictionDto);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePrediction(int userId, int matchId) {
        URI url = buildUrlDeletePrediction(userId, matchId);
        System.out.println(url);
        try {
            restTemplate.delete(url);
        } catch (RestClientException e) {
            System.out.println(e.getMessage());
        }
    }


    public URI buildUrlPrediction() {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/predictions")
                .build().encode().toUri();
    }

    public URI buildUrlDeletePrediction(int userId, int matchId) {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/predictions/" + userId + "&" + matchId)
                .build().encode().toUri();
    }

}