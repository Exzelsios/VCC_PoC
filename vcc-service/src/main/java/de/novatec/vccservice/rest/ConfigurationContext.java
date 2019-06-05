package de.novatec.vccservice.rest;

import de.novatec.vccservice.datapool.entity.DataPool;

public class ConfigurationContext {
    private String configurationDate;
    private int countryId;
    private int correlationId;
    private DataPool dataPool;

    public ConfigurationContext() {
    }

    public ConfigurationContext(String configurationDate, int countryId, int correlationId, DataPool dataPool) {
        this.configurationDate = configurationDate;
        this.countryId = countryId;
        this.correlationId = correlationId;
        this.dataPool = dataPool;
    }

    public String getConfigurationDate() {
        return configurationDate;
    }

    public void setConfigurationDate(String configurationDate) {
        this.configurationDate = configurationDate;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(int correlationId) {
        this.correlationId = correlationId;
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public void setDataPool(DataPool dataPool) {
        this.dataPool = dataPool;
    }

    @Override
    public String toString() {
        return "ConfigurationContext{" +
                "configurationDate='" + configurationDate + '\'' +
                ", countryId=" + countryId +
                ", correlationId=" + correlationId +
                ", dataPool=" + dataPool +
                '}';
    }
}
