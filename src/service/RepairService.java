package service;

import exception.InvalidCustomerException;
import exception.InvalidIssueTypeException;
import model.Customer;

public class RepairService implements Serviceable {
    private static int nextInvoiceId = 4;

    private static final double SCREEN_REPAIR_COST = 2500.0;
    private static final double BATTERY_REPAIR_COST = 1200.0;
    private static final double SOFTWARE_REPAIR_COST = 600.0;
    private static final double WATER_DAMAGE_REPAIR_COST = 4000.0;

    public RepairService() {
    }

    @Override
    public double calculateRepairCost(String issueType) throws InvalidIssueTypeException {
        if (issueType == null || issueType.trim().isEmpty()) {
            throw new InvalidIssueTypeException("Issue type cannot be empty.");
        }
        switch (issueType.trim().toLowerCase()) {
            case "screen":
                return SCREEN_REPAIR_COST;
            case "battery":
                return BATTERY_REPAIR_COST;
            case "software":
                return SOFTWARE_REPAIR_COST;
            case "water damage":
                return WATER_DAMAGE_REPAIR_COST;
            default:
                throw new InvalidIssueTypeException("Unknown issue type: " + issueType);
        }
    }

    @Override
    public String generateInvoice(Customer customer, double cost) throws InvalidCustomerException {
        if (customer == null) {
            throw new InvalidCustomerException("Cannot generate an invoice for a null customer.");
        }
        if (cost < 0) {
            throw new InvalidCustomerException("Repair cost cannot be negative.");
        }
        int invoiceId = nextInvoiceId++;
        return "Invoice #" + invoiceId + " | Customer: " + customer.getName()
                + " | Device: " + customer.getDeviceModel()
                + " | Issue: " + customer.getIssueDescription()
                + " | Amount Due: " + cost + " BDT";
    }

    // Overloaded generateInvoice: lets you attach an optional note
    public String generateInvoice(Customer customer, double cost, String note) throws InvalidCustomerException {
        String baseInvoice = generateInvoice(customer, cost);
        if (note == null || note.trim().isEmpty()) {
            return baseInvoice;
        }
        return baseInvoice + " | Note: " + note.trim();
    }

    @Override
    public String toString() {
        return "RepairService [Screen: " + SCREEN_REPAIR_COST +
                ", Battery: " + BATTERY_REPAIR_COST +
                ", Software: " + SOFTWARE_REPAIR_COST +
                ", Water Damage: " + WATER_DAMAGE_REPAIR_COST + "]";
    }
}