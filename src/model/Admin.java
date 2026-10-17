package model;

import file.FileHandler;

public class Admin extends Person {

	private String accessLevel;
	
	private static final String REPAIRS_FILE = "data/repairs.txt";

	public Admin(String name, int id, String phoneNumber, String accessLevel) {
		super(name, id, phoneNumber);
		this.accessLevel = accessLevel;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	//public void viewAllRecords()

	// Reads every repair record from the repairs file and sums the "Amount Due" values
	public double calculateTotalRevenue() {
		FileHandler fileHandler = new FileHandler(REPAIRS_FILE);
		String[] records = fileHandler.readAllRecords();
		double totalRevenue = 0.0;

		for (String record : records) {
			String[] fields = record.split("\\|");
			for (String field : fields) {
				field = field.trim();
				if (field.startsWith("Amount Due:")) {
					String amountStr = field.substring("Amount Due:".length()).trim();
					amountStr = amountStr.replace("BDT", "").trim();
					try {
						totalRevenue += Double.parseDouble(amountStr);
					} catch (NumberFormatException e) {
						System.out.println("Skipping record with invalid amount: " + record);
					}
				}
			}
		}
		return totalRevenue;
	}

	@Override
	public String getRole() {
		return "Admin";
	}
	@Override
	public String toString() {
		return super.toString() +
				", Access Level: " + accessLevel;
	}

}