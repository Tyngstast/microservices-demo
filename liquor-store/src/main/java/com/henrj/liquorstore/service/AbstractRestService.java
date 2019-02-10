package com.henrj.liquorstore.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class AbstractRestService {

    protected String buildUri(String baseUrl, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        params.forEach(builder::queryParam);
        return builder
                .build()
                .toUriString();
    }
}
