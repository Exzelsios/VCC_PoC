package de.novatec.vccclient.datapool.entity;

public class DebugPod {
    private String id;
    private String internalEndpoint;
    private String status;
    private String image;

    public DebugPod() {
    }

    public DebugPod(String id, String internalEndpoint, String status, String image) {
        this.id = id;
        this.internalEndpoint = internalEndpoint;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalEndpoint() {
        return internalEndpoint;
    }

    public void setInternalEndpoint(String internalEndpoint) {
        this.internalEndpoint = internalEndpoint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "DebugPod{" +
                "id='" + id + '\'' +
                ", internalEndpoint='" + internalEndpoint + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
