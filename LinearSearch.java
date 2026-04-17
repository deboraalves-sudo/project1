public class LinearSearch {

    public int[] run(int[] input) {
        int target = input[input.length - 1];

        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == target) {
                return new int[]{i};
            }
        }
        return new int[]{-1};
    }

    public String getName() {
        return "Linear Search";
    }
}
