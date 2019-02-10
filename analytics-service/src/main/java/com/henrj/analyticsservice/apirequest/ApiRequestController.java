package com.henrj.analyticsservice.apirequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api-requests", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ApiRequestController {

    private final ApiRequestRepository apiRequestRepository;

    public ApiRequestController(ApiRequestRepository apiRequestRepository) {
        this.apiRequestRepository = apiRequestRepository;
    }

    @GetMapping
    public List<ApiRequest> getAll() {
        return apiRequestRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Optional<ApiRequest> apiRequest = apiRequestRepository.findById(id);
        return apiRequest
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "user")
    public ResponseEntity getByUserId(@RequestParam String user) {
        Optional<List<ApiRequest>> apiRequests = apiRequestRepository.findByUser(user);
        return apiRequests
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "series")
    public ResponseEntity getBySeries(@RequestParam HttpStatus.Series series) {
        Optional<List<ApiRequest>> apiRequests = apiRequestRepository.findByStatusCodeStartsWith(series.value());
        return apiRequests
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid ApiRequest apiRequest) {
        if (!apiRequestRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apiRequest.setId(id);
        apiRequestRepository.save(apiRequest);
        return ResponseEntity.ok(apiRequest);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ApiRequest apiRequest) {
        ApiRequest saved = apiRequestRepository.save(apiRequest);
        return ResponseEntity
                .created(URI.create("/api-requests/" + saved.getId()))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        if (!apiRequestRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apiRequestRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
