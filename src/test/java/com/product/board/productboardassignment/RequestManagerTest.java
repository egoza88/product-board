package com.product.board.productboardassignment;

import com.product.board.productboardassignment.scheduler.http.RequestManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class RequestManagerTest {

    @Autowired
    RequestManager requestManager;

    @Test
    void getProjectNames() {
//      Test repo or mock must be used to check number of project in repo
        List<String> getProjectNames = requestManager.getProjectNames();

        Assertions.assertEquals(57, getProjectNames.size(),
                "Last time there were 57 packages in ProductBoard repo");
    }

    @Test
    void getProjectStatsValidProjectName() {
        Map<String, Object> response = requestManager.getProjectStats("react");
        Assertions.assertFalse(response.isEmpty(),
                "With valid project name response must be non empty");
    }

    @Test
    void getProjectStatsInvalidProjectName() {
        Map<String, Object> response = requestManager.getProjectStats(null);
        Assertions.assertTrue(response.isEmpty(),
                "With invalid project name response must be empty");
    }
}
