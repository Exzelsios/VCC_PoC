package de.novatec.vccstatusrepository.entity;

import javax.persistence.*;

@Entity
@Table(name = "datapoolstatus")
public class DataPoolStatus {
    @Id
    private Long dataVersion;

    @OneToOne(cascade = CascadeType.ALL)
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
