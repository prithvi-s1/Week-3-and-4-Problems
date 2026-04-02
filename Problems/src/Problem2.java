import java.util.*;

class Client {
    String id;
    int riskScore;
    double accountBalance;

    public Client(String id, int riskScore, double accountBalance) {
        this.id = id;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return id + ":" + riskScore;
    }
}

public class Problem2 {

    public static void main(String[] args) {
        // Sample Input
        Client[] clients = {
                new Client("clientC", 80, 5000.0),
                new Client("clientA", 20, 1500.0),
                new Client("clientB", 50, 12000.0),
                new Client("clientD", 50, 9000.0) // Testing multi-level sort
        };

        System.out.println("--- Risk Management Report ---");

        // 1. Bubble Sort (Ascending) with Swap Visualization
        bubbleSortAscending(Arrays.copyOf(clients, clients.length));

        // 2. Insertion Sort (Descending Score + Account Balance)
        insertionSortDescending(clients);

        // 3. Identify Top Risks
        printTopRisks(clients, 3); // Identifying top 3 for this sample
    }

    /**
     * Bubble Sort: Ascending by Risk Score
     * Demonstration of adjacent swaps and in-place sorting.
     */
    public static void bubbleSortAscending(Client[] arr) {
        int n = arr.length;
        int totalSwaps = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // Swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    totalSwaps++;
                }
            }
        }
        System.out.println("Bubble (asc): " + Arrays.toString(arr) + " // Swaps: " + totalSwaps);
    }

    /**
     * Insertion Sort: Descending by Risk Score, then Descending by Balance
     * Adaptive algorithm: O(n) best case if already sorted.
     */
    public static void insertionSortDescending(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;

            /*
             * Shifting logic for Descending Sort:
             * Move element if current riskScore is LOWER than the key,
             * or if scores are equal, move if balance is LOWER than key.
             */
            while (j >= 0 && (arr[j].riskScore < key.riskScore ||
                    (arr[j].riskScore == key.riskScore && arr[j].accountBalance < key.accountBalance))) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        System.out.println("Insertion (desc): " + Arrays.toString(arr));
    }

    /**
     * Prints the top N risks from a sorted array.
     */
    public static void printTopRisks(Client[] sortedArr, int topN) {
        System.out.print("Top " + topN + " risks: ");
        int limit = Math.min(topN, sortedArr.length);
        for (int i = 0; i < limit; i++) {
            System.out.print(sortedArr[i].id + "(" + sortedArr[i].riskScore + ")" +
                    (i == limit - 1 ? "" : ", "));
        }
        System.out.println();
    }
}