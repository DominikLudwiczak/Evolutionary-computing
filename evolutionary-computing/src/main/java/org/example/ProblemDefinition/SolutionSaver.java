package org.example.ProblemDefinition;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SolutionSaver {
    public static void CreateFile(String fileName, int assignmentNumber) {
        File file = new File(String.format("outputs/%d/%s", assignmentNumber, fileName));

        if (!file.exists()) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                String[] header = {"Name", "Instance", "Solution", "Distance"};
                writer.writeNext(header);
                System.out.println("CSV file created with headers: Name, Instance, Solution, Distance.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("CSV file already exists.");
        }
    }

    public static void SaveSolution(String fileName, String name,
                                    String instance, String solution,
                                    int distance, int assignmentNumber) {
        File file = new File(String.format("outputs/%d/%s", assignmentNumber, fileName));

        if (!file.exists()) {
            CreateFile(fileName, assignmentNumber);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
            String[] data = {name, instance, solution, Integer.toString(distance)};
            writer.writeNext(data);
            System.out.println("Solution saved to CSV file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveFile(String fileName, int assignmentNumber) {
        File file = new File(String.format("outputs/%d/%s", assignmentNumber, fileName));
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }
}