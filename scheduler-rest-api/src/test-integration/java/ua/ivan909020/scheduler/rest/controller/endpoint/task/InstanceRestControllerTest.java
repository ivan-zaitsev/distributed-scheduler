package ua.ivan909020.scheduler.rest.controller.endpoint.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceDto;

class InstanceRestControllerTest extends ControllerTestBase {

    @Test
    void findAll_shouldReturnEmptyListResponse_whenInstancesNotFound() {
        String url = "/api/v1/instances";

        ResponseEntity<List<InstanceDto>> response = 
                buildRestTemplate().exchange(url, GET, null, new ParameterizedTypeReference<List<InstanceDto>>() {});

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(), response.getBody());
    }

}
