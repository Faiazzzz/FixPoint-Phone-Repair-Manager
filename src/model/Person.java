package model;

public abstract class Person {
	private String name;
	private int id;
	private String phoneNumber;
	
	public Person(String name, int id, String phoneNumber) {
		this.name = name;
		this.id  = id;
		this.phoneNumber  = phoneNumber;

	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public abstract String getRole();
	@Override
	public String toString() {
		return "ID: " + id +
				", Name: " + name + 
				", Phone: " + phoneNumber + 
				", Role: " + getRole();
	}
}
