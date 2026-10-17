package model;

import file.FileHandler;
import exception.InvalidTechnicianException;

public class Technician extends Person {

	private String specialization;
	private static final String TECHNICIAN_FILE = "data/technicians.txt";

	public Technician(String name, int id, String phoneNumber, String specialization ) {
		super(name, id, phoneNumber);
		this.specialization = specialization;
	}

	public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

	@Override
	public String getRole() {
		return "Technician";
	}

	@Override
	public String toString() {
		return super.toString() +
				", Specialization: " + specialization;
	}

	// Auto-selects a technician from technicians.txt based on the issue type
	public static Technician assignTechnician(String issueType) throws InvalidTechnicianException {
		if (issueType == null || issueType.trim().isEmpty()) {
			throw new InvalidTechnicianException("Issue type cannot be empty when assigning a technician.");
		}

		FileHandler fileHandler = new FileHandler(TECHNICIAN_FILE);
		String[] records = fileHandler.readAllRecords();

		for (String record : records) {
			String[] parts = record.split(",");
			if (parts.length != 4) {
				continue;
			}

			String name = parts[0].trim();
			int id;
			try {
				id = Integer.parseInt(parts[1].trim());
			} catch (NumberFormatException e) {
				continue;
			}
			String phoneNumber = parts[2].trim();
			String specialization = parts[3].trim();

			if (specialization.equalsIgnoreCase(issueType.trim())) {
				return new Technician(name, id, phoneNumber, specialization);
			}
		}

		throw new InvalidTechnicianException("No technician available for issue type: " + issueType);
	}
}