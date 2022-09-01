package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class TopicNode extends ArrayList<String> {
    private String topic = null;
    private boolean isVisited = false;

    TopicNode(final String uri) throws java.io.IOException {
        super();
        topic = uri;
        super.addAll(ParseTopics.getMentionedTopics(uri));
    }

    public static void main(String [] args) {
        try {
            TopicNode node = new TopicNode("/wiki/url");
            System.out.println(node.toString());
        }catch(java.io.IOException e) {
            e.printStackTrace();
            System.out.println("Unable to connect to " + "/wiki/url");
        }
    }

    /**
     * @return topic
     */
    public String getTopic() {
        return topic;
    }

    public boolean equals(TopicNode toCheck) {
        return toCheck.topic.equals(topic);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(topic);
        Iterator<String> iterator = super.iterator();
        while(iterator.hasNext()) {
            stringBuilder.append(iterator.next() + ", ");
        }
        return stringBuilder.toString();
    }

    public boolean visited() {
        return isVisited;
    }
    public void setVisited(boolean value) {
        isVisited = value;
    }
}
