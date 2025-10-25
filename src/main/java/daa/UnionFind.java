package daa;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {

    private Map<String, String> parent;
    private Map<String, Integer> rank;

    public long operationsCount = 0; // будем считать операции для отчёта

    public UnionFind(Iterable<String> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (String node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
            operationsCount += 2;
        }
    }

    public String find(String x) {
        operationsCount++;
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
            operationsCount += 2;
        }
        return parent.get(x);
    }

    public boolean union(String a, String b) {
        // возвращает true, если реально слили два разных множества
        // false, если они уже были в одном (то есть это ребро создало бы цикл)

        String rootA = find(a);
        String rootB = find(b);

        operationsCount += 2;

        if (rootA.equals(rootB)) {
            operationsCount++;
            return false;
        }

        // union by rank
        int rankA = rank.get(rootA);
        int rankB = rank.get(rootB);
        operationsCount += 2;

        if (rankA < rankB) {
            parent.put(rootA, rootB);
            operationsCount++;
        } else if (rankA > rankB) {
            parent.put(rootB, rootA);
            operationsCount++;
        } else {
            parent.put(rootB, rootA);
            rank.put(rootA, rankA + 1);
            operationsCount += 2;
        }

        return true; // мы соединили два разных компонента
    }
}
