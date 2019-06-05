package de.novatec.vccadmin.debug;

public class ServiceProperties {
    private String appVersion;
    private boolean cpuOverload;

    public ServiceProperties() {
    }

    public ServiceProperties(String appVersion, boolean cpuOverload) {
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
        return "ServiceProperties{" +
                "appVersion='" + appVersion + '\'' +
                ", cpuOverload=" + cpuOverload +
                '}';
    }
}
