import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Project1 {

    public static void main(String[] args) {
        int[] small = generateRandomArray(100);
        int[] medium = generateRandomArray(1_000);
        int[] large = generateRandomArray(10_000);

        int[][] testArrays = {small, medium, large};
        String[] sizes = {"100", "1,000", "10,000"};

        // Use a List instead of a generic array
        List<Algorithm<int[], int[]>> algorithms = new ArrayList<>();
        algorithms.add(new BubbleSort());
        algorithms.add(new MergeSort());
        algorithms.add(new QuickSort());
        // Teammates add their algorithms here...

        for (int i = 0; i < testArrays.length; i++) {
            int[] original = testArrays[i];
            System.out.println("=== Array Size: " + sizes[i] + " ===");

            for (Algorithm<int[], int[]> algo : algorithms) {
                int[] arrayCopy = original.clone();
                long startTime = System.nanoTime();
                algo.execute(arrayCopy);
                long endTime = System.nanoTime();

                System.out.println(algo.getName() + " result (first 20 elements): " +
                        Arrays.toString(Arrays.copyOf(arrayCopy, Math.min(20, arrayCopy.length))));
                System.out.println("Execution time: " + (endTime - startTime) + " ns\n");
            }
        }

        System.out.println("\n======================================");
        System.out.println("GRAPH TRAVERSAL PERFORMANCE ANALYSIS");
        System.out.println("======================================");

        int[] graphSizes = {100, 500, 1000, 5000, 10000};
        DFSTraversal dfsTraversal = new DFSTraversal();

        for (int vertices : graphSizes) {
            int edges = vertices * 3;

            long totalTime = 0;
            long totalMemory = 0;
            int visitedCount = 0;

            for (int run = 0; run < 5; run++) {
                Graph graph = generateRandomConnectedGraph(vertices, edges);

                Runtime runtime = Runtime.getRuntime();
                System.gc();

                long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
                long startTime = System.nanoTime();

               List<Integer> traversalOrder = dfsTraversal.execute(graph, 0);

                long endTime = System.nanoTime();
                long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

                totalTime += (endTime - startTime);
                totalMemory += Math.max(0, memoryAfter - memoryBefore);
                visitedCount = traversalOrder.size();
            }

            double avgTimeMs = totalTime / 5.0 / 1_000_000.0;
            double avgMemoryKB = totalMemory / 5.0 / 1024.0;

            System.out.println("Vertices: " + vertices +
                    ", Edges: " + edges +
                    ", Avg Runtime: " + avgTimeMs + " ms" +
                    ", Avg Memory: " + avgMemoryKB + " KB" +
                    ", Visited: " + visitedCount);
        }
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size * 10);
        }
        return array;
    }

    private static Graph generateRandomConnectedGraph(int vertices, int edges) {
        if (edges < vertices - 1) {
            throw new IllegalArgumentException("Edges must be at least vertices - 1.");
        }

        Graph graph = new Graph(vertices);
        Random rand = new Random();

        for (int i = 0; i < vertices - 1; i++) {
            graph.addEdge(i, i + 1);
        }

        int currentEdges = vertices - 1;

        while (currentEdges < edges) {
            int source = rand.nextInt(vertices);
            int destination = rand.nextInt(vertices);

            if (source != destination &&
                    !graph.getAdjList().get(source).contains(destination)) {
                graph.addEdge(source, destination);
                currentEdges++;
            }
        }

        return graph;
    }

}