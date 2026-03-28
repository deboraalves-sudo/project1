import java.util.List;
import java.util.Random;

public class GraphPerformanceAnalyzer {

    private static final int[] INPUT_SIZES = {100, 500, 1000, 5000, 10000};
    private static final int RUNS_PER_SIZE = 10;

    public static void main(String[] args) {
        DFSTraversal dfsTraversal = new DFSTraversal();

        System.out.println("PROJECT 1 - GRAPH TRAVERSAL PERFORMANCE ANALYZER");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-12s %-12s %-20s %-20s%n",
                "Vertices", "Edges", "Avg Runtime (ms)", "Avg Memory (KB)");
        System.out.println("----------------------------------------------------------------");

        for (int vertices : INPUT_SIZES) {
            int edges = vertices * 3;

            long totalTimeNs = 0;
            long totalMemoryBytes = 0;
            int visitedCount = 0;

            for (int run = 0; run < RUNS_PER_SIZE; run++) {
                Graph graph = generateRandomConnectedGraph(vertices, edges);

                Runtime runtime = Runtime.getRuntime();
                System.gc();

                long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
                long startTime = System.nanoTime();

                List<Integer> traversalOrder = dfsTraversal.execute(graph, 0);

                long endTime = System.nanoTime();
                long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

                totalTimeNs += (endTime - startTime);
                totalMemoryBytes += Math.max(0, memoryAfter - memoryBefore);
                visitedCount = traversalOrder.size();
            }

            double avgTimeMs = totalTimeNs / (RUNS_PER_SIZE * 1_000_000.0);
            double avgMemoryKB = totalMemoryBytes / (RUNS_PER_SIZE * 1024.0);

            System.out.printf("%-12d %-12d %-20.4f %-20.4f%n",
                    vertices, edges, avgTimeMs, avgMemoryKB);
            System.out.printf("%-12s %d%n", "Visited:", visitedCount);
            System.out.println("----------------------------------------------------------------");
        }
    }

    public static Graph generateRandomConnectedGraph(int vertices, int edges) {
        if (edges < vertices - 1) {
            throw new IllegalArgumentException("Edges must be at least vertices - 1.");
        }

        Graph graph = new Graph(vertices);
        Random random = new Random();

        // Make graph connected first
        for (int i = 0; i < vertices - 1; i++) {
            graph.addEdge(i, i + 1);
        }

        int currentEdges = vertices - 1;

        while (currentEdges < edges) {
            int source = random.nextInt(vertices);
            int destination = random.nextInt(vertices);

            if (source != destination && !graph.getAdjList().get(source).contains(destination)) {
                graph.addEdge(source, destination);
                currentEdges++;
            }
        }

        return graph;
    }
}