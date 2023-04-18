package ua.ivan909020.scheduler.rest.controller.endpoint.instance;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.ivan909020.scheduler.rest.model.dto.instance.InstanceDto;
import ua.ivan909020.scheduler.rest.service.instance.InstanceService;

@RestController
@RequestMapping("/api")
public class InstanceRestController {

    private final InstanceService instanceService;

    public InstanceRestController(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @GetMapping("/v1/instances")
    public List<InstanceDto> findAll() {
        return instanceService.findAll();
    }

}
