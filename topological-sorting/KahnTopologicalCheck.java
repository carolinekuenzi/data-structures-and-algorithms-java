import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * KahnTopologicalCheck.java
 *
 * Determines whether all courses can be completed given a set of prerequisite relationships.
 * Implements Kahn's Algorithm for detecting cycles in a directed graph using topological sort.
 */
public class KahnTopologicalCheck {

    /**
     * Directed graph representation with adjacency list and in-degree tracking.
     */
    static class CourseGraph {
        private final List<List<Integer>> graph;
        private final int[] inDegree;
        private final int size;

        public CourseGraph(int size) {
            this.size = size;
            this.inDegree = new int[size];
            this.graph = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                graph.add(new ArrayList<>());
            }
        }

        /**
         * Adds a prerequisite edge from course A (prereq) to course B.
         */
        public void addPrerequisite(int prereq, int course) {
            graph.get(prereq).add(course);
            inDegree[course]++;
        }

        /**
         * Returns true if it is possible to complete all courses (i.e., no cycles).
         * Implements Kahn's algorithm (BFS) for topological sorting.
         */
        public boolean isCompletionPossible() {
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < size; i++) {
                if (inDegree[i] == 0) queue.offer(i);
            }

            int visitedCount = 0;
            while (!queue.isEmpty()) {
                int course = queue.poll();
                visitedCount++;

                for (int dependent : graph.get(course)) {
                    inDegree[dependent]--;
                    if (inDegree[dependent] == 0) {
                        queue.offer(dependent);
                    }
                }
            }

            return visitedCount == size;
        }
    }

    /**
     * Reads the input and prints "possible" if all courses can be completed, otherwise "IMPOSSIBLE".
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstLine = reader.readLine().trim().split("\\s+");
        int numCourses = Integer.parseInt(firstLine[0]);
        int numPrereqs = Integer.parseInt(firstLine[1]);

        CourseGraph graph = new CourseGraph(numCourses);

        for (int i = 0; i < numPrereqs; i++) {
            String[] edge = reader.readLine().trim().split("\\s+");
            int prereq = Integer.parseInt(edge[0]);
            int course = Integer.parseInt(edge[1]);
            graph.addPrerequisite(prereq, course);
        }

        System.out.println(graph.isCompletionPossible() ? "possible" : "IMPOSSIBLE");

        reader.close();
    }
}
