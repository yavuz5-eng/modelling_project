package Project;

public class DoublyLinkedList {

    // Node class represents one package in the doubly linked list
    private static class Node {
        Package data; // Package stored in this node
        Node prev;    // Previous node
        Node next;    // Next node

        public Node(Package data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head; // First package in the intake buffer
    private Node tail; // Last package in the intake buffer

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Inserts a new package at the end of the buffer in O(1)
    public void insertAtTail(Package pkg) {
        Node newNode = new Node(pkg);

        // If the list is empty, new node becomes both head and tail
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        // Otherwise, connect the new node after the current tail
        else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Removes and returns the package from the front of the buffer
    public Package removeFromHead() {
        // If the list is empty, there is nothing to remove
        if (head == null) {
            System.out.println("Intake Buffer is empty.");
            return null;
        }

        Package removedPackage = head.data;

        // If there is only one node
        if (head == tail) {
            head = null;
            tail = null;
        }
        // If there is more than one node
        else {
            head = head.next;
            head.prev = null;
        }

        return removedPackage;
    }

    // Displays all packages currently waiting in the intake buffer
    public void displayBuffer() {
        if (head == null) {
            System.out.println("Intake Buffer is empty.");
            return;
        }

        System.out.println("\n--- Intake Buffer ---");

        Node current = head;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
