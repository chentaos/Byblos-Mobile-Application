package service;

public class ServiceRequest {
    ServiceForm serviceForm;
    boolean isPending;
    boolean isAccepted;
    String customerName;
    String key;
    String parentId;
    String service;

    public ServiceRequest(){

    }

    public ServiceRequest(ServiceForm serviceForm, boolean isPending, boolean isAccepted, String customerName) {
        this.serviceForm = serviceForm;
        this.isPending = isPending;
        this.isAccepted = isAccepted;
        this.customerName = customerName;
    }

    public ServiceForm getServiceForm() {
        return serviceForm;
    }

    public void setServiceForm(ServiceForm serviceForm) {
        this.serviceForm = serviceForm;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "serviceForm=" + serviceForm +
                ", isPending=" + isPending +
                ", isAccepted=" + isAccepted +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
