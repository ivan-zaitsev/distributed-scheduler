package ua.ivan909020.scheduler.rest.controller.endpoint.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ua.ivan909020.scheduler.core.model.domain.page.PagedList;
import ua.ivan909020.scheduler.core.model.entity.TaskStatus;
import ua.ivan909020.scheduler.rest.model.dto.ErrorCode;
import ua.ivan909020.scheduler.rest.model.dto.ErrorResponseDto;
import ua.ivan909020.scheduler.rest.model.dto.task.TaskDto;

class TaskRestControllerTest extends ControllerTestBase {

    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 1001 })
    void findAll_shouldReturnBadRequestResponse_whenPageSizeIsNotValid(int pageSize) {
        String url = UriComponentsBuilder.fromPath("/api/v1/tasks")
                .queryParam("statuses", TaskStatus.SCHEDULED)
                .queryParam("pageSize", pageSize)
                .toUriString();

        ResponseEntity<ErrorResponseDto> response = 
                buildRestTemplate().exchange(url, GET, null, ErrorResponseDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorCode.REQUEST_PARAMETERS_NOT_VALID, response.getBody().getErrorCode());
    }

    @Test
    void findAll_shouldReturnEmptyListResponse_whenTasksNotFound() {
        String url = UriComponentsBuilder.fromPath("/api/v1/tasks")
                .queryParam("statuses", TaskStatus.SCHEDULED)
                .queryParam("pageSize", 50)
                .toUriString();

        ResponseEntity<PagedList<TaskDto>> response = 
                buildRestTemplate().exchange(url, GET, null, new ParameterizedTypeReference<PagedList<TaskDto>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new PagedList<>(List.of(), null), response.getBody());
    }

}
