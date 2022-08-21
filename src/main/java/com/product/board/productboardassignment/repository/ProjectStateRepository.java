package com.product.board.productboardassignment.repository;

import com.product.board.productboardassignment.model.ProjectState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ProjectStateRepository extends MongoRepository<ProjectState, String> {
    ProjectState findFirstByProjectNameOrderByDateCreatedDesc(String projectName);

    //    @Query("{'date' : { $lte: \"2022-08-21T19:02:59.149Z\" } }")
    List<ProjectState> findByProjectNameAndDateCreatedBefore(String projectName, Date dateCreated);
}
