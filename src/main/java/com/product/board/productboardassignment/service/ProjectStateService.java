package com.product.board.productboardassignment.service;

import com.product.board.productboardassignment.model.ProjectState;
import com.product.board.productboardassignment.repository.ProjectStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectStateService {

    private final ProjectStateRepository projectStateRepository;

    @Autowired
    public ProjectStateService(ProjectStateRepository projectStateRepository) {
        this.projectStateRepository = projectStateRepository;
    }

    public ResponseEntity<String> findProjectState(String name, Optional<String> isoDate) {
        if (isoDate.isEmpty()) {
            return findProjectStateByName(name);
        }
        return findProjectStateByNameAndTime(name, isoDate.get());
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
        List<ProjectState> projectStates = projectStateRepository.findByProjectNameAndDateCreatedBefore(name, parsedDate);
        if (projectStates.isEmpty()) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private Date parseStringToDate(String date) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(date);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }
}
