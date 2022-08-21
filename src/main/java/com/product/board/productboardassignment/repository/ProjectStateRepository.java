package com.product.board.productboardassignment.repository;

import com.product.board.productboardassignment.model.ProjectState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface ProjectStateRepository extends MongoRepository<ProjectState, String> {
    ProjectState findFirstByProjectNameOrderByDateCreatedDesc(String projectName);

    ProjectState findFirstByProjectNameAndDateCreatedBeforeOrderByDateCreatedDesc(String projectName, Date dateCreated);
}
