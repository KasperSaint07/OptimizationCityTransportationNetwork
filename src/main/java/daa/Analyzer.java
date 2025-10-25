package daa;

import com.google.gson.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    public static List<Graph> readGraphs(String filePath) {
        List<Graph> graphs = new ArrayList<>();

        try {
            Gson gson = new Gson();
            JsonObject data = gson.fromJson(new FileReader(filePath), JsonObject.class);

            JsonArray graphsArray = data.getAsJsonArray("graphs");

            for (JsonElement elem : graphsArray) {
                JsonObject g = elem.getAsJsonObject();
                List<String> nodes = new ArrayList<>();
                JsonArray nodesJson = g.getAsJsonArray("nodes");
                for (JsonElement nodeElem : nodesJson) {
                    nodes.add(nodeElem.getAsString());
                }

                List<Edge> edges = new ArrayList<>();
                JsonArray edgesJson = g.getAsJsonArray("edges");
                for (JsonElement edgeElem : edgesJson) {
                    JsonObject e = edgeElem.getAsJsonObject();
                    String from = e.get("from").getAsString();
                    String to = e.get("to").getAsString();
                    int weight = e.get("weight").getAsInt();

                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(nodes, edges));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graphs;
    }
}
