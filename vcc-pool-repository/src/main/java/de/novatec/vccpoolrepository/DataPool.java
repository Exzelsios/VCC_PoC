package de.novatec.vccpoolrepository;

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
    private int[] countries;
    private int payloadSize;
    private int loadDelay;

    public DataPool() {
    }

    public DataPool(Long dataVersion, int organizationId, int productStructureId, int[] countries, int payloadSize, int loadDelay) {
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

    public int[] getCountries() {
        return countries;
    }

    public void setCountries(int[] countries) {
        this.countries = countries;
    }

    public String getCountriesLabel() {
        return Arrays.toString(countries);
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

    @Override
    public String toString() {
        return "DataPool{" +
                "dataVersion=" + dataVersion +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                ", countries=" + Arrays.toString(countries) +
                ", payloadSize=" + payloadSize +
                ", loadDelay=" + loadDelay +
                '}';
    }
}
