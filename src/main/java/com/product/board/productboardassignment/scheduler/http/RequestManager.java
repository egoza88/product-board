package com.product.board.productboardassignment.scheduler.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.board.productboardassignment.scheduler.dto.RepoInfoResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RequestManager {
    private final ObjectMapper objectMapper;

    @Value("${organisation.name}")
    private String organisationRepoName;

    @Value("${github.token}")
    private String authToken;

    public RequestManager() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<String> getProjectNames() {
        HttpResponseWrapper responseWrapper = HttpClient.sendGet(
                "https://api.github.com/orgs/" + organisationRepoName + "/repos?per_page=100",
                getHeaders());

        if (!responseWrapper.isSuccessful()) {
            log.error("GetProjectNames request failed. Response code: {}. Response body: {}",
                    responseWrapper.getStatusCode(), responseWrapper.getBody());
            return Collections.emptyList();
        }

        try {
            List<RepoInfoResponse> repoInfoResponses = objectMapper.readValue(responseWrapper.getBody(), new TypeReference<>() {
            });
            return repoInfoResponses.stream().map(RepoInfoResponse::getProjectName).toList();
        } catch (JsonProcessingException e) {
            log.error("Jackson failed to map response body to object", e);
        }

        return Collections.emptyList();
    }

    public Map<String, Object> getProjectStats(String projectName) {
        HttpResponseWrapper responseWrapper = HttpClient.sendGet(
                "https://api.github.com/repos/" + organisationRepoName + "/" + projectName + "/languages",
                getHeaders());

        if (!responseWrapper.isSuccessful()) {
            log.error("GetProjectStats request failed. Project: {}. Response code: {}. Response body: {}",
                    projectName, responseWrapper.getStatusCode(), responseWrapper.getBody());
            return Collections.emptyMap();
        }

        try {
            return objectMapper.readValue(responseWrapper.getBody(), HashMap.class);
        } catch (JsonProcessingException e) {
            log.error("Jackson failed to map response body to object", e);
        }

        return Collections.emptyMap();
    }

    private Headers getHeaders() {
        return Headers.of("Content-Type", "application/json",
                "Accept", "application/json", "Authorization", "Token " + authToken);
    }
}
