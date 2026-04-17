import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Project1 {

    static List<Integer> sortingSizes = new ArrayList<>();
    static List<Long> bubbleTimes = new ArrayList<>();
    static List<Long> mergeTimes = new ArrayList<>();
    static List<Long> quickTimes = new ArrayList<>();

    static List<Integer> searchSizes = new ArrayList<>();
    static List<Long> linearTimes = new ArrayList<>();
    static List<Long> binaryTimes = new ArrayList<>();

    static List<Integer> graphSizes = new ArrayList<>();
    static List<Long> dfsTimes = new ArrayList<>();
    
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

        for (int i = 0; i < testArrays.length; i++) {
            int[] original = testArrays[i];
            sortingSizes.add(original.length);
            System.out.println("=== Array Size: " + sizes[i] + " ===");

            for (Algorithm<int[], int[]> algo : algorithms) {
                int[] arrayCopy = original.clone();
                long startTime = System.nanoTime();
                algo.execute(arrayCopy);
                long endTime = System.nanoTime();
                long timeTaken = endTime - startTime;

                if (algo.getName().equals("Bubble Sort")) {
                    bubbleTimes.add(timeTaken);
                } else if (algo.getName().equals("Merge Sort")) {
                    mergeTimes.add(timeTaken);
                } else if (algo.getName().equals("Quick Sort")) {
                    quickTimes.add(timeTaken);
                }

                System.out.println(algo.getName() + " result (first 20 elements): " +
                        Arrays.toString(Arrays.copyOf(arrayCopy, Math.min(20, arrayCopy.length))));
                System.out.println("Execution time: " + (endTime - startTime) + " ns\n");
            }
        }

        System.out.println("\n=== Search Performance ===");

        int[] searchSizerArr = {100, 1000, 5000, 10000};

        for (int size: searchSizerArr) {
            int[] arr = generateRandomArray(size);

            int randomIndex = (int)(Math.random() * (arr.length - 1));
            arr[arr.length - 1] = arr[randomIndex];

            LinearSearch linear = new LinearSearch();
            BinarySearch binary = new BinarySearch();

            long start, end;

            start = System.nanoTime();
            int linRes = linear.run(arr)[0];
            end = System.nanoTime();
            long linearTime = end - start;

            start = System.nanoTime();
            int binRes = binary.run(arr)[0];
            end = System.nanoTime();
            long binaryTime = end - start;

            searchSizes.add(size);
            linearTimes.add(linearTime);
            binaryTimes.add(binaryTime);

            System.out.println("Size: " + size +
                " | Linear: " + linearTime +
                " ns | Binary: " + binaryTime + " ns");
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
            Project1.graphSizes.add(vertices);
            dfsTimes.add((long) totalTime / 5);

            System.out.println("Vertices: " + vertices +
                    ", Edges: " + edges +
                    ", Avg Runtime: " + avgTimeMs + " ms" +
                    ", Avg Memory: " + avgMemoryKB + " KB" +
                    ", Visited: " + visitedCount);
        }
            SwingUtilities.invokeLater(() -> {
                PerformanceGraphs graphs = new PerformanceGraphs();
                graphs.setVisible(true);
            });
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

class PerformanceGraphs extends JFrame {

    public PerformanceGraphs() {
        setTitle("Algorithm Performance Analysis");
        setSize(1200, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        add(new GraphPanel(
                "Sorting Performance",
                "Input Size",
                "Execution Time (ns)",
                Project1.sortingSizes,
                java.util.List.of(Project1.bubbleTimes, Project1.mergeTimes, Project1.quickTimes),
                java.util.List.of("Bubble Sort", "Merge Sort", "Quick Sort"),
                java.util.List.of(
                        new Color(220, 53, 69),   // red
                        new Color(0, 123, 255),   // blue
                        new Color(40, 167, 69)    // green
                )
        ));

        add(new GraphPanel(
                "Search Performance",
                "Input Size",
                "Execution Time (ns)",
                Project1.searchSizes,
                java.util.List.of(Project1.linearTimes, Project1.binaryTimes),
                java.util.List.of("Linear Search", "Binary Search"),
                java.util.List.of(
                        new Color(111, 66, 193),  // purple
                        new Color(255, 140, 0)    // orange
                )
        ));

        add(new GraphPanel(
                "Graph Traversal Performance (DFS)",
                "Number of Vertices",
                "Execution Time (ns)",
                Project1.graphSizes,
                java.util.List.of(Project1.dfsTimes),
                java.util.List.of("DFS Traversal"),
                java.util.List.of(
                        new Color(33, 37, 41)     // dark gray / black
                )
        ));
    }
}

class GraphPanel extends JPanel {
    private final String title;
    private final String xAxisLabel;
    private final String yAxisLabel;
    private final java.util.List<Integer> xValues;
    private final java.util.List<java.util.List<Long>> ySeries;
    private final java.util.List<String> labels;
    private final java.util.List<Color> colors;

    public GraphPanel(String title,
                      String xAxisLabel,
                      String yAxisLabel,
                      java.util.List<Integer> xValues,
                      java.util.List<java.util.List<Long>> ySeries,
                      java.util.List<String> labels,
                      java.util.List<Color> colors) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.yAxisLabel = yAxisLabel;
        this.xValues = xValues;
        this.ySeries = ySeries;
        this.labels = labels;
        this.colors = colors;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        int leftMargin = 90;
        int rightMargin = 220;
        int topMargin = 50;
        int bottomMargin = 70;

        int graphWidth = width - leftMargin - rightMargin;
        int graphHeight = height - topMargin - bottomMargin;

        // Title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.drawString(title, leftMargin, 30);

        // Axes
        g2.setColor(Color.BLACK);
        g2.drawLine(leftMargin, topMargin, leftMargin, topMargin + graphHeight);
        g2.drawLine(leftMargin, topMargin + graphHeight, leftMargin + graphWidth, topMargin + graphHeight);

        // Find max Y value
        long maxY = 1;
        for (java.util.List<Long> series : ySeries) {
            for (Long value : series) {
                if (value > maxY) {
                    maxY = value;
                }
            }
        }

        // Draw horizontal grid lines and Y-axis labels
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        for (int i = 0; i <= 5; i++) {
            int y = topMargin + graphHeight - (i * graphHeight / 5);
            long yValue = maxY * i / 5;

            g2.setColor(new Color(220, 220, 220));
            g2.drawLine(leftMargin, y, leftMargin + graphWidth, y);

            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(yValue), 15, y + 5);
        }

        // Draw X-axis labels and vertical guide lines
        if (!xValues.isEmpty()) {
            for (int i = 0; i < xValues.size(); i++) {
                int x = leftMargin + (i * graphWidth / Math.max(1, xValues.size() - 1));

                g2.setColor(new Color(235, 235, 235));
                g2.drawLine(x, topMargin, x, topMargin + graphHeight);

                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(xValues.get(i)), x - 15, topMargin + graphHeight + 20);
            }
        }

        // Plot all series
        for (int s = 0; s < ySeries.size(); s++) {
            java.util.List<Long> series = ySeries.get(s);
            g2.setColor(colors.get(s));

            for (int i = 0; i < series.size(); i++) {
                int x = leftMargin + (i * graphWidth / Math.max(1, series.size() - 1));
                int y = topMargin + graphHeight - (int) ((series.get(i) * 1.0 / maxY) * graphHeight);

                // Point
                g2.fillOval(x - 4, y - 4, 8, 8);

                // Line to next point
                if (i < series.size() - 1) {
                    int nextX = leftMargin + ((i + 1) * graphWidth / Math.max(1, series.size() - 1));
                    int nextY = topMargin + graphHeight - (int) ((series.get(i + 1) * 1.0 / maxY) * graphHeight);
                    g2.drawLine(x, y, nextX, nextY);
                }
            }
        }

        // Legend box
        int legendX = leftMargin + graphWidth + 20;
        int legendY = topMargin + 20;

        g2.setColor(new Color(245, 245, 245));
        g2.fillRoundRect(legendX - 10, legendY - 20, 180, 30 + labels.size() * 25, 12, 12);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(legendX - 10, legendY - 20, 180, 30 + labels.size() * 25, 12, 12);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString("Legend", legendX, legendY);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (int i = 0; i < labels.size(); i++) {
            int itemY = legendY + 20 + (i * 22);

            g2.setColor(colors.get(i));
            g2.fillRect(legendX, itemY - 10, 14, 14);

            g2.setColor(Color.BLACK);
            g2.drawRect(legendX, itemY - 10, 14, 14);
            g2.drawString(labels.get(i), legendX + 22, itemY + 2);
        }

        // Axis labels
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2.drawString(xAxisLabel, leftMargin + (graphWidth / 2) - 30, height - 15);

        Graphics2D g2Rotated = (Graphics2D) g2.create();
        g2Rotated.rotate(-Math.PI / 2);
        g2Rotated.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2Rotated.drawString(yAxisLabel, -(topMargin + graphHeight / 2 + 40), 25);
        g2Rotated.dispose();
    }
}
