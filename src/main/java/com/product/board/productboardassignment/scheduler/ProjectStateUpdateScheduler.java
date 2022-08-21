package com.product.board.productboardassignment.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.board.productboardassignment.model.ProjectState;
import com.product.board.productboardassignment.scheduler.http.RequestManager;
import com.product.board.productboardassignment.service.ProjectStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class ProjectStateUpdateScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectStateUpdateScheduler.class);

    private final ProjectStateService projectStateService;
    private final RequestManager requestManager;
    private final ObjectMapper objectMapper;


    @Autowired
    public ProjectStateUpdateScheduler(ProjectStateService projectStateService, RequestManager requestManager) {
        this.projectStateService = projectStateService;
        this.requestManager = requestManager;
        this.objectMapper = new ObjectMapper();
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void updateProjectStatsTask() {
        LOGGER.info("Project Stats task started");
        List<String> projectNames = requestManager.getProjectNames();

        for (String projectName : projectNames) {
            String stats = getProjectStats(projectName);

            ProjectState projectState = new ProjectState();
            projectState.setProjectName(projectName);
            projectState.setLanguageStats(stats);
            projectState.setDateCreated(new Date());

            projectStateService.saveProjectState(projectState);
        }
        LOGGER.info("Project Stats task completed");
    }

    private String getProjectStats(String projectId) {
        long bytesSum = 0;
        Map<String, Double> projectStatsProcessed = new HashMap<>();

        Map<String, Object> projectStatsRaw = requestManager.getProjectStats(projectId);

        for (Object bytes : projectStatsRaw.values()) {
            bytesSum += Long.parseLong(bytes.toString());
        }

        for (String language : projectStatsRaw.keySet()) {
            long languageBytes = Long.parseLong(projectStatsRaw.get(language).toString());
            Double percentage = Math.round(((languageBytes * 1.0) / (bytesSum * 1.0) * 100) * 10) / 10.0;
            projectStatsProcessed.put(language, percentage);
        }

        return mapToString(projectStatsProcessed);
    }

    private String mapToString(Map<String, Double> map) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to map map to string", e);
        }
        return "";
    }
}
