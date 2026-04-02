import java.util.*;

// 1. Define the Data Model FIRST
class Asset {
    String ticker;
    double returnRate;
    double volatility;

    public Asset(String ticker, double returnRate, double volatility) {
        this.ticker = ticker;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return String.format("%s:%.1f%%", ticker, returnRate);
    }
}

// 2. Define the Logic Class SECOND
public class Problem4 {

    public static void main(String[] args) {
        // Create the array using the Asset symbol defined above
        Asset[] assets = {
                new Asset("AAPL", 12.0, 0.25),
                new Asset("TSLA", 8.0, 0.45),
                new Asset("GOOG", 15.0, 0.20),
                new Asset("MSFT", 12.0, 0.18)
        };

        System.out.println("Initial Portfolio: " + Arrays.toString(assets));

        // Test Merge Sort (Ascending)
        Asset[] mergeData = Arrays.copyOf(assets, assets.length);
        mergeSort(mergeData, 0, mergeData.length - 1);
        System.out.println("Merge Sort (Asc): " + Arrays.toString(mergeData));

        // Test Quick Sort (Descending Return + Ascending Volatility)
        Asset[] quickData = Arrays.copyOf(assets, assets.length);
        quickSort(quickData, 0, quickData.length - 1);
        System.out.println("Quick Sort (Risk-Adjusted Desc): " + Arrays.toString(quickData));
    }

    // --- MERGE SORT (Stable) ---
    public static void mergeSort(Asset[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(Asset[] arr, int l, int m, int r) {
        Asset[] left = Arrays.copyOfRange(arr, l, m + 1);
        Asset[] right = Arrays.copyOfRange(arr, m + 1, r + 1);

        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i].returnRate <= right[j].returnRate) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    // --- QUICK SORT (Unstable but Fast) ---
    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private static int partition(Asset[] arr, int low, int high) {
        // Median-of-3 Pivot selection
        int mid = low + (high - low) / 2;
        int pivotIdx = getMedianIdx(arr, low, mid, high);
        swap(arr, pivotIdx, high);

        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Sort: High Return first; if tied, Lower Volatility first
            if (arr[j].returnRate > pivot.returnRate ||
                    (arr[j].returnRate == pivot.returnRate && arr[j].volatility < pivot.volatility)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static int getMedianIdx(Asset[] arr, int a, int b, int c) {
        double vA = arr[a].returnRate;
        double vB = arr[b].returnRate;
        double vC = arr[c].returnRate;
        if ((vA < vB) ^ (vA < vC)) return a;
        else if ((vB < vA) ^ (vB < vC)) return b;
        else return c;
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}