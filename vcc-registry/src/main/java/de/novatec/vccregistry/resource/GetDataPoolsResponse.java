package de.novatec.vccregistry.resource;

import de.novatec.vccregistry.DataPool;

import java.util.List;

public class GetDataPoolsResponse {

    private final String response = "GetDataPools";
    private String serverVersion;
    private List<DataPool> dataPools;

    public GetDataPoolsResponse() {
    }

    public GetDataPoolsResponse(String serverVersion, List<DataPool> dataPools) {
        this.serverVersion = serverVersion;
        this.dataPools = dataPools;
    }

    public String getResponse() {
        return response;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public List<DataPool> getDataPools() {
        return dataPools;
    }

    public void setDataPools(List<DataPool> dataPools) {
        this.dataPools = dataPools;
    }

    @Override
    public String toString() {
        return "GetDataPoolsResponse{" +
                "response='" + response + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", dataPools=" + dataPools +
                '}';
    }
}
