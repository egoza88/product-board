package com.product.board.productboardassignment.controller;

import com.product.board.productboardassignment.service.ProjectStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProjectStateController {

    private final ProjectStateService projectStateService;

    @Autowired
    public ProjectStateController(ProjectStateService projectStateService) {
        this.projectStateService = projectStateService;
    }

    @GetMapping("/getByName")
    private ResponseEntity<String> getProjectState(String name, Optional<String> date) {
        return projectStateService.findProjectState(name, date);
    }
}
