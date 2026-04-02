import java.util.*;

/**
 * Compliance Risk Band Lookup
 * Linear Search: O(n) | Binary Search: O(log n)
 */
public class Problem6 {
    public static void main(String[] args) {
        int[] risks = {10, 25, 50, 100};
        int target = 30;

        // 1. Linear Search for Exact Match
        int linearIdx = -1, linearComps = 0;
        for (int i = 0; i < risks.length; i++) {
            linearComps++;
            if (risks[i] == target) {
                linearIdx = i;
                break;
            }
        }
        System.out.println("Linear Search: target=" + target + " -> " +
                (linearIdx != -1 ? "found at " + linearIdx : "not found") + " (" + linearComps + " comps)");

        // 2. Binary Search for Floor and Ceiling
        int low = 0, high = risks.length - 1, binaryComps = 0;
        int floor = -1, ceiling = -1;

        while (low <= high) {
            binaryComps++;
            int mid = low + (high - low) / 2;

            if (risks[mid] == target) {
                floor = ceiling = risks[mid];
                break;
            } else if (risks[mid] < target) {
                floor = risks[mid]; // Current mid is a candidate for floor
                low = mid + 1;
            } else {
                ceiling = risks[mid]; // Current mid is a candidate for ceiling
                high = mid - 1;
            }
        }

        System.out.println("Binary Search: floor=" + floor + ", ceiling=" + ceiling +
                " (" + binaryComps + " comps) [O(log n)]");

        // Insertion point for new client
        System.out.println("New client insertion point: index " + low);
    }
}