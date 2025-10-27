package daa;

import com.google.gson.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //читает граф из входного JSON
        List<Graph> graphs = Analyzer.readGraphs("src/main/resources/input.json");

        //Это будет использовано и для печати, и для записи в файл:
        List<JsonObject> jsonResults = new ArrayList<>();

        int idx = 1;
        for (Graph g : graphs) {

            //инфа про граф
            System.out.println("Graph #" + idx);
            System.out.println("Vertices: " + g.getVerticesCount());
            System.out.println("Edges: " + g.getEdgesCount());
            System.out.println("Edge list:");
            for (Edge e : g.edges) {
                System.out.println("  " + e);
            }
            System.out.println("-----------------------------");

            //Prim
            MSTResult primResult = PrimMST.runPrim(g);
            System.out.println("[Prim]");
            System.out.println("  MST total cost = " + primResult.totalCost);
            System.out.println("  MST edges:");
            for (Edge mstEdge : primResult.mstEdges) {
                System.out.println("    " + mstEdge);
            }
            System.out.println("  operations = " + primResult.operationsCount);
            System.out.println("  time (ms)  = " + primResult.executionTimeMs);

            //Kruskal
            MSTResult kruskalResult = KruskalMST.runKruskal(g);
            System.out.println("[Kruskal]");
            System.out.println("  MST total cost = " + kruskalResult.totalCost);
            System.out.println("  MST edges:");
            for (Edge mstEdge : kruskalResult.mstEdges) {
                System.out.println("    " + mstEdge);
            }
            System.out.println("  operations = " + kruskalResult.operationsCount);
            System.out.println("  time (ms)  = " + kruskalResult.executionTimeMs);

            if (primResult.totalCost == kruskalResult.totalCost) {
                System.out.println("Both algorithms produced the same MST cost.");
            } else {
                System.out.println("Mismatch in MST cost!");
            }

            System.out.println("====================================");

            //готовим JSON-объект для этого графа

            JsonObject perGraphJson = new JsonObject();

            // graph_id
            perGraphJson.addProperty("graph_id", idx);

            // input_stats
            JsonObject statsJson = new JsonObject();
            statsJson.addProperty("vertices", g.getVerticesCount());
            statsJson.addProperty("edges", g.getEdgesCount());
            perGraphJson.add("input_stats", statsJson);

            // prim
            JsonObject primJson = new JsonObject();
            JsonArray primEdgesJson = new JsonArray();
            for (Edge edge : primResult.mstEdges) {
                JsonObject eJson = new JsonObject();
                eJson.addProperty("from", edge.from);
                eJson.addProperty("to", edge.to);
                eJson.addProperty("weight", edge.weight);
                primEdgesJson.add(eJson);
            }
            primJson.add("mst_edges", primEdgesJson);
            primJson.addProperty("total_cost", primResult.totalCost);
            primJson.addProperty("operations_count", primResult.operationsCount);
            primJson.addProperty("execution_time_ms", primResult.executionTimeMs);
            perGraphJson.add("prim", primJson);

            // kruskal
            JsonObject kruskalJson = new JsonObject();
            JsonArray kruskalEdgesJson = new JsonArray();
            for (Edge edge : kruskalResult.mstEdges) {
                JsonObject eJson = new JsonObject();
                eJson.addProperty("from", edge.from);
                eJson.addProperty("to", edge.to);
                eJson.addProperty("weight", edge.weight);
                kruskalEdgesJson.add(eJson);
            }
            kruskalJson.add("mst_edges", kruskalEdgesJson);
            kruskalJson.addProperty("total_cost", kruskalResult.totalCost);
            kruskalJson.addProperty("operations_count", kruskalResult.operationsCount);
            kruskalJson.addProperty("execution_time_ms", kruskalResult.executionTimeMs);
            perGraphJson.add("kruskal", kruskalJson);


            jsonResults.add(perGraphJson);

            idx++;
        }

        //Упаковываем всё это в объект верхнего уровня:
        JsonObject root = new JsonObject();
        JsonArray resultsJsonArray = new JsonArray();
        for (JsonObject obj : jsonResults) {
            resultsJsonArray.add(obj);
        }
        root.add("results", resultsJsonArray);

        //В файл src/main/output/result.json
        try (FileWriter fw = new FileWriter("src/main/output/result.json")) {
            Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();
            gsonPretty.toJson(root, fw);
            System.out.println("result.json written to src/main/output/result.json");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
