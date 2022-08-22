package com.product.board.productboardassignment;

import com.product.board.productboardassignment.model.ProjectState;
import com.product.board.productboardassignment.service.ProjectStateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
//One more must have here, but I don't want to pay for a test DB :D
//@TestPropertySource(locations="classpath:test.properties")
public class ProjectStateServiceTest {

    @Autowired
    ProjectStateService projectStateService;

    @Test
    void findProjectStateValidProjectName() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("react", Optional.empty());
        Assertions.assertEquals(HttpStatus.OK, requestResponse.getStatusCode(),
                "If project exists - service must return code 200");
        Assertions.assertNotEquals("", requestResponse.getBody(),
                "If project exists - service must return non empty body");
    }

    @Test
    void findProjectStateInvalidProjectName() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("test", Optional.empty());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, requestResponse.getStatusCode(),
                "If project doesn't exist - service must return code 404");
        Assertions.assertEquals("", requestResponse.getBody(),
                "If project doesn't exist - service must return empty body");
    }

    @Test
    void findProjectStateValidProjectNameAndDate() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("react", Optional.of("2050-12-01T22:00:00.694Z"));
        Assertions.assertEquals(HttpStatus.OK, requestResponse.getStatusCode(),
                "If project exists and date is valid and there is some older record in DB - service must return code 200");
        Assertions.assertNotEquals("", requestResponse.getBody(),
                "If project exists and date is valid and there is some older record in DB - service must return non empty body");
    }

    @Test
    void findProjectStateOnlyDate() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("", Optional.of("2050-12-01T22:00:00.694Z"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, requestResponse.getStatusCode(),
                "Without provided project name service must return code 404");
        Assertions.assertEquals("", requestResponse.getBody(),
                "Without provided project name service must return empty body");
    }

    @Test
    void findProjectStateValidProjectNameAndInvalidDate() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("react", Optional.of("2050-12-01T22:00:00.694"));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, requestResponse.getStatusCode(),
                "If provided date is invalid - return code must be 404");
        Assertions.assertEquals("", requestResponse.getBody(),
                "If provided date is invalid - service must return empty body");
    }

    @Test
    void findProjectStateEmptyProjectNameAndDate() {
        ResponseEntity<String> requestResponse = projectStateService.findProjectState("", Optional.of(""));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, requestResponse.getStatusCode(),
                "If nothing is provided - return code must be 404");
        Assertions.assertEquals("", requestResponse.getBody(),
                "If nothing is provided - service must return empty body");
    }

    @Test
    void saveProjectStateValidProjectState() {
        ProjectState newProjectState = new ProjectState();
        newProjectState.setProjectName("test_project_");
        newProjectState.setDateCreated(parseStringToDate("1983-01-01T22:00:00.694Z"));
        newProjectState.setLanguageStats("Some cool stats");

        ProjectState createdProjectState = projectStateService.saveProjectState(newProjectState);

        Assertions.assertEquals("test_project_", createdProjectState.getProjectName(),
                "Project name of created entity must be the same");
        Assertions.assertEquals("1983-01-01T22:00:00.694Z", createdProjectState.getDateCreated().toInstant().toString(),
                "DateCreated of created entity must be the same");
        Assertions.assertEquals("Some cool stats", createdProjectState.getLanguageStats(),
                "Language stats of created entity must be the same");

//      HERE MUST BE SOME INTELLIGENT CLEAN UP CALL, BUT THIS WON'T BE NEEDED IF SOME MOCK OR TEST DB INSTANCE IS USED
//      BUT INSTEAD YOU CAN CHECK THIS COOL PIG
//        2.                         _
//        3. _._ _..._ .-',     _.._(`))
//        4.'-. `     '  /-._.-'    ',/
//        5.   )         \            '.
//        6.  / _    _    |             \
//        7. |  a    a    /              |
//        8. \   .-.                     ;
//        9.  '-('' ).-'       ,'       ;
//        10.     '-;           |      .'
//        11.        \           \    /
//        12.        | 7  .__  _.-\   \
//        13.        | |  |  ``/  /`  /
//        14.       /,_|  |   /,_/   /
//        15.          /,_/      '`-'
    }

    @Test
    void saveProjectStateInvalidProjectState() {
        ProjectState newProjectState = new ProjectState();
        newProjectState.setProjectName(null);
        newProjectState.setDateCreated(null);
        newProjectState.setLanguageStats(null);

        projectStateService.saveProjectState(newProjectState);
    }

    private Date parseStringToDate(String date) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(date);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }
}
