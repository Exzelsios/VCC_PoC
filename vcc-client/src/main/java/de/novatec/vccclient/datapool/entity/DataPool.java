package de.novatec.vccclient.datapool.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "datapool")
public class DataPool {

    @Id
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private int payloadSize;
    private String countries;
    private int loadDelay;

    public DataPool() {
    }

    public DataPool(Long dataVersion, int organizationId, int productStructureId, String countries, int payloadSize, int loadDelay) {
        this.dataVersion = dataVersion;
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
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

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "ViewDataPool{" +
                "dataVersion=" + dataVersion +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                ", payloadSize=" + payloadSize +
                ", countries='" + countries + '\'' +
                ", loadDelay=" + loadDelay +
                '}';
    }
}
