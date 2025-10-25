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

            //PRIM
            MSTResult primResult = PrimMST.runPrim(g);

            System.out.println("[Prim]");
            System.out.println("  MST total cost = " + primResult.totalCost);
            System.out.println("  MST edges:");
            for (Edge mstEdge : primResult.mstEdges) {
                System.out.println("    " + mstEdge);
            }
            System.out.println("  operations = " + primResult.operationsCount);
            System.out.println("  time (ms)  = " + primResult.executionTimeMs);

            //KRUSKAL
            MSTResult kruskalResult = KruskalMST.runKruskal(g);

            System.out.println("[Kruskal]");
            System.out.println("  MST total cost = " + kruskalResult.totalCost);
            System.out.println("  MST edges:");
            for (Edge mstEdge : kruskalResult.mstEdges) {
                System.out.println("    " + mstEdge);
            }
            System.out.println("  operations = " + kruskalResult.operationsCount);
            System.out.println("  time (ms)  = " + kruskalResult.executionTimeMs);

            //VALIDATION
            if (primResult.totalCost == kruskalResult.totalCost) {
                System.out.println("Both algorithms produced the same MST cost.");
            } else {
                System.out.println(" Mismatch in MST cost!");
            }

            System.out.println("====================================");
            idx++;
        }
    }
}
