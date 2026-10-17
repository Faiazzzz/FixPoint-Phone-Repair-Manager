package service;

import exception.InvalidCustomerException;

import exception.InvalidIssueTypeException;
import model.Customer;

public interface Serviceable {
    double calculateRepairCost(String issueType) throws InvalidIssueTypeException;

    String generateInvoice(Customer customer, double cost) throws InvalidCustomerException;
}