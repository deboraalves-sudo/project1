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
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size * 10);
        }
        return array;
    }
}