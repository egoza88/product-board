package com.product.board.productboardassignment.controller;

import com.product.board.productboardassignment.service.ProjectStateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/projectStats")
public class ProjectStateController {

    private final ProjectStateService projectStateService;

    @Autowired
    public ProjectStateController(ProjectStateService projectStateService) {
        this.projectStateService = projectStateService;
    }

    @GetMapping("/getByName")
    @ApiOperation(value = "Get project stats by id, and optionally date", produces = MediaType.APPLICATION_JSON_VALUE,
            notes = "Returns info about languages in provided project", response = String.class)
    private ResponseEntity<String> getProjectState(
            @ApiParam(value="Date must be provided in ISO UTC format", required = true) String name,
            Optional<String> date) {
        return projectStateService.findProjectState(name, date);
    }


}
