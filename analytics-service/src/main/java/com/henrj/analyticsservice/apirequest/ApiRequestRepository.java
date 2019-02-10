package com.henrj.analyticsservice.apirequest;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ApiRequestRepository extends MongoRepository<ApiRequest, String> {
    Optional<List<ApiRequest>> findByUser(String user);
    Optional<List<ApiRequest>> findByStatusCodeStartsWith(int series);
}
