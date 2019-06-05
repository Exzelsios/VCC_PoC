package de.novatec.vccservice.datapoolstatus.entity;

public class DataPoolStatus {
    private Long dataVersion;
    private TargetStatus targetStatus;
    private BusinessAttributes businessAttributes;

    public DataPoolStatus() {
    }

    public DataPoolStatus(Long dataVersion, TargetStatus targetStatus, BusinessAttributes businessAttributes) {
        this.dataVersion = dataVersion;
        this.targetStatus = targetStatus;
        this.businessAttributes = businessAttributes;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public TargetStatus getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(TargetStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    public BusinessAttributes getBusinessAttributes() {
        return businessAttributes;
    }

    public void setBusinessAttributes(BusinessAttributes businessAttributes) {
        this.businessAttributes = businessAttributes;
    }

    @Override
    public String toString() {
        return "DataPoolStatus{" +
                "dataVersion='" + dataVersion + '\'' +
                ", targetStatus=" + targetStatus +
                ", businessAttributes=" + businessAttributes +
                '}';
    }
}
