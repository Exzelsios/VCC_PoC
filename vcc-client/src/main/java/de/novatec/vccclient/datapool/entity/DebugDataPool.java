package de.novatec.vccclient.datapool.entity;

import java.util.ArrayList;
import java.util.List;

public class DebugDataPool {
    private Long dataVersion;
    private String deploymentName;
    private String image;
    private String lastScaleTime;
    private Integer desiredReplicas;
    private Integer actualReplicas;
    private Integer currentCpuUtilization;
    private List<DebugPod> pods = new ArrayList<>();

    public DebugDataPool() {
    }

    public DebugDataPool(Long dataVersion, String deploymentName, String image, String lastScaleTime, Integer desiredReplicas, Integer actualReplicas, Integer currentCpuUtilization, List<DebugPod> pods) {
        this.dataVersion = dataVersion;
        this.deploymentName = deploymentName;
        this.image = image;
        this.lastScaleTime = lastScaleTime;
        this.desiredReplicas = desiredReplicas;
        this.actualReplicas = actualReplicas;
        this.pods = pods;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDesiredReplicas() {
        return desiredReplicas;
    }

    public void setDesiredReplicas(Integer desiredReplicas) {
        this.desiredReplicas = desiredReplicas;
    }

    public Integer getActualReplicas() {
        return actualReplicas;
    }

    public void setActualReplicas(Integer actualReplicas) {
        this.actualReplicas = actualReplicas;
    }

    public List<DebugPod> getPods() {
        return pods;
    }

    public void setPods(List<DebugPod> pods) {
        this.pods = pods;
    }

    public Integer getCurrentCpuUtilization() {
        return currentCpuUtilization;
    }

    public void setCurrentCpuUtilization(Integer currentCpuUtilization) {
        this.currentCpuUtilization = currentCpuUtilization;
    }

    public String getLastScaleTime() {
        return lastScaleTime;
    }

    public void setLastScaleTime(String lastScaleTime) {
        this.lastScaleTime = lastScaleTime;
    }

    @Override
    public String toString() {
        return "DebugDataPool{" +
                "dataVersion=" + dataVersion +
                ", deploymentName='" + deploymentName + '\'' +
                ", image='" + image + '\'' +
                ", lastScaleTime='" + lastScaleTime + '\'' +
                ", desiredReplicas=" + desiredReplicas +
                ", actualReplicas=" + actualReplicas +
                ", currentCpuUtilization=" + currentCpuUtilization +
                ", pods=" + pods +
                '}';
    }
}
