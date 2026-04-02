import java.util.*;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class Problem3 {

    public static void main(String[] args) {
        // Sample Trade Data
        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.println("--- Citi Trading Volume Analysis ---");

        // 1. Merge Sort (Ascending) - Guaranteed O(n log n)
        Trade[] mergeSorted = Arrays.copyOf(trades, trades.length);
        mergeSort(mergeSorted, 0, mergeSorted.length - 1);
        System.out.println("MergeSort (asc): " + Arrays.toString(mergeSorted));

        // 2. Quick Sort (Descending) - Average O(n log n)
        Trade[] quickSorted = Arrays.copyOf(trades, trades.length);
        quickSort(quickSorted, 0, quickSorted.length - 1);
        System.out.println("QuickSort (desc): " + Arrays.toString(quickSorted));

        // 3. Post-sort Analytics: Total Volume
        long totalVolume = calculateTotalVolume(trades);
        System.out.println("Merged morning+afternoon total: " + totalVolume);
    }

    // --- MERGE SORT IMPLEMENTATION (ASCENDING) ---
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {
        Trade[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Trade[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].volume <= rightArr[j].volume) { // Stable: <=
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // --- QUICK SORT IMPLEMENTATION (DESCENDING) ---
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {
        // Using high as pivot (Lomuto Partition)
        int pivot = arr[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Compare for Descending Order
            if (arr[j].volume >= pivot) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static long calculateTotalVolume(Trade[] trades) {
        long sum = 0;
        for (Trade t : trades) sum += t.volume;
        return sum;
    }
}