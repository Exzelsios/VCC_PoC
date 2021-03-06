package de.novatec.vccclient.datapoolmeta.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "datapool")
public class DataPoolMeta {

    public interface DataPoolMetaId {
        Long getDataVersion();
    }

    @Id
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private int payloadSize;
    private int loadDelay;

    public DataPoolMeta() {
    }

    public DataPoolMeta(Long dataVersion, int organizationId, int productStructureId, int payloadSize, int loadDelay) {
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

    @Override
    public String toString() {
        return "DataPoolMeta{" +
                "dataVersion='" + dataVersion + '\'' +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                '}';
    }
}
