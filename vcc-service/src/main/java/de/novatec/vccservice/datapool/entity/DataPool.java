package de.novatec.vccservice.datapool.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;

@Entity
@Table(name = "datapool")
public class DataPool {

    @Id
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private String countries;
    private String payload;
    private int loadDelay;
    private int payloadSize;

    public DataPool() {
    }

    public DataPool(int organizationId, int productStructureId, String countries, int loadDelay, int payloadSize) {
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
        this.countries = countries;
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
        return "DataPool{" +
                "dataVersion='" + dataVersion + '\'' +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                ", countries=" + countries +
                ", payload='" + payload.length() + " characters" + '\'' +
                '}';
    }
}
