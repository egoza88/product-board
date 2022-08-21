package com.product.board.productboardassignment.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepoInfoResponse {
    @JsonProperty("name")
    String projectName;

    public String getProjectName() {
        return projectName;
    }
}
