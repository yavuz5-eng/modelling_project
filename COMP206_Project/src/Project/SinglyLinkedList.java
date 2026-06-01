package Project;

public class SinglyLinkedList {

    // Node class represents one item in the linked list
    private static class Node {
        Package data; // Package object stored in this node
        Node next;    // Reference to the next node

        public Node(Package data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // First node of the list
    private Node tail; // Last node of the list

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Adds a package record to the end of the daily log
    public void addRecord(Package pkg) {
        Node newNode = new Node(pkg);

        // If the list is empty, new node becomes both head and tail
        if (head == null) {
            head = newNode;
            tail = newNode;
        } 
        // Otherwise, attach new node after tail and update tail
        else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    // Displays all package records in the daily log
    public void displayLog() {
        if (head == null) {
            System.out.println("Master Registry is empty.");
            return;
        }

        System.out.println("\n--- Master Registry Daily Log ---");

        Node current = head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
