package de.novatec.vccstatusrepository.entity;

import javax.persistence.*;

@Entity
@Table(name = "targetstatus")
public class TargetStatus {
    @Id
    private Long dataPoolId;

    @Enumerated(EnumType.STRING)
    private LoadStatus targetStatus;

    public TargetStatus() {
    }

    public TargetStatus(Long dataPoolId, LoadStatus targetStatus) {
        this.dataPoolId = dataPoolId;
        this.targetStatus = targetStatus;
    }

    public Long getDataPoolId() {
        return dataPoolId;
    }

    public void setDataPoolId(Long dataPoolId) {
        this.dataPoolId = dataPoolId;
    }

    public LoadStatus getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(LoadStatus targetStatus) {
        this.targetStatus = targetStatus;
    }

    @Override
    public String toString() {
        return "TargetStatus{" +
                "dataPoolId=" + dataPoolId +
                ", targetStatus=" + targetStatus +
                '}';
    }
}
