package com.company;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class WikiGraph {
    private String start;
    private String end;
    private ArrayList<String> path = null;

    WikiGraph(final String _start, final String _end) {
        start = _start;
        end = _end;
    }

    public static void main(String [] args) {
        WikiGraph wikiGraph = new WikiGraph("/wiki/Family_Guy", "/wiki/SpongeBob_SquarePants");
        wikiGraph.startSearch();
        wikiGraph.displayPath();
    }

    public ArrayList<String> startSearch() {
        Instant startTime = Instant.now();
        TopicGraph topicGraph = new TopicGraph();
        if (topicGraph.addNodeUntil(start, end)) {
            System.out.println("Finished pulling topics from Wikipedia");
        }
        else {
            System.out.println("Error while pulling topics from Wikipedia");
        }
        path = topicGraph.findRouteDFS(start, end);
        Instant endTime = Instant.now();
        System.out.println("Running time: " + Duration.between(startTime, endTime).toMillis() / 1000.0 + "s");
        return path;
    }

    public void displayPath() {
        if (path == null) return;
        for (int i=path.size() - 1; i>=0; --i) {
            System.out.print(path.get(i));
            if (i != 0) {
                System.out.print(" -> ");
            }
        }
    }

    public ArrayList<String> getPath() {
        return path;
    }
}
