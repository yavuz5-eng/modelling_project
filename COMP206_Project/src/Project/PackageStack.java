package Project;

public class PackageStack {

    // Node class represents one package in the stack
    private static class Node {
        Package data; // Package stored in this node
        Node next;    // Node below this one

        public Node(Package data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top; // Top package in the truck

    public PackageStack() {
        this.top = null;
    }

    // Loads a package into the truck
    public void push(Package pkg) {
        Node newNode = new Node(pkg);

        // New package is placed on top of the stack
        newNode.next = top;
        top = newNode;
    }

    // Unloads the most recently loaded package
    public Package pop() {
        // If stack is empty, there is nothing to unload
        if (top == null) {
            System.out.println("Truck Loading Stack is empty.");
            return null;
        }

        Package removedPackage = top.data;

        // Move top to the package below
        top = top.next;

        return removedPackage;
    }

    // Displays all packages currently loaded in the truck
    public void displayStack() {
        if (top == null) {
            System.out.println("Truck Loading Stack is empty.");
            return;
        }

        System.out.println("\n--- Truck Loading Stack ---");

        Node current = top;

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
