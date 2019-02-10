package com.henrj.edgeservicezuul.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@Slf4j
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .csrf().ignoringRequestMatchers(new RequestHeaderRequestMatcher(HttpHeaders.AUTHORIZATION))
                .and()
//                .requestMatcher(new RequestHeaderRequestMatcher(HttpHeaders.AUTHORIZATION))
                .authorizeRequests()
                    .antMatchers("/actuator/health").permitAll()
                    .mvcMatchers("/customers/**").access("hasAuthority('SCOPE_email')")
                    .anyRequest().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .oauth2ResourceServer()
                    .jwt()
                    .jwkSetUri(jwkUri);
    }
}