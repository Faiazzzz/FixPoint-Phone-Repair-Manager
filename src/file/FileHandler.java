package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
    private String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // Method to write/append a record to file
    public void writeRecord(String record) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(record);
            pw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to read all records from file
    public String[] readAllRecords() {
        String content = "";
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                content = content + scanner.nextLine() + "\n";
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new String[0];
        }
        
        if (content.equals("")) {
            return new String[0];
        }
        
        return content.split("\n");
    }
}