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
public class MatchClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.kodilla.bookmaker.app.client.BookmakerAppClient.class);

    private final RestTemplate restTemplate;
    private final BookmakerAppClientConfig bookmakerAppClientConfig;

    public List<MatchDto> getMatches() {
        URI url = buildUrlMatch();
        //System.out.println(url);
        try {
            MatchDto[] boardsResponse = restTemplate.getForObject(url, MatchDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new MatchDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public MatchDto getMatch(int id) {
        URI url = buildUrlMatchId(id);
        //System.out.println(url);
        try {
            MatchDto boardsResponse = restTemplate.getForObject(url, MatchDto.class);
            return ofNullable(boardsResponse).orElse(new MatchDto());
        } catch (RestClientException e) {
            return new MatchDto();
        }
    }

    public URI buildUrlMatch() {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/matches")
                .build().encode().toUri();
    }

    public URI buildUrlMatchId(int id) {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint()+ "/matches/"+ id )
                .build().encode().toUri();
    }

}
