package de.novatec.vccclient.datapool.entity.rest;

public class GetConfigurationContextRequest {

    private String request;
    private DataPoolIdentifier dataPoolIdentifier;
    private int countryId;
    private String configurationDate;

    public GetConfigurationContextRequest() {
    }

    public GetConfigurationContextRequest(String request, DataPoolIdentifier dataPoolIdentifier, int countryId, String configurationDate) {
        this.request = request;
        this.dataPoolIdentifier = dataPoolIdentifier;
        this.countryId = countryId;
        this.configurationDate = configurationDate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public DataPoolIdentifier getDataPoolIdentifier() {
        return dataPoolIdentifier;
    }

    public void setDataPoolIdentifier(DataPoolIdentifier dataPoolIdentifier) {
        this.dataPoolIdentifier = dataPoolIdentifier;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getConfigurationDate() {
        return configurationDate;
    }

    public void setConfigurationDate(String configurationDate) {
        this.configurationDate = configurationDate;
    }

    @Override
    public String toString() {
        return "GetConfigurationContextRequest{" +
                "request='" + request + '\'' +
                ", dataPoolIdentifier=" + dataPoolIdentifier +
                ", countryId=" + countryId +
                ", configurationDate='" + configurationDate + '\'' +
                '}';
    }
}
