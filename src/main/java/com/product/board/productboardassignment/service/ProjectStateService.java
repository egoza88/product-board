package com.product.board.productboardassignment.service;

import com.product.board.productboardassignment.model.ProjectState;
import com.product.board.productboardassignment.repository.ProjectStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class ProjectStateService {

    private final ProjectStateRepository projectStateRepository;

    @Autowired
    public ProjectStateService(ProjectStateRepository projectStateRepository) {
        this.projectStateRepository = projectStateRepository;
    }

    public ResponseEntity<String> findProjectState(String projectName, Optional<String> isoDate) {
        try {
            if (isoDate.isEmpty()) {
                return findProjectStateByName(projectName);
            }
            return findProjectStateByNameAndTime(projectName, isoDate.get());
        } catch (Exception e) {
            log.error("Failed to parse request for project {}", projectName, e);
        }
        return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    }

    public void saveProjectState(ProjectState projectState) {
        projectStateRepository.save(projectState);
    }

    private ResponseEntity<String> findProjectStateByName(String name) {
        ProjectState projectState = projectStateRepository.findFirstByProjectNameOrderByDateCreatedDesc(name);
        if (projectState == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                projectState.getLanguageStats(),
                HttpStatus.OK
        );
    }

    private ResponseEntity<String> findProjectStateByNameAndTime(String name, String date) {
        Date parsedDate = parseStringToDate(date);
        ProjectState projectStates = projectStateRepository.findFirstByProjectNameAndDateCreatedBeforeOrderByDateCreatedDesc(name, parsedDate);
        if (projectStates == null) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                projectStates.getLanguageStats(),
                HttpStatus.OK);
    }

    private Date parseStringToDate(String date) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(date);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }
}
