package de.novatec.vccclient.datapool.entity.rest;

public class DataPoolIdentifier {
    private Long dataVersion;

    public DataPoolIdentifier() {
    }

    public DataPoolIdentifier(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }
}
