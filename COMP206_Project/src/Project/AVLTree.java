package Project;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    // Node class represents one neighborhood in the AVL tree
    private static class Node {
        String neighborhood;          // Neighborhood name
        List<String> customerIDs;     // Multiple customers can live in the same neighborhood
        int height;                   // Height of the node
        Node left;                    // Left child
        Node right;                   // Right child

        public Node(String neighborhood, String customerID) {
            this.neighborhood = neighborhood;
            this.customerIDs = new ArrayList<>();
            this.customerIDs.add(customerID);
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public AVLTree() {
        this.root = null;
    }

    // Public insert method
    public void insert(String neighborhood, String customerID) {
        root = insert(root, neighborhood, customerID);
    }

    // Recursive insert method
    private Node insert(Node node, String neighborhood, String customerID) {

        // Normal BST insertion
        if (node == null) {
            return new Node(neighborhood, customerID);
        }

        if (neighborhood.compareToIgnoreCase(node.neighborhood) < 0) {
            node.left = insert(node.left, neighborhood, customerID);
        } else if (neighborhood.compareToIgnoreCase(node.neighborhood) > 0) {
            node.right = insert(node.right, neighborhood, customerID);
        } else {
            // If the neighborhood already exists, add the customer ID to the same node
            node.customerIDs.add(customerID);
            return node;
        }

        // Update height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // Balance the tree
        return balance(node);
    }

    // Searches a neighborhood and returns all customer IDs
    public List<String> search(String neighborhood) {
        Node result = search(root, neighborhood);

        if (result == null) {
            return null;
        }

        return result.customerIDs;
    }

    // Recursive search method
    private Node search(Node node, String neighborhood) {

        if (node == null) {
            return null;
        }

        if (neighborhood.equalsIgnoreCase(node.neighborhood)) {
            return node;
        }

        if (neighborhood.compareToIgnoreCase(node.neighborhood) < 0) {
            return search(node.left, neighborhood);
        }

        return search(node.right, neighborhood);
    }

    // Balances an AVL node if needed
    private Node balance(Node node) {
        int balanceFactor = getBalanceFactor(node);

        // Left heavy
        if (balanceFactor > 1) {

            // Left-Right case
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }

            // Left-Left case
            return rotateRight(node);
        }

        // Right heavy
        if (balanceFactor < -1) {

            // Right-Left case
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }

            // Right-Right case
            return rotateLeft(node);
        }

        return node;
    }

    // Right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node temp = x.right;

        x.right = y;
        y.left = temp;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // Left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node temp = y.left;

        y.left = x;
        x.right = temp;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }

        return node.height;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    // Displays AVL tree using inorder traversal
    public void displayInOrder() {
        System.out.println("\n--- Address Directory AVL Tree ---");
        displayInOrder(root);
    }

    private void displayInOrder(Node node) {
        if (node == null) {
            return;
        }

        displayInOrder(node.left);

        System.out.println(
                "Neighborhood: " + node.neighborhood +
                ", Customer IDs: " + node.customerIDs
        );

        displayInOrder(node.right);
    }
}