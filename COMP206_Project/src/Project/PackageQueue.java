package Project;

public class PackageQueue {

    // Node class represents one package in the queue
    private static class Node {
        Package data; // Package stored in this node
        Node next;    // Next node in the queue

        public Node(Package data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // First package to be removed
    private Node rear;  // Last package added

    public PackageQueue() {
        this.front = null;
        this.rear = null;
    }

    // Adds a package to the back of the queue
    public void enqueue(Package pkg) {
        Node newNode = new Node(pkg);

        // If queue is empty, new node becomes both front and rear
        if (rear == null) {
            front = newNode;
            rear = newNode;
        }
        // Otherwise, attach the new node after rear and update rear
        else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    // Removes and returns the package from the front of the queue
    public Package dequeue() {
        // If queue is empty, there is nothing to remove
        if (front == null) {
            System.out.println("Standard Delivery Queue is empty.");
            return null;
        }

        Package removedPackage = front.data;

        // Move front to the next node
        front = front.next;

        // If queue became empty, rear must also be null
        if (front == null) {
            rear = null;
        }

        return removedPackage;
    }

    // Displays all packages in the queue
    public void displayQueue() {
        if (front == null) {
            System.out.println("Standard Delivery Queue is empty.");
            return;
        }

        System.out.println("\n--- Standard Delivery Queue ---");

        Node current = front;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}