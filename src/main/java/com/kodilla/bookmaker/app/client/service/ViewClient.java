package com.kodilla.bookmaker.app.client.service;

import com.kodilla.bookmaker.app.config.BookmakerAppClientConfig;
import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.PredictionView;
import com.kodilla.bookmaker.app.dto.RankingRecord;
import lombok.RequiredArgsConstructor;
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
public class ViewClient {

    private final RestTemplate restTemplate;
    private final BookmakerAppClientConfig bookmakerAppClientConfig;


    public List<PredictionView> getPredictions() {
        URI url = buildUrlPrediction();
        System.out.println(url);
        try {
            PredictionView[] boardsResponse = restTemplate.getForObject(url, PredictionView[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new PredictionView[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<RankingRecord> getRanking() {
        URI url = buildUrlRanking();
        System.out.println(url);
        try {
            RankingRecord[] boardsResponse = restTemplate.getForObject(url, RankingRecord[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new RankingRecord[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public URI buildUrlPrediction() {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/predictionView")
                .build().encode().toUri();
    }

    public URI buildUrlRanking() {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/ranking")
                .build().encode().toUri();
    }


}
