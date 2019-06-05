package de.novatec.vccregistry.resource;

public class GetDataPoolsrequest {

    private String request;
    private Integer productStructureId;
    private Integer organizationId;
    private Integer countryId;

    public GetDataPoolsrequest() {
    }

    public GetDataPoolsrequest(String request, Integer productStructureId, Integer organizationId, Integer countryId) {
        this.request = request;
        this.productStructureId = productStructureId;
        this.organizationId = organizationId;
        this.countryId = countryId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getProductStructureId() {
        return productStructureId;
    }

    public void setProductStructureId(Integer productStructureId) {
        this.productStructureId = productStructureId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return "GetDataPoolsrequest{" +
                "request='" + request + '\'' +
                ", productStructureId=" + productStructureId +
                ", organizationId=" + organizationId +
                ", countryId=" + countryId +
                '}';
    }
}
