package de.novatec.vccregistry.datapoolmeta.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "datapool")
public class DataPoolMeta {

    @Id
    private Long dataVersion;
    private int organizationId;
    private int productStructureId;

    public DataPoolMeta() {
    }

    public DataPoolMeta(int organizationId, int productStructureId) {
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
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

    @Override
    public String toString() {
        return "DataPoolMeta{" +
                "dataVersion='" + dataVersion + '\'' +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                '}';
    }
}
