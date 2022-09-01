package com.company;

import java.util.*;

public class TopicGraph {
    public static long MAX_DEPTH = 1000;
    private HashMap<String, TopicNode> verticies = new HashMap<>();

    TopicGraph() { }

    public static void main(String [] args) throws Exception {
        TopicGraph topicGraph = new TopicGraph();
        if (topicGraph.addNodeUntil("/wiki/gardening","/wiki/Family_Guy")) {
            topicGraph.display();
            System.out.println();
            ArrayList<String> path = topicGraph.findRouteDFS("/wiki/gardening","/wiki/Family_Guy");
            System.out.println("Path from start to end: ");
            for (String p : path) {
                System.out.println(p);
            }
        }
    }

    // Display all verticies in the graph (Does not display edges which have no verticy)
    private void display() {
        Set<String> keys = verticies.keySet();
        for (String key : keys) {
            System.out.print(key + ", ");
            ArrayList<String> related = verticies.get(key);
            for (String topic : related) {
                if (verticies.containsKey(topic)) {
                    System.out.print(topic + ", ");
                }
            }
            // System.out.print(verticies.get(key).toString());
            System.out.println();
        }
    }

    // Adds a single node to the graph by the uri. Does not add the related topics as nodes, but only as edges to newTopic
    public TopicNode addNode(String newTopic) {
        if (verticies.containsKey(newTopic)) return null;
        try {
            TopicNode newNode = new TopicNode(newTopic);
            verticies.putIfAbsent(newTopic, newNode);
            System.out.println("(" + verticies.size() + ") Adding: " + newTopic);
            return newNode;
        }catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create node for: " + newTopic);
        }
        return null;
    }

    // Might get full heap error here!
    public boolean addNodeUntil(final String start, final String end) {
        Queue<String> toAdd = new ArrayDeque<>();
        toAdd.add(start);
        boolean found = false;
        while (!found && !toAdd.isEmpty()) {
            found = addNodeUntil(toAdd.poll(), end, toAdd);
        }
        return found;
    }

    // Adds nodes until the end has been added. By adding the end we know we have some path from start to end
    private boolean addNodeUntil(String current, final String end, Queue<String> toAdd) {
        ArrayList<String> relatedTopics = addNode(current);
        if (relatedTopics != null) {
            for (String topic : relatedTopics) {
                if (!verticies.containsKey(topic))
                    toAdd.addAll(relatedTopics);
            }
        }
        return current.equals(end);
    }

    public ArrayList<String> findRouteBFS(final String start, final String end) {
        ArrayList<String> path = new ArrayList<>();
        Queue<TopicNode> toSearch = new ArrayDeque<>();
        toSearch.add(verticies.get(start));
        findRouteBFS(end, toSearch, path);

        toSearch.clear();
        return path;
    }

    // BFS from start to end
    // TODO fix BFS
    private boolean findRouteBFS(final String end, Queue<TopicNode> toSearch, ArrayList<String> path) {
        TopicNode current = toSearch.poll();
        if (current == null) {
            return false;
        }
        if (current.getTopic().equals(end)) {
            path.add(current.getTopic());
            return true;
        }

        for (String topic : current) {
            TopicNode foundTopic = verticies.get(topic);
            if (foundTopic != null && !foundTopic.visited()) {
                foundTopic.setVisited(true);
                toSearch.add(foundTopic);
            }
        }
        if (findRouteBFS(end, toSearch, path)) {
            path.add(current.getTopic());
            return true;
        }
        return false;
    }

    // Search by DFS
    public ArrayList<String> findRouteDFS(final String start, final String end) {
        Stack<TopicNode> toSearch = new Stack<>();
        ArrayList<String> path = new ArrayList<>();
        toSearch.push(verticies.get(start));
        findRouteDFS(toSearch, end, path);

        return path;
    }

    private boolean findRouteDFS(Stack<TopicNode> toSearch, final String end, ArrayList<String> path) {
        TopicNode current = toSearch.pop();
        if (current == null) {
            return false;
        }
        if (current.getTopic().equals(end)) {
            current.setVisited(true);
            path.add(current.getTopic());
            return true;
        }

        for (String topic : current) {
            TopicNode next = verticies.get(topic);
            if (next != null && !next.visited()) {
                next.setVisited(true);
                toSearch.push(next);
                if (findRouteDFS(toSearch, end, path)) {
                    path.add(current.getTopic());
                    return true;
                }
            }
        }
        return false;
    }
}
