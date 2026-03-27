public class QuickSort extends Algorithm<int[], int[]> {

    public QuickSort() {
        super("Quick Sort", "Sorting");
    }

    @Override
    public int[] execute(int[] input) {
        quickSort(input, 0, input.length - 1);
        return input;
    }

    // Recursive quick sort
    private void quickSort(int[] array, int low, int high) {
        if (low >= high) return; // base case

        int pivotIndex = partition(array, low, high);
        quickSort(array, low, pivotIndex - 1);  // sort left
        quickSort(array, pivotIndex + 1, high); // sort right
    }

    // Partition array around pivot
    private int partition(int[] array, int low, int high) {
        int pivot = array[high]; // choose last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
}
