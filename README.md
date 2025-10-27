# Assignment 3 — Optimization of a City Transportation Network (Minimum Spanning Tree)

**Course:** Design and Analysis of Algorithms  
**Student:** Madiyar Amanzhol  
**Group:** SE-2407  
**University:** Astana IT University  
**Date:** October 27, 2025  
**Topic:** Minimum Spanning Tree (Prim vs Kruskal)


## 1. Objective

The goal of this assignment is to optimize a city transportation network by building a set of roads that:
- connects all city districts (all vertices are reachable),
- has the minimum possible total construction cost.

The city map is modeled as a **weighted undirected graph**:
- vertices = districts,
- edges = possible roads,
- edge weight = construction cost.

To solve this, we compute a **Minimum Spanning Tree (MST)** using two classic greedy algorithms:
- Prim’s Algorithm
- Kruskal’s Algorithm

We then compare their performance on the same input graphs.


## 2. What the program does

The program:
1. Reads the input graph data from `src/main/resources/input.json`.
2. For each graph:
   - runs **Prim's algorithm**,
   - runs **Kruskal's algorithm**,
   - collects statistics for each algorithm.
3. For both algorithms it records:
   - the list of edges used in the MST,
   - the total MST cost,
   - the number of operations performed,
   - the execution time in milliseconds.
4. It also records:
   - number of vertices and edges in the original graph.
5. Finally it writes all results (for all graphs) into  
   `src/main/output/result.json`.

This `result.json` can be attached directly to the report / submission.


## 3. Project structure

```text
OptimizationCityTransportationNetwork/
│
├─ pom.xml
│
└─ src/
   └─ main/
      ├─ java/
      │   └─ daa/
      │       ├─ Main.java
      │       ├─ Analyzer.java
      │       ├─ Graph.java
      │       ├─ Edge.java
      │       ├─ MSTResult.java
      │       ├─ PrimMST.java
      │       ├─ KruskalMST.java
      │       ├─ UnionFind.java
      │
      ├─ resources/
      │   └─ input.json
      │        (list of graphs: nodes and weighted edges)
      │
      └─ output/
          └─ result.json
          (auto-generated after running Main.java)


4. How to run
Option A: Run from IntelliJ IDEA

Open the project as a Maven project.

Make sure pom.xml is loaded (Gson dependency is resolved).

Open Main.java.

Run main().



5. Input format (input.json)

The input JSON contains an array of graphs.
Each graph has:

"nodes": list of vertex names (district labels)

"edges": list of possible roads with weights



{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2},
        {"from": "B", "to": "D", "weight": 5},
        {"from": "C", "to": "D", "weight": 7},
        {"from": "C", "to": "E", "weight": 8},
        {"from": "D", "to": "E", "weight": 6}
      ]
    }
  ]
}


{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {
        "vertices": 5,
        "edges": 7
      },
      "prim": {
        "mst_edges": [
          {"from": "A", "to": "C", "weight": 3},
          {"from": "B", "to": "C", "weight": 2},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 118,
        "execution_time_ms": 0.666
      },
      "kruskal": {
        "mst_edges": [
          {"from": "B", "to": "C", "weight": 2},
          {"from": "A", "to": "C", "weight": 3},
          {"from": "B", "to": "D", "weight": 5},
          {"from": "D", "to": "E", "weight": 6}
        ],
        "total_cost": 16,
        "operations_count": 73,
        "execution_time_ms": 0.8705
      }
    },
    {
      "graph_id": 2,
      "input_stats": {
        "vertices": 4,
        "edges": 5
      },
      "prim": {
        "mst_edges": [
          {"from": "A", "to": "B", "weight": 1},
          {"from": "B", "to": "C", "weight": 2},
          {"from": "C", "to": "D", "weight": 3}
        ],
        "total_cost": 6,
        "operations_count": 68,
        "execution_time_ms": 0.0482
      },
      "kruskal": {
        "mst_edges": [
          {"from": "A", "to": "B", "weight": 1},
          {"from": "B", "to": "C", "weight": 2},
          {"from": "C", "to": "D", "weight": 3}
        ],
        "total_cost": 6,
        "operations_count": 50,
        "execution_time_ms": 0.042
      }
    }
  ]
}

This matches the instructor’s expectations:

per-graph stats,

Prim block,

Kruskal block,

cost, time, operations.


7. Algorithms used
Prim’s Algorithm

Start from an arbitrary vertex.

Repeatedly take the cheapest edge that connects the current tree to a new vertex.

Continue until all vertices are included.

Internally uses:

visited set

PriorityQueue<Edge> to always pick the minimum-weight available edge.

Measured values:

total_cost

which edges were chosen

operationsCount (push/pop in priority queue, visited checks)

execution time in ms

Class in code: PrimMST.java
Result container: MSTResult.java

Kruskal’s Algorithm

Sort all edges by weight (ascending).

Go through edges from cheapest to most expensive.

Take an edge if it connects two different components.

Skip an edge if it would form a cycle.

Use a Disjoint Set Union / Union-Find data structure.

Measured values:

same metrics as Prim (cost, edges, operations, time)

Classes in code:

KruskalMST.java

UnionFind.java (for union-find / DSU)



8. Performance summary

Below is an example summary from running the program (your console output):

Graph #1

Vertices: 5

Edges: 7

Prim:

MST total cost = 16

operations = 118

time (ms) = 0.666

Kruskal:

MST total cost = 16

operations = 73

time (ms) = 0.8705

MST cost matches 

Graph #2

Vertices: 4

Edges: 5

Prim:

MST total cost = 6

operations = 68

time (ms) = 0.0482

Kruskal:

MST total cost = 6

operations = 50

time (ms) = 0.042

MST cost matches 

Interpretation:

Both algorithms always return the same MST cost, as expected.

Kruskal tends to use fewer logical operations on these graphs.

Runtime is very small (< 1 ms), but still recorded for comparison.

9. When to use which algorithm

Prim’s Algorithm

Better suited for dense graphs (many edges).

Natural with adjacency structure and a priority queue.

Grows a single connected tree outward.

Kruskal’s Algorithm

Very good for sparse graphs (fewer edges).

Works edge-by-edge using sorting + Union-Find.

Easy to implement and reason about.

In our tests, both produced identical total MST cost.
The sequence of chosen edges can differ, but the total cost must be the same.

10. References

These resources were used to design and analyze the algorithms:

Robert Sedgewick, Kevin Wayne — Algorithms, 4th Edition. Minimum Spanning Trees.

Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein — Introduction to Algorithms, 3rd Ed.

Princeton University, Algorithms (algs4) materials on MST and Union-Find.

GeeksForGeeks: explanations of Prim’s Algorithm and Kruskal’s Algorithm (used to align terminology and operation counting style).
