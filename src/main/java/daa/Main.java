package daa;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Graph> graphs = Analyzer.readGraphs("src/main/resources/input.json");

        int idx = 1;
        for (Graph g : graphs) {
            System.out.println("Graph #" + idx);
            System.out.println("Vertices: " + g.getVerticesCount());
            System.out.println("Edges: " + g.getEdgesCount());
            System.out.println("Edge list:");
            for (Edge e : g.edges) {
                System.out.println("  " + e);
            }
            System.out.println("-----------------------------");
            idx++;
        }
    }
}
