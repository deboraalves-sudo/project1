public class SearchTest {
    public static void main(String[] args) {
        int[] testArray = {5, 3, 8, 2, 7, 8}; // last number is the target

        LinearSearch linear = new LinearSearch();
        BinarySearch binary = new BinarySearch();

        System.out.println("Linear Search Result: " + linear.run(testArray)[0]);
        System.out.println("Binary Search Result: " + binary.run(testArray)[0]);
    }
}
