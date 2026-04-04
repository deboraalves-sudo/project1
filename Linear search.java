public class LinearSearch implements Algorithm<int[], int[]> {

    @Override
    public int[] run(int[] input) {
        int target = input[input.length - 1];

        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == target) {
                return new int[]{i};
            }
        }
        return new int[]{-1};
    }

    @Override
    public String getName() {
        return "Linear Search";
    }
}
