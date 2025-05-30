import java.io.*;
import java.util.*;

/**
 * This Java program maintains an integer array that is updated dynamically.
 * After each update, it outputs the index of the current maximum value.
 * Ties are broken by the smaller index. It uses a PriorityQueue for fast tracking.
 */
public class MaxValueTracker {

    /**
     * Entry class used to track value and index in a max-heap (priority queue).
     * Implements Comparable to ensure max-value entries rise to the top,
     * breaking ties with the smallest index.
     */
    public static class Entry implements Comparable<Entry> {
        int index, value;

        Entry(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Entry other) {
            int diff = Integer.compare(other.value, this.value); // max heap
            if (diff == 0) {
                diff = Integer.compare(this.index, other.index); // tie-break by index
            }
            return diff;
        }
    }

    /**
     * Main method: reads input, processes updates, and prints the current max index.
     * Input:
     *   Line 1: two integers M (size of array) and N (number of updates)
     *   Next N lines: each line contains an index i and a value v to assign
     * Output:
     *   After each update, prints the index of the current maximum value
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken()); // array size
        int N = Integer.parseInt(st.nextToken()); // number of updates

        int[] array = new int[M]; // default to 0s
        PriorityQueue<Entry> pq = new PriorityQueue<>();
        pq.add(new Entry(0, 0)); // initial entry

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            array[i] = v;
            pq.add(new Entry(i, v));

            // Remove stale entries
            while (pq.peek() != null && array[pq.peek().index] != pq.peek().value) {
                pq.poll();
            }

            out.println(pq.peek().index);
        }

        out.close();
    }
}
