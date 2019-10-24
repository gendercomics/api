package net.gendercomics.api.model;

import org.springframework.stereotype.Component;

@Component
public class BuildInfo {

    private String _applicationName;
    private String _version;

    public String getApplicationName() {
        return _applicationName;
    }

    public void setApplicationName(String applicationName) {
        _applicationName = applicationName;
    }

    public String getVersion() {
        return _version;
    }

    public void setVersion(String version) {
        _version = version;
    }

}
