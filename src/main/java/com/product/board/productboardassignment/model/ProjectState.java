package com.product.board.productboardassignment.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//@Entity
@Getter
@Setter
@Document(collection = "ProjectState")
public class ProjectState {

    @Getter(AccessLevel.NONE)
    private String id;

    private Date dateCreated;
    private String projectName;
    private String languageStats;
}
