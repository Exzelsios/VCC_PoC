package de.novatec.vccadmin.datapoolstatus.entity;

public class DataPoolStatus {
    private Long dataVersion;
    private BusinessAttributes businessAttributes;

    public DataPoolStatus() {
    }

    public DataPoolStatus(BusinessAttributes businessAttributes) {
        this.businessAttributes = businessAttributes;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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
                ", businessAttributes=" + businessAttributes +
                '}';
    }
}
