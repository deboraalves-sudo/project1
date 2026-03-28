import java.util.List;

public class GraphProjectTest {
    public static void main(String[] args) {
        Graph graph = new Graph(7);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);

        DFSTraversal dfsTraversal = new DFSTraversal();
        List<Integer> result = dfsTraversal.execute(graph, 0);

        System.out.println("DFS Traversal starting from vertex 0:");
        System.out.println(result);
    }
}