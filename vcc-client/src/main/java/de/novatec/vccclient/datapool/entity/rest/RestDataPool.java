package de.novatec.vccclient.datapool.entity.rest;

public class RestDataPool {
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private String countries;
    private String payload;
    private int loadDelay;
    private int payloadSize;

    public RestDataPool() {
    }

    public RestDataPool(Long dataVersion, int organizationId, int productStructureId, String countries, String payload, int loadDelay, int payloadSize) {
        this.dataVersion = dataVersion;
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
        this.countries = countries;
        this.payload = payload;
        this.loadDelay = loadDelay;
        this.payloadSize = payloadSize;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getProductStructureId() {
        return productStructureId;
    }

    public void setProductStructureId(int productStructureId) {
        this.productStructureId = productStructureId;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public int getLoadDelay() {
        return loadDelay;
    }

    public void setLoadDelay(int loadDelay) {
        this.loadDelay = loadDelay;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    @Override
    public String toString() {
        return "RestDataPool{" +
                "dataVersion=" + dataVersion +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                ", countries='" + countries + '\'' +
                ", payload='" + payload + '\'' +
                ", loadDelay=" + loadDelay +
                ", payloadSize=" + payloadSize +
                '}';
    }
}
