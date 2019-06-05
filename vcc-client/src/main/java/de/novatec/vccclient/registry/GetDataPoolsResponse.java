package de.novatec.vccclient.registry;

import java.util.List;

public class GetDataPoolsResponse {

    private final String response = "GetDataPools";
    private String serverVersion;
    private List<RegistryDataPool> dataPools;

    public GetDataPoolsResponse() {
    }

    public GetDataPoolsResponse(String serverVersion, List<RegistryDataPool> dataPools) {
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

    public List<RegistryDataPool> getDataPools() {
        return dataPools;
    }

    public void setDataPools(List<RegistryDataPool> dataPools) {
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
