# FixPoint: Phone Repair Management System
 
A Java Swing desktop application for managing a phone repair shop. It registers customers and their devices, automatically assigns a technician based on the type of issue, generates invoices, and lets an admin view all records and total revenue. Data is stored in plain text files, so no database or external libraries are needed.
 
> Built as a project for my **Object-Oriented Programming (Java)** course at North South University.
 
---
 
## Table of Contents
 
- [Features](#features)
- [Why I Built This](#why-i-built-this)
- [OOP Concepts Demonstrated](#oop-concepts-demonstrated)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [How It Works](#how-it-works)
- [Data Files](#data-files)
- [Limitations and Future Improvements](#limitations-and-future-improvements)
---
 
## Features
 
- **Register a customer**: enter name, phone number, device model, and issue type through a form.
- **Automatic technician assignment**: the app reads `data/technicians.txt` and picks the technician whose specialization matches the issue.
- **Automatic pricing and invoicing**: repair cost is calculated from the issue type and an invoice line is generated and saved.
- **View technicians**: browse the technician list in a separate window.
- **View all repair records**: see every saved invoice.
- **Calculate total revenue**: the admin role parses all saved invoices and sums the amounts due.
- **Input validation**: empty fields, unknown issue types, and missing technicians show clear error messages instead of crashing.
### Supported issue types and prices
 
| Issue type     | Cost (BDT) |
|----------------|-----------:|
| `screen`       | 2500.0     |
| `battery`      | 1200.0     |
| `software`     | 600.0      |
| `water damage` | 4000.0     |
 
---
 
## Why I Built This
 
This project was my assignment for the Object-Oriented Programming course. The goal was to take the concepts we learned in lectures (inheritance, abstraction, interfaces, polymorphism, exception handling, and file I/O) and apply them together in one working program instead of in isolated exercises.
 
I chose a **phone repair shop** because it is a small, realistic domain that maps naturally onto OOP:
 
- There are different kinds of people (customers, technicians, admins) who share common data but behave differently, which is a natural fit for inheritance.
- Pricing and invoicing are a "service" that can be defined by an interface and implemented by a class.
- Many things can go wrong (empty input, an unknown issue type, no technician available), which gave me real reasons to write custom exceptions.
- Records need to survive after the program closes, so file handling had a clear purpose.
### How I approached it
 
1. **Designed first.** I started with a UML class diagram covering the `model`, `service`, `exception`, and `ui` packages before writing code.
2. **Built the model layer.** An abstract `Person` class, with `Customer`, `Technician`, and `Admin` extending it.
3. **Added the service layer.** A `Serviceable` interface and a `RepairService` class that implements it.
4. **Wrote custom exceptions** for each failure case so errors are specific and meaningful.
5. **Added file handling** in a reusable `FileHandler` class used by both the technician lookup and the revenue calculation.
6. **Finished with the GUI.** A Swing-based `MainFrame` ties everything together.
Keeping the UI as the last step meant the logic was already working and testable before I connected it to any buttons.
 
---
 
## OOP Concepts Demonstrated
 
| Concept | Where it appears |
|---------|------------------|
| **Abstraction** | `Person` is an abstract class with an abstract method `getRole()` |
| **Inheritance** | `Customer`, `Technician`, and `Admin` all extend `Person` |
| **Polymorphism / method overriding** | Each subclass overrides `getRole()` and `toString()` |
| **Interfaces** | `Serviceable` defines `calculateRepairCost()` and `generateInvoice()`; `RepairService` implements it |
| **Method overloading** | `RepairService.generateInvoice(customer, cost)` and `generateInvoice(customer, cost, note)` |
| **Encapsulation** | Private fields with public getters and setters throughout the model classes |
| **Custom exceptions** | `InvalidInputException`, `InvalidCustomerException`, `InvalidIssueTypeException`, `InvalidTechnicianException` |
| **Exception handling** | Multi-catch in `MainFrame` turns exceptions into friendly error dialogs |
| **File I/O** | `FileHandler` reads and appends records using `Scanner`, `FileWriter`, and `PrintWriter` |
| **Static methods** | `Technician.assignTechnician()` selects a technician from file data |
| **Packages** | Code is split into `model`, `service`, `exception`, `file`, and `ui` |
| **GUI programming** | Swing components with event listeners (`JFrame`, `JOptionPane`, `JTextArea`, `JScrollPane`) |
 
---
 
## Project Structure
 
```
FixPoint/
├── data/
│   ├── repairs.txt          # Saved invoices (appended to at runtime)
│   └── technicians.txt      # Technician list: name,id,phone,specialization
├── src/
│   ├── Main.java            # Entry point
│   ├── exception/
│   │   ├── InvalidCustomerException.java
│   │   ├── InvalidInputException.java
│   │   ├── InvalidIssueTypeException.java
│   │   └── InvalidTechnicianException.java
│   ├── file/
│   │   └── FileHandler.java
│   ├── model/
│   │   ├── Person.java      # Abstract base class
│   │   ├── Customer.java
│   │   ├── Technician.java
│   │   └── Admin.java
│   ├── service/
│   │   ├── Serviceable.java # Interface
│   │   └── RepairService.java
│   └── ui/
│       └── MainFrame.java   # Swing GUI
└── README.md
```
 
---
 
## How to Run
 
### Requirements
 
- Java Development Kit (JDK) 8 or newer
### From the command line
 
The program reads `data/repairs.txt` and `data/technicians.txt` using relative paths, so **run it from the project root** (the folder that contains `data/` and `src/`).
 
**Linux / macOS**
 
```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
java -cp bin Main
```
 
**Windows (Command Prompt)**
 
```cmd
mkdir bin
dir /s /b src\*.java > sources.txt
javac -d bin @sources.txt
java -cp bin Main
```
 
### From an IDE (Eclipse / IntelliJ / VS Code)
 
1. Import or open the project folder.
2. Mark `src` as the source folder.
3. Run `Main.java`. Make sure the working directory is the project root.
---
 
## How It Works
 
1. `Main` launches `MainFrame` on the Swing event thread.
2. When you click **Register Customer**, `MainFrame` validates the form input and creates a `Customer` object.
3. `Technician.assignTechnician()` reads `technicians.txt` and returns the technician whose specialization matches the issue type.
4. `RepairService` calculates the cost and generates the invoice string.
5. `FileHandler` appends the invoice and technician name to `repairs.txt`.
6. **Calculate Total Revenue** makes `Admin` read `repairs.txt`, extract each "Amount Due" value, and add them up.
If anything goes wrong along the way, one of the custom exceptions is thrown and shown to the user in an error dialog.
 
---
 
## Data Files
 
**`data/technicians.txt`** uses one comma-separated technician per line:
 
```
name,id,phone,specialization
```
 
Example:
 
```
Mike Smith,101,01711111111,screen
```
 
**`data/repairs.txt`** stores one invoice per line, in this format:
 
```
Invoice #<id> | Customer: <name> | Device: <model> | Issue: <type> | Amount Due: <cost> BDT | Technician: <name>
```
 
All names and phone numbers in the sample data are fictional.
 
---
 
## Limitations and Future Improvements
 
This is a learning project, so there are some known limits I would improve with more time:
 
- **Counters reset on restart.** Customer and invoice numbers are held in memory, so they restart each time the app launches. Persisting the last used ID would fix this.
- **One technician per specialization.** The app picks the first matching technician. A better version would balance workload or check availability.
- **No customer search or status tracking.** Adding repair statuses (pending, in progress, completed) and a search feature would make it more useful.
- **Plain text storage.** Moving to a database such as SQLite would be more robust than text files.
- **Limited admin tools.** There is no login, and admin actions are available to anyone using the app.
- **No automated tests.** Unit tests for `RepairService` and `FileHandler` would be a good next step.
---
