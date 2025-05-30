import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Maintains the index of the current maximum value in an integer array
 * that receives updates. Uses both a custom tracker and a PriorityQueue
 * for efficient stale-entry elimination.
 */
public class ArrayMaxTracker {

    static class Entry implements Comparable<Entry> {
        int index, value;

        Entry(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Entry other) {
            int diff = Integer.compare(other.value, this.value); // max-heap logic
            if (diff == 0) {
                diff = Integer.compare(this.index, other.index); // break ties by lower index
            }
            return diff;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] array = new int[M];
        PriorityQueue<Entry> pq = new PriorityQueue<>();
        pq.add(new Entry(0, 0)); // initial entry

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int i = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            array[i] = v;
            pq.add(new Entry(i, v));

            // Eliminate stale entries
            while (pq.peek() != null && array[pq.peek().index] != pq.peek().value) {
                pq.poll();
            }

            out.println(pq.peek().index);
        }

        out.close();
    }
}
