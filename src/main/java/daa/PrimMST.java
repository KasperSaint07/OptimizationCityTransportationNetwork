package daa;

import java.util.*;

public class PrimMST {

    public static MSTResult runPrim(Graph graph) {

        long operations = 0; // будем считать важные опирэйщны

        long startTime = System.nanoTime();

        // Возьмём первую вершину как старТ
        String startNode = graph.nodes.get(0);
        Set<String> visited = new HashSet<>();
        visited.add(startNode);

        // Мин-куча рёбер по весу
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        // кидаем все рёбра, которые выходят из старт вершины
        for (Edge e : graph.edges) {
            if (e.from.equals(startNode) || e.to.equals(startNode)) {
                pq.add(e);
                operations++;
            }
        }

        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;


        while (mstEdges.size() < graph.getVerticesCount() - 1 && !pq.isEmpty()) {

            Edge best = pq.poll(); // достаём самое дешёвое ребро
            operations++;

            // определяем в какую сторону идём
            String u = best.from;
            String v = best.to;

            boolean uVisited = visited.contains(u);
            boolean vVisited = visited.contains(v);
            operations += 2; // две проверки contains


            if (uVisited && vVisited) {
                continue;
            }

            // добавляем ребро в MST
            mstEdges.add(best);
            totalCost += best.weight;
            operations += 2;

            String newNode = uVisited ? v : u;
            visited.add(newNode);
            operations++; // add в visited

            for (Edge e : graph.edges) {
                boolean touchesNew =
                        e.from.equals(newNode) || e.to.equals(newNode);
                boolean otherSideVisited =
                        visited.contains(e.from) && visited.contains(e.to);

                operations += 3; // equals+equals+contains combo (примерно)

                if (touchesNew && !otherSideVisited) {
                    pq.add(e);
                    operations++; // add в pq
                }
            }
        }

        long endTime = System.nanoTime();
        double execMs = (endTime - startTime) / 1_000_000.0;

        return new MSTResult(
                mstEdges,
                totalCost,
                operations,
                execMs
        );
    }
}
