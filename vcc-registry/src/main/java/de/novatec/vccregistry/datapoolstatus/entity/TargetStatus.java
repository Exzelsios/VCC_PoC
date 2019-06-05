package de.novatec.vccregistry.datapoolstatus.entity;

public class TargetStatus {
    private Long dataPoolId;
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
