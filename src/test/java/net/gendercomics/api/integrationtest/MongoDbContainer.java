package net.gendercomics.api.integrationtest;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    private static final int PORT = 27017;

    public MongoDbContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.addExposedPort(PORT);
    }

    public String getUri() {
        final String ip = this.getContainerIpAddress();
        final Integer port = this.getMappedPort(PORT);
        return String.format("mongodb://%s:%s/test", ip, port);
    }

    @Override
    public void stop() {
        // let the JVM handle the shutdown
    }

}
