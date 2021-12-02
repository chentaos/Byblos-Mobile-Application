package service;

public class ServiceRequest {
    ServiceForm serviceForm;
    boolean isPending;
    boolean isAccepted;
    String customerName;

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
