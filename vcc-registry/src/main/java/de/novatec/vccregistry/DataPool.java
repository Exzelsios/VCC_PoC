package de.novatec.vccregistry;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DataPool {

    private int[] countryIds;
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;

    public DataPool() {
    }

    public DataPool(Long dataVersion, int organizationId, int productStructureId, int[] countryIds) {
        this.countryIds = countryIds;
        this.dataVersion = dataVersion;
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
    }

    public int[] getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(int[] countryIds) {
        this.countryIds = countryIds;
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

    public boolean containsCountryId(int country) {
        return IntStream.of(countryIds).anyMatch(x -> x == country);
    }

    @Override
    public String toString() {
        return "DataPool{" +
                "countryIds=" + Arrays.toString(countryIds) +
                ", dataVersion=" + dataVersion +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                '}';
    }
}
