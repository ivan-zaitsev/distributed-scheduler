package ua.ivan909020.scheduler.testdata;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.ivan909020.scheduler.core.model.domain.instance.Instance;
import ua.ivan909020.scheduler.core.service.discovery.InstanceRegistry;

@SpringBootApplication(scanBasePackages = "ua.ivan909020.scheduler")
public class TestApplication {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public InstanceRegistry instanceRegistry() {
        return new InstanceRegistry() {

            @Override
            public Instance getCurrentInstance() {
                return null;
            }

            @Override
            public void updateCurrentInstanceMetadata(Map<String, String> metadata) {

            }

            @Override
            public List<Instance> getAllInstances() {
                return List.of();
            }

        };
    }

}
