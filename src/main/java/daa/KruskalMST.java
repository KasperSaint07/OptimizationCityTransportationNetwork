package daa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    public static MSTResult runKruskal(Graph graph) {

        long operations = 0;

        long startTime = System.nanoTime();

        List<Edge> sortedEdges = new ArrayList<>(graph.edges);
        Collections.sort(sortedEdges);
        operations += sortedEdges.size();

        UnionFind uf = new UnionFind(graph.nodes);

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (Edge e : sortedEdges) {

            boolean merged = uf.union(e.from, e.to);
            operations++;

            if (merged) {

                mstEdges.add(e);
                totalCost += e.weight;
                operations += 2; // add + сумма


                if (mstEdges.size() == graph.getVerticesCount() - 1) {
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        double execMs = (endTime - startTime) / 1_000_000.0;

        long totalOperations = operations + uf.operationsCount;

        return new MSTResult(
                mstEdges,
                totalCost,
                totalOperations,
                execMs
        );
    }
}
