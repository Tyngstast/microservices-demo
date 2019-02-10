package com.henrj.analyticsservice.kafka;

import com.henrj.analyticsservice.apirequest.ApiRequest;
import com.henrj.analyticsservice.apirequest.ApiRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaApiRequestConsumer {

    private final ApiRequestRepository apiRequestRepository;

    public KafkaApiRequestConsumer(ApiRequestRepository apiRequestRepository) {
        this.apiRequestRepository = apiRequestRepository;
    }

    @KafkaListener(groupId = "api_requests_group",
            topics = "api_requests",
            containerFactory = "apiRequestKafkaListenerFactory")
    public void orderListener(ApiRequest apiRequest) {
        log.info("Message received: " + apiRequest);
        this.apiRequestRepository.save(apiRequest);
    }
}
