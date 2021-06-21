package com.kodilla.bookmaker.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class BookmakerAppClientConfig {

    @Value("${backend.api.address}")
    private String backendApiEndpoint;
}
