package com.kodilla.bookmaker.app.client;

import com.kodilla.bookmaker.app.client.service.MatchClient;
import com.kodilla.bookmaker.app.client.service.PredictionClient;
import com.kodilla.bookmaker.app.client.service.UserClient;
import com.kodilla.bookmaker.app.client.service.ViewClient;
import com.kodilla.bookmaker.app.config.BookmakerAppClientConfig;
import com.kodilla.bookmaker.app.dto.MatchDto;
import com.kodilla.bookmaker.app.dto.PredictionDto;
import com.kodilla.bookmaker.app.dto.UserDto;
import lombok.Getter;
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
@Getter
public class BookmakerAppClient {

    private final MatchClient matchClient;
    private final PredictionClient predictionClient;
    private final UserClient userClient;
    private final ViewClient viewClient;
}
