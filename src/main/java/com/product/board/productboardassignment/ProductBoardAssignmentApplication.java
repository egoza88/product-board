package com.product.board.productboardassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.product.board.productboardassignment.repository"})
public class ProductBoardAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductBoardAssignmentApplication.class, args);
    }
}