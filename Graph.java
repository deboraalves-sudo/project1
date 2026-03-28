import java.util.ArrayList;
import java.util.List;

public class Graph {
    private final int vertices;
    private final List<List<Integer>> adjList;

    public Graph(int vertices) {
        if (vertices <= 0) {
            throw new IllegalArgumentException("Number of vertices must be greater than 0.");
        }

        this.vertices = vertices;
        this.adjList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        validateVertex(source);
        validateVertex(destination);

        if (source == destination) {
            return;
        }

        if (!adjList.get(source).contains(destination)) {
            adjList.get(source).add(destination);
        }

        if (!adjList.get(destination).contains(source)) {
            adjList.get(destination).add(source); // undirected graph
        }
    }

    public int getVertices() {
        return vertices;
    }

    public List<List<Integer>> getAdjList() {
        return adjList;
    }

    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("Invalid vertex: " + vertex);
        }
    }
}