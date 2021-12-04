package service;

public class ServiceRequest {
    ServiceForm serviceForm;
    boolean isPending;
    boolean isAccepted;
    String customerName;
    String key;
    String parentId;
    String service;
    boolean rated;
    int currentRate;

    public ServiceRequest(){

    }

    public ServiceRequest(ServiceForm serviceForm, boolean isPending, boolean isAccepted, String customerName, boolean rated, int currentRate) {
        this.serviceForm = serviceForm;
        this.isPending = isPending;
        this.isAccepted = isAccepted;
        this.customerName = customerName;
        this.rated = rated;
        this.currentRate = currentRate;
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

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public int getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(int currentRate) {
        this.currentRate = currentRate;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "serviceForm=" + serviceForm +
                ", isPending=" + isPending +
                ", isAccepted=" + isAccepted +
                ", customerName='" + customerName + '\'' +
                ", service='" + service + '\'' +
                ", rated=" + rated +
                ", currentRate=" + currentRate +
                '}';
    }
}
