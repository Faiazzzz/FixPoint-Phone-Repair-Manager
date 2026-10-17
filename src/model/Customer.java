package model;

public class Customer extends Person {
	
	private String deviceModel;
	private String issueDescription;
	private boolean subscriptionActive;
	
	public Customer(String name, int id, String phoneNumber, String deviceModel, String issueDescription, boolean subscriptionActive ) {
		super(name, id, phoneNumber);
		this.deviceModel  = deviceModel;
		this.issueDescription = issueDescription;
		this.subscriptionActive = subscriptionActive;
		
	}
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public boolean isSubscriptionActive() {
        return subscriptionActive;
    }

    public void setSubscriptionActive(boolean subscriptionActive) {
        this.subscriptionActive = subscriptionActive;
    }
	@Override
	public String getRole() {
		return "Customer";
	}
	@Override
	public String toString() {
		return super.toString() + 
				", Device: " + deviceModel +
				", Issue: " + issueDescription +
				", Subscription: "+ (subscriptionActive? "Active" : "Inactive");
				
		}

}
