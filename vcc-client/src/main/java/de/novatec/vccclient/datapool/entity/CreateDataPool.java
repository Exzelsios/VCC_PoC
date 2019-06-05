package de.novatec.vccclient.datapool.entity;

public class CreateDataPool {
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private String countries;
    private int payloadSize;
    private int loadDelay;

    public CreateDataPool() {
    }

    public CreateDataPool(Long dataVersion, int organizationId, int productStructureId, String countries, int payloadSize, int loadDelay) {
        this.dataVersion = dataVersion;
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
        this.countries = countries;
        this.payloadSize = payloadSize;
        this.loadDelay = loadDelay;
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

    public int getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    public int getLoadDelay() {
        return loadDelay;
    }

    public void setLoadDelay(int loadDelay) {
        this.loadDelay = loadDelay;
    }
}
