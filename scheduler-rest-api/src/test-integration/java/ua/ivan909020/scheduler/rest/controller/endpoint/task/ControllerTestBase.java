package ua.ivan909020.scheduler.rest.controller.endpoint.task;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;

import jakarta.annotation.PostConstruct;
import ua.ivan909020.scheduler.testdata.TestApplication;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestApplication.class)
public class ControllerTestBase {

    @LocalServerPort
    private int localPort;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @PostConstruct
    private void initialize() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + localPort);
    }

    public TestRestTemplate buildRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);

        restTemplateBuilder = restTemplateBuilder.requestFactory(() -> requestFactory);
        restTemplateBuilder = restTemplateBuilder.defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        return new TestRestTemplate(restTemplateBuilder);
    }

}
