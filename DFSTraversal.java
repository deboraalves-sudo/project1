import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DFSTraversal {

    public List<Integer> execute(Graph graph, int startVertex) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null.");
        }

        if (startVertex < 0 || startVertex >= graph.getVertices()) {
            throw new IllegalArgumentException("Invalid start vertex.");
        }

        boolean[] visited = new boolean[graph.getVertices()];
        List<Integer> traversalOrder = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            int vertex = stack.pop();

            if (!visited[vertex]) {
                visited[vertex] = true;
                traversalOrder.add(vertex);

                List<Integer> neighbors = graph.getAdjList().get(vertex);

                // Push in reverse order to preserve a traversal order
                // similar to recursive DFS
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int neighbor = neighbors.get(i);
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        return traversalOrder;
    }
}