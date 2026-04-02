import java.util.*;

/**
 * Banking Transaction Fee-Sorting System
 * Optimized for Compliance Audit and Fraud Detection
 */
public class Problem1 {

    // Transaction Model
    static class Transaction {
        String id;
        double fee;
        long timestamp; // Minutes from start of day or epoch

        public Transaction(String id, double fee, long timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return String.format("%s:%.1f", id, fee);
        }
    }

    public static void main(String[] args) {
        // Sample Input
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, 600)); // 10:00
        transactions.add(new Transaction("id2", 25.0, 570)); // 09:30
        transactions.add(new Transaction("id3", 5.0, 615));  // 10:15

        System.out.println("--- Banking Audit Report ---");

        // 1. Bubble Sort: Small Batches (n <= 100)
        // Complexity: O(n^2), Best Case: O(n) with early termination
        runBubbleSort(new ArrayList<>(transactions));

        // 2. Insertion Sort: Medium Batches (100 - 1000)
        // Multi-level sort: Fee (Ascending) then Timestamp (Ascending)
        runInsertionSort(new ArrayList<>(transactions));

        // 3. High-Fee Outlier Detection
        checkOutliers(transactions);
    }

    /**
     * Optimized Bubble Sort for Fee sorting.
     * Uses early termination if no swaps occur in a pass.
     */
    public static void runBubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;
        int passes = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                // Stable comparison: only swap if strictly greater
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        System.out.println("BubbleSort (fees): " + list + " // " + passes + " passes, " + swaps + " swaps");
    }

    /**
     * Insertion Sort for Fee + Timestamp sorting.
     * Builds a sorted subarray by shifting larger elements to the right.
     */
    public static void runInsertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            /* * Logic: Shift elements if:
             * 1. Fee is higher OR
             * 2. Fee is equal but timestamp is later (Maintaining stability)
             */
            while (j >= 0 && (list.get(j).fee > key.fee ||
                    (list.get(j).fee == key.fee && list.get(j).timestamp > key.timestamp))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }

        // Formatted output to show timestamps for verification
        System.out.print("InsertionSort (fee+ts): [");
        for (int i = 0; i < list.size(); i++) {
            Transaction t = list.get(i);
            String ts = String.format("%02d:%02d", t.timestamp / 60, t.timestamp % 60);
            System.out.print(t.id + ":" + t.fee + "@" + ts + (i == list.size() - 1 ? "" : ", "));
        }
        System.out.println("]");
    }

    /**
     * Flags transactions exceeding the $50 threshold.
     */
    public static void checkOutliers(List<Transaction> list) {
        List<String> outliers = new ArrayList<>();
        for (Transaction t : list) {
            if (t.fee > 50.0) {
                outliers.add(t.id);
            }
        }
        System.out.println("High-fee outliers: " + (outliers.isEmpty() ? "none" : outliers));
    }
}