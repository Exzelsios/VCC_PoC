package de.novatec.vccstatusmanager.datapoolmeta.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "datapool")
public class DataPoolMeta {

    @Id
    private Long dataVersion;
    private int payloadSize;
    private int loadDelay;

    public DataPoolMeta() {
    }

    public DataPoolMeta(Long dataVersion, int payloadSize, int loadDelay) {
        this.dataVersion = dataVersion;
        this.payloadSize = payloadSize;
        this.loadDelay = loadDelay;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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
                "dataVersion=" + dataVersion +
                ", payloadSize=" + payloadSize +
                ", loadDelay=" + loadDelay +
                '}';
    }
}
