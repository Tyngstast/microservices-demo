package com.henrj.liquorstore.web;

import com.henrj.liquorstore.data.ApiRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class RequestTrackingFilter implements Filter {

    private static final String KAFKA_TOPIC = "api_requests";
    private final KafkaTemplate<String, ApiRequestEvent> kafkaTemplate;

    public RequestTrackingFilter(KafkaTemplate<String, ApiRequestEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = ((HttpServletRequest) servletRequest);

        if (request.getRequestURI().contains("/api")) {
            Authentication authentication = (Authentication) request.getUserPrincipal();
            if (authentication instanceof OAuth2AuthenticationToken) {
                String username = ((DefaultOAuth2User)authentication.getPrincipal()).getAttributes().get("preferred_username").toString();
                String requestUri = request.getRequestURI();
                ApiRequestEvent apiRequestEvent = new ApiRequestEvent(username, requestUri, request.getMethod(), ((HttpServletResponse)servletResponse).getStatus(), new Date());
                kafkaTemplate.send(KAFKA_TOPIC, apiRequestEvent);
            }
        }

    }
}
