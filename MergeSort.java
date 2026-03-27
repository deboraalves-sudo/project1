public class MergeSort extends Algorithm<int[], int[]> {

    public MergeSort() {
        super("Merge Sort", "Sorting");
    }

    @Override
    public int[] execute(int[] input) {
        mergeSort(input, 0, input.length - 1);
        return input;
    }

    // Recursive merge sort
    private void mergeSort(int[] array, int left, int right) {
        if (left >= right) return; // base case

        int mid = (left + right) / 2;

        mergeSort(array, left, mid);       // sort left half
        mergeSort(array, mid + 1, right);  // sort right half
        merge(array, left, mid, right);    // merge halves
    }

    // Merge two sorted subarrays
    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (array[i] <= array[j]) temp[k++] = array[i++];
            else temp[k++] = array[j++];
        }

        while (i <= mid) temp[k++] = array[i++];
        while (j <= right) temp[k++] = array[j++];

        for (int x = 0; x < temp.length; x++) {
            array[left + x] = temp[x];
        }
    }
}
