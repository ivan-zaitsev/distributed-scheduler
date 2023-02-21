package ua.ivan909020.scheduler.core.testdata;

import java.net.URI;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;

public class ServiceInstanceMock implements ServiceInstance {

    private String instanceId;

    public ServiceInstanceMock(String instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public String getInstanceId() {
        return instanceId;
    }

    @Override
    public String getServiceId() {
        return null;
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public Map<String, String> getMetadata() {
        return null;
    }

}
