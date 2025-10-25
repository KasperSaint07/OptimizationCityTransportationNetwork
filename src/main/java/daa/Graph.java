package daa;

import java.util.*;

public class Graph {
    public List<String> nodes;
    public List<Edge> edges;

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public int getVerticesCount() {
        return nodes.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }
}
