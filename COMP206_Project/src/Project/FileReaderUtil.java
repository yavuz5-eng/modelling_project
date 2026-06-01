package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReaderUtil {

    // Reads packageData.txt and loads packages into Master Registry, Intake Buffer, and AVL Tree
    public static void readPackageData(
            String fileName,
            SinglyLinkedList masterRegistry,
            DoublyLinkedList intakeBuffer,
            AVLTree addressDirectory
    ) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int customerCounter = 1;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip empty lines and comment lines
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length < 2) {
                    continue;
                }

                String packageID = parts[0];
                String destination = parts[1];

                Package pkg = new Package(packageID, destination);

                // Step 1: Every incoming package is recorded in the daily log
                masterRegistry.addRecord(pkg);

                // Step 2: Package waits in the intake buffer
                intakeBuffer.insertAtTail(pkg);

                // Step 3: Destination is added to the address directory
                String customerID = "CUS_" + String.format("%03d", customerCounter);
                addressDirectory.insert(destination, customerID);

                customerCounter++;
            }

            scanner.close();

            System.out.println("Package data loaded successfully from " + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + fileName + " was not found.");
        }
    }

    // Reads mapData.txt and loads roads into the graph
    public static void readMapData(String fileName, Graph cityMap) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip empty lines and comment lines
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("\\s+");

                if (parts.length < 3) {
                    continue;
                }

                String source = parts[0];
                String destination = parts[1];
                int distance = Integer.parseInt(parts[2]);

                cityMap.addEdge(source, destination, distance);
            }

            scanner.close();

            System.out.println("Map data loaded successfully from " + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + fileName + " was not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Distance value in " + fileName + " must be a number.");
        }
    }
}