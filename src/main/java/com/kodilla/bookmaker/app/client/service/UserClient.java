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
public class UserClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.kodilla.bookmaker.app.client.BookmakerAppClient.class);

    private final RestTemplate restTemplate;
    private final BookmakerAppClientConfig bookmakerAppClientConfig;

    public List<UserDto> getUsers() {
        URI url = buildUrlUser();
        //System.out.println(url);
        try {
            UserDto[] boardsResponse = restTemplate.getForObject(url, UserDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new UserDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public UserDto getUser(int id) {
        URI url = buildUrlUserId(id);
        //System.out.println(url);
        try {
            UserDto boardsResponse = restTemplate.getForObject(url, UserDto.class);
            return ofNullable(boardsResponse).orElse(new UserDto());
        } catch (RestClientException e) {
            return new UserDto();
        }
    }


    public URI buildUrlUser() {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint() + "/users")
                .build().encode().toUri();
    }

    public URI buildUrlUserId(int id) {
        return UriComponentsBuilder.fromHttpUrl(bookmakerAppClientConfig.getBackendApiEndpoint() + "/users/" + id)
                .build().encode().toUri();
    }
}