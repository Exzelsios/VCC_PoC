package de.novatec.vccservice;

public class ApplicationProperties {
    private String appVersion;
    private boolean cpuOverload;

    public ApplicationProperties(String appVersion, boolean cpuOverload) {
        this.appVersion = appVersion;
        this.cpuOverload = cpuOverload;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isCpuOverload() {
        return cpuOverload;
    }

    public void setCpuOverload(boolean cpuOverload) {
        this.cpuOverload = cpuOverload;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "appVersion='" + appVersion + '\'' +
                ", cpuOverload=" + cpuOverload +
                '}';
    }
}
