package com.company;

public class Main {
//improve runtime
    //current times:
    //4:35
    //2:37
    public static void main(String[] args) {
        WikiGraph wikiGraph = new WikiGraph("/wiki/Vela_Incident","/wiki/List_of_WWE_personnel");
        wikiGraph.startSearch();
        wikiGraph.displayPath();
    }
}
