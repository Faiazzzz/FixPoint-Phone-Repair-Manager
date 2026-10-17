package ui;

import exception.InvalidCustomerException;
import exception.InvalidInputException;
import exception.InvalidIssueTypeException;
import exception.InvalidTechnicianException;
import file.FileHandler;
import model.Admin;
import model.Customer;
import model.Technician;
import service.RepairService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private int nextCustomerId = 100;

    private final RepairService repairService = new RepairService();
    private final FileHandler repairFileHandler = new FileHandler("data/repairs.txt");
    private final FileHandler technicianFileHandler = new FileHandler("data/technicians.txt");
    private final Admin admin = new Admin("System Admin", 1, "0000000000", "Full");

    public MainFrame() {
        setTitle("FixPoint - Phone Repair Management System");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 5, 5));

        JButton registerCustomerBtn = new JButton("Register Customer");
        JButton viewTechniciansBtn = new JButton("View Technicians");
        JButton viewRecordsBtn = new JButton("View All Records");
        JButton totalRevenueBtn = new JButton("Calculate Total Revenue");
        JButton exitBtn = new JButton("Exit");

        registerCustomerBtn.addActionListener(this::registerCustomer);
        viewTechniciansBtn.addActionListener(this::viewTechnicians);
        viewRecordsBtn.addActionListener(this::viewAllRecords);
        totalRevenueBtn.addActionListener(this::calculateTotalRevenue);
        exitBtn.addActionListener(e -> System.exit(0));

        add(registerCustomerBtn);
        add(viewTechniciansBtn);
        add(viewRecordsBtn);
        add(totalRevenueBtn);
        add(exitBtn);
    }

    private void registerCustomer(ActionEvent e) {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField deviceField = new JTextField();
        JTextField issueField = new JTextField();

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(new JLabel("Customer name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Device model:"));
        formPanel.add(deviceField);
        formPanel.add(new JLabel("Issue type (screen/battery/software/water damage):"));
        formPanel.add(issueField);

        int result = JOptionPane.showConfirmDialog(this, formPanel, "Register Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            String name = nameField.getText();
            if (name == null || name.trim().isEmpty()) {
                throw new InvalidInputException("Customer name cannot be empty.");
            }

            String phone = phoneField.getText();
            if (phone == null || phone.trim().isEmpty()) {
                throw new InvalidInputException("Phone number cannot be empty.");
            }

            String device = deviceField.getText();
            if (device == null || device.trim().isEmpty()) {
                throw new InvalidInputException("Device model cannot be empty.");
            }

            String issue = issueField.getText();
            if (issue == null || issue.trim().isEmpty()) {
                throw new InvalidInputException("Issue type cannot be empty.");
            }

            Customer customer = new Customer(name.trim(), nextCustomerId++, phone.trim(),
                    device.trim(), issue.trim(), false);

            // Auto-assign a technician based on issue type (reads data/technicians.txt)
            Technician technician = Technician.assignTechnician(issue.trim());

            double cost = repairService.calculateRepairCost(issue.trim());
            String invoice = repairService.generateInvoice(customer, cost);

            String record = invoice + " | Technician: " + technician.getName();
            repairFileHandler.writeRecord(record);

            JOptionPane.showMessageDialog(this, "Registered:\n" + record);

        } catch (InvalidInputException | InvalidCustomerException
                 | InvalidIssueTypeException | InvalidTechnicianException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewTechnicians(ActionEvent e) {
        String[] records = technicianFileHandler.readAllRecords();
        showRecordsWindow("Technicians", records);
    }

    private void viewAllRecords(ActionEvent e) {
        String[] records = repairFileHandler.readAllRecords();
        showRecordsWindow("All Repair Records", records);
    }

    private void calculateTotalRevenue(ActionEvent e) {
        double total = admin.calculateTotalRevenue();
        JOptionPane.showMessageDialog(this, "Total Revenue: " + total + " BDT",
                "Total Revenue", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRecordsWindow(String title, String[] records) {
        StringBuilder allRecords = new StringBuilder();
        if (records.length == 0) {
            allRecords.append("No records found.");
        } else {
            for (String record : records) {
                allRecords.append(record).append("\n");
            }
        }

        JTextArea textArea = new JTextArea(allRecords.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 300));

        JFrame recordsFrame = new JFrame(title);
        recordsFrame.setSize(500, 400);
        recordsFrame.setLocationRelativeTo(this);
        recordsFrame.setLayout(new BorderLayout());
        recordsFrame.add(scrollPane, BorderLayout.CENTER);
        recordsFrame.setVisible(true);
    }
}