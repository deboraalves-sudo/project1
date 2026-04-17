import java.util.Arrays;

public class BinarySearch {

    public int[] run(int[] input) {
        int target = input[input.length - 1];

        int[] array = Arrays.copyOf(input, input.length - 1);
        Arrays.sort(array);

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (array[mid] == target) {
                int value = array[mid];
                for (int i = 0; i < input.length - 1; i++) {
                    if (input[i] == value) {
                        return new int[]{i};
                    }
                }
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new int[]{-1};
    }

    public String getName() {
        return "Binary Search";
    }
}
