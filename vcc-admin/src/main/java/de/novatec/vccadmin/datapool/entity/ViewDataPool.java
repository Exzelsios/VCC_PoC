package de.novatec.vccadmin.datapool.entity;

import de.novatec.vccadmin.datapoolstatus.entity.BusinessAttributes;
import de.novatec.vccadmin.datapoolstatus.entity.LoadStatus;
import de.novatec.vccadmin.datapoolstatus.entity.TargetStatus;

public class ViewDataPool {

    private Long dataVersion;
    private int organizationId;
    private int productStructureId;
    private int payloadSize;
    private int loadDelay;
    private String targetStatus;
    private String status;
    private BusinessAttributes businessAttributes;

    public ViewDataPool() {
    }

    public ViewDataPool(Long dataVersion, int organizationId, int productStructureId, int payloadSize, int loadDelay, LoadStatus targetStatus, LoadStatus status, BusinessAttributes businessAttributes) {
        this.dataVersion = dataVersion;
        this.organizationId = organizationId;
        this.productStructureId = productStructureId;
        this.payloadSize = payloadSize;
        this.loadDelay = loadDelay;
        this.targetStatus = targetStatus == LoadStatus.LOADED ? "LOAD" : "UNLOAD";
        this.status = status == LoadStatus.LOADED ? "LOADED" : "UNLOADED";
        this.businessAttributes = businessAttributes;
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

    public String getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus) {
        this.targetStatus = targetStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BusinessAttributes getBusinessAttributes() {
        return businessAttributes;
    }

    public void setBusinessAttributes(BusinessAttributes businessAttributes) {
        this.businessAttributes = businessAttributes;
    }

    public String getCountriesLabel() {
        return businessAttributes != null ? businessAttributes.getCountriesAsString() : "null";
    }

    @Override
    public String toString() {
        return "ViewDataPool{" +
                "dataVersion=" + dataVersion +
                ", organizationId=" + organizationId +
                ", productStructureId=" + productStructureId +
                ", payloadSize=" + payloadSize +
                ", loadDelay=" + loadDelay +
                ", targetStatus=" + targetStatus +
                ", status=" + status +
                ", businessAttributes=" + businessAttributes +
                '}';
    }
}
