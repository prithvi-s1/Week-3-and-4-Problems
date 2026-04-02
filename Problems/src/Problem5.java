import java.util.*;

public class Problem5 {
    public static void main(String[] args) {
        String[] logs = {"accA", "accB", "accB", "accC"};
        String target = "accB";

        // 1. Linear Search (First/Last Occurrence)
        int firstIdx = -1, lastIdx = -1, linearComps = 0;
        for (int i = 0; i < logs.length; i++) {
            linearComps++;
            if (logs[i].equals(target)) {
                if (firstIdx == -1) firstIdx = i;
                lastIdx = i;
            }
        }
        System.out.println("Linear Search - First: " + firstIdx + ", Last: " + lastIdx + ", Comparisons: " + linearComps + " [O(n)]");

        // 2. Binary Search (Requires Sorting)
        Arrays.sort(logs); // Ensure sorted for Binary Search
        int low = 0, high = logs.length - 1, binaryIdx = -1, binaryComps = 0;
        while (low <= high) {
            binaryComps++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(logs[mid]);
            if (res == 0) {
                binaryIdx = mid;
                break;
            } else if (res > 0) low = mid + 1;
            else high = mid - 1;
        }

        // Count occurrences using binary bounds
        int count = 0;
        if (binaryIdx != -1) {
            int temp = binaryIdx;
            while (temp >= 0 && logs[temp].equals(target)) { count++; temp--; }
            temp = binaryIdx + 1;
            while (temp < logs.length && logs[temp].equals(target)) { count++; temp++; }
        }
        System.out.println("Binary Search - Index: " + binaryIdx + ", Count: " + count + ", Comparisons: " + binaryComps + " [O(log n)]");
    }
}