package Project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {

    private static class Edge {
        String destination;
        int weight;

        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    private static class PathNode implements Comparable<PathNode> {
        String neighborhood;
        int distance;

        public PathNode(String neighborhood, int distance) {
            this.neighborhood = neighborhood;
            this.distance = distance;
        }

        @Override
        public int compareTo(PathNode other) {
            return this.distance - other.distance;
        }
    }
    
 // Helper class for Prim's MST algorithm
    private static class MSTEdge implements Comparable<MSTEdge> {
        String source;
        String destination;
        int weight;

        public MSTEdge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public int compareTo(MSTEdge other) {
            return this.weight - other.weight;
        }
    }

    private Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String source, String destination, int weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.putIfAbsent(destination, new ArrayList<>());

        adjacencyList.get(source).add(new Edge(destination, weight));
        adjacencyList.get(destination).add(new Edge(source, weight));
    }

    public void displayGraph() {
        System.out.println("\n--- Kayseri City Map Graph ---");

        for (String neighborhood : adjacencyList.keySet()) {
            System.out.print(neighborhood + " -> ");

            List<Edge> edges = adjacencyList.get(neighborhood);

            for (Edge edge : edges) {
                System.out.print("(" + edge.destination + ", " + edge.weight + " km) ");
            }

            System.out.println();
        }
    }

    public void calculateShortestPath(String start, String end) {

    	
    	// Convert user input to actual graph keys by ignoring case
        String actualStart = findNeighborhoodKey(start);
        String actualEnd = findNeighborhoodKey(end);

        // Check if start or end does not exist in the graph
        if (actualStart == null || actualEnd == null) {
            System.out.println("Start or end neighborhood does not exist in the city map.");
            return;
        }

        start = actualStart;
        end = actualEnd;

        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            System.out.println("Start or end neighborhood does not exist in the city map.");
            return;
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<PathNode> priorityQueue = new PriorityQueue<>();

        for (String neighborhood : adjacencyList.keySet()) {
            distances.put(neighborhood, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        priorityQueue.add(new PathNode(start, 0));

        while (!priorityQueue.isEmpty()) {
            PathNode currentNode = priorityQueue.poll();
            String currentNeighborhood = currentNode.neighborhood;

            if (visited.contains(currentNeighborhood)) {
                continue;
            }

            visited.add(currentNeighborhood);

            if (currentNeighborhood.equals(end)) {
                break;
            }

            for (Edge edge : adjacencyList.get(currentNeighborhood)) {
                String neighbor = edge.destination;
                int newDistance = distances.get(currentNeighborhood) + edge.weight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, currentNeighborhood);
                    priorityQueue.add(new PathNode(neighbor, newDistance));
                }
            }
        }

        if (distances.get(end) == Integer.MAX_VALUE) {
            System.out.println("No path found from " + start + " to " + end + ".");
            return;
        }

        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("\n--- Shortest Path using Dijkstra ---");
        System.out.println("From: " + start);
        System.out.println("To: " + end);
        System.out.println("Shortest Distance: " + distances.get(end) + " km");
        System.out.println("Route: " + String.join(" -> ", path));
    }
    
 // Calculates and displays the Minimum Spanning Tree using Prim's algorithm
    public void calculateMST() {

        if (adjacencyList.isEmpty()) {
            System.out.println("City map is empty. MST cannot be calculated.");
            return;
        }

        Set<String> visited = new HashSet<>();
        PriorityQueue<MSTEdge> priorityQueue = new PriorityQueue<>();

        int totalWeight = 0;

        // Start from any neighborhood in the graph
        String start = adjacencyList.keySet().iterator().next();

        visited.add(start);

        // Add all edges of the starting neighborhood to the priority queue
        for (Edge edge : adjacencyList.get(start)) {
            priorityQueue.add(new MSTEdge(start, edge.destination, edge.weight));
        }

        System.out.println("\n--- Minimum Spanning Tree using Prim's Algorithm ---");

        while (!priorityQueue.isEmpty()) {

            // Pick the smallest edge
            MSTEdge currentEdge = priorityQueue.poll();

            // If destination is already visited, skip this edge
            if (visited.contains(currentEdge.destination)) {
                continue;
            }

            // Accept this edge into MST
            visited.add(currentEdge.destination);
            totalWeight += currentEdge.weight;

            System.out.println(
                    currentEdge.source + " -> " +
                    currentEdge.destination + " : " +
                    currentEdge.weight + " km"
            );

            // Add new edges from the newly visited neighborhood
            for (Edge edge : adjacencyList.get(currentEdge.destination)) {
                if (!visited.contains(edge.destination)) {
                    priorityQueue.add(
                            new MSTEdge(currentEdge.destination, edge.destination, edge.weight)
                    );
                }
            }
        }

        // If not all neighborhoods were visited, graph is disconnected
        if (visited.size() != adjacencyList.size()) {
            System.out.println("Warning: The graph is disconnected. MST does not include all neighborhoods.");
        }

        System.out.println("Total MST Distance: " + totalWeight + " km");
    }
    
 // Finds the actual graph key by ignoring uppercase/lowercase differences
    private String findNeighborhoodKey(String input) {
        for (String key : adjacencyList.keySet()) {
            if (key.equalsIgnoreCase(input)) {
                return key;
            }
        }

        return null;
    }
}