package Project;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String companyName = "KayseriSwift Logistics";

        Scanner input = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("Welcome to " + companyName);
        System.out.println("Urban Logistics & Distribution System");
        System.out.println("Mission: Fast and optimized package delivery across Kayseri.");
        System.out.println("==================================");

        // Create required data structures
        SinglyLinkedList masterRegistry = new SinglyLinkedList();
        DoublyLinkedList intakeBuffer = new DoublyLinkedList();
        PackageQueue deliveryQueue = new PackageQueue();
        PackageStack truckStack = new PackageStack();
        AVLTree addressDirectory = new AVLTree();
        Graph cityMap = new Graph();

        // Read package and map data from txt files
        FileReaderUtil.readPackageData(
                "packageData.txt",
                masterRegistry,
                intakeBuffer,
                addressDirectory
        );

        FileReaderUtil.readMapData("mapData.txt", cityMap);
        
        addressDirectory.insert("Meydan", "HUB_000");

        int choice;

        do {
            displayMenu();

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Clears the newline character

            switch (choice) {

                case 1:
                    masterRegistry.displayLog();
                    break;

                case 2:
                    intakeBuffer.displayBuffer();
                    break;

                case 3:
                    movePackagesToQueue(intakeBuffer, deliveryQueue);
                    break;

                case 4:
                    deliveryQueue.displayQueue();
                    break;

                case 5:
                    loadPackagesToTruck(deliveryQueue, truckStack);
                    break;

                case 6:
                    truckStack.displayStack();
                    break;

                case 7:
                    unloadPackageFromTruck(truckStack);
                    break;

                case 8:
                    addressDirectory.displayInOrder();
                    break;

                case 9:
                    System.out.print("Enter neighborhood name to search: ");
                    String neighborhood = input.nextLine();

                    List<String> customerIDs = addressDirectory.search(neighborhood);

                    if (customerIDs != null) {
                        System.out.println("Customer IDs for " + neighborhood + ": " + customerIDs);
                    } else {
                        System.out.println("Neighborhood not found.");
                    }
                    break;

                case 10:
                    cityMap.displayGraph();
                    break;

                case 11:
                    System.out.print("Enter start neighborhood: ");
                    String start = input.nextLine();

                    System.out.print("Enter destination neighborhood: ");
                    String end = input.nextLine();

                    cityMap.calculateShortestPath(start, end);
                    break;

                case 12:
                    cityMap.calculateMST();
                    break;

                case 0:
                    System.out.println("Exiting system. Thank you for using " + companyName + ".");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        input.close();
    }

    // Displays the main menu
    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Display Master Registry (SLL)");
        System.out.println("2. Display Intake Buffer (DLL)");
        System.out.println("3. Move Packages from Intake Buffer to Delivery Queue");
        System.out.println("4. Display Delivery Queue");
        System.out.println("5. Load Packages from Delivery Queue to Truck Stack");
        System.out.println("6. Display Truck Stack");
        System.out.println("7. Unload Package from Truck");
        System.out.println("8. Display Address Directory (AVL Tree)");
        System.out.println("9. Search Address Directory");
        System.out.println("10. Display City Map Graph");
        System.out.println("11. Calculate Shortest Path with Dijkstra");
        System.out.println("12. Calculate MST with Prim's Algorithm");
        System.out.println("0. Exit");
        System.out.println("===============================");
    }

    // Moves all packages from DLL intake buffer to queue
    private static void movePackagesToQueue(
            DoublyLinkedList intakeBuffer,
            PackageQueue deliveryQueue
    ) {
        System.out.println("\n--- Moving Packages from Intake Buffer to Delivery Queue ---");

        Package currentPackage = intakeBuffer.removeFromHead();

        if (currentPackage == null) {
            System.out.println("No packages were moved.");
            return;
        }

        while (currentPackage != null) {
            System.out.println("Moved to queue: " + currentPackage);
            deliveryQueue.enqueue(currentPackage);

            currentPackage = intakeBuffer.removeFromHead();
        }
    }

    // Moves all packages from queue to truck stack
    private static void loadPackagesToTruck(
            PackageQueue deliveryQueue,
            PackageStack truckStack
    ) {
        System.out.println("\n--- Loading Packages from Delivery Queue to Truck Stack ---");

        Package currentPackage = deliveryQueue.dequeue();

        if (currentPackage == null) {
            System.out.println("No packages were loaded.");
            return;
        }

        while (currentPackage != null) {
            System.out.println("Loaded into truck: " + currentPackage);
            truckStack.push(currentPackage);

            currentPackage = deliveryQueue.dequeue();
        }
    }

    // Removes one package from the truck stack
    private static void unloadPackageFromTruck(PackageStack truckStack) {
        System.out.println("\n--- Unloading One Package from Truck ---");

        Package unloadedPackage = truckStack.pop();

        if (unloadedPackage != null) {
            System.out.println("Unloaded package: " + unloadedPackage);
        }
    }
}