import java.util.Arrays;

public class BinarySearch implements Algorithm<int[], int[]> {

    @Override
    public int[] run(int[] input) {
        int target = input[input.length - 1];

        int[] array = Arrays.copyOf(input, input.length - 1);
        Arrays.sort(array);

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (array[mid] == target) {
                return new int[]{mid};
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new int[]{-1};
    }

    @Override
    public String getName() {
        return "Binary Search";
    }
}
