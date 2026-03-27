public class BubbleSort extends Algorithm<int[], int[]> {

    public BubbleSort() {
        super("Bubble Sort", "Sorting");
    }

    @Override
    public int[] execute(int[] input) {
        int size = input.length;
        for (int i = 0; i < size - 1; i++) {
            boolean didSwap = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (input[j] > input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                    didSwap = true;
                }
            }
            if (!didSwap) break;
        }
        return input;
    }
}
