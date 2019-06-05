package de.novatec.vccservice.rest;

public class GetConfigurationContextResponse {

    private String response;
    private String serverVersion;
    private ConfigurationContext configurationContext;

    public GetConfigurationContextResponse() {
    }

    public GetConfigurationContextResponse(String response, String serverVersion, ConfigurationContext configurationContext) {
        this.response = response;
        this.serverVersion = serverVersion;
        this.configurationContext = configurationContext;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public ConfigurationContext getConfigurationContext() {
        return configurationContext;
    }

    public void setConfigurationContext(ConfigurationContext configurationContext) {
        this.configurationContext = configurationContext;
    }

    @Override
    public String toString() {
        return "GetConfigurationContextResponse{" +
                "response='" + response + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", configurationContext=" + configurationContext +
                '}';
    }
}
