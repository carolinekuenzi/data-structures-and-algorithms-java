import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * ClosenessCentrality.java
 *
 * Computes closeness centrality for all vertices in a directed, weighted graph,
 * using Dijkstra's algorithm and a custom adaptable priority queue.
 * Outputs the most influential vertex (highest centrality score).
 */
public class ClosenessCentrality {

    // Vertex class representing graph nodes
    static class Vertex implements Comparable<Vertex> {
        public final int id;
        public int distance;
        public Vertex parent;
        public double centrality;
        public Map<Vertex, Edge> outgoing = new HashMap<>();

        public static final int INFINITY = Integer.MAX_VALUE;

        public Vertex(int id) {
            this.id = id;
            this.distance = INFINITY;
            this.parent = null;
        }

        @Override
        public int compareTo(Vertex other) {
            int cmp = Double.compare(other.centrality, this.centrality);
            return (cmp != 0) ? cmp : Integer.compare(this.id, other.id);
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }

        @Override
        public boolean equals(Object o) {
            return (this == o) || (o instanceof Vertex && this.id == ((Vertex) o).id);
        }
    }

    // Edge class representing directed edges with weights
    static class Edge {
        public final int weight;
        public final Vertex to;

        public Edge(Vertex to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // Custom adaptable priority queue for Dijkstra’s algorithm
    static class HeapAdaptablePriorityQueue<K extends Comparable<K>, V> {
        static class AdaptableEntry<K, V> {
            private K key;
            private final V value;

            AdaptableEntry(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() { return key; }
            public V getValue() { return value; }
            public void setKey(K key) { this.key = key; }
        }

        private final Map<K, Queue<AdaptableEntry<K, V>>> map = new HashMap<>();
        private K minKey = null;

        public AdaptableEntry<K, V> insert(K key, V value) {
            AdaptableEntry<K, V> entry = new AdaptableEntry<>(key, value);
            map.computeIfAbsent(key, k -> new LinkedList<>()).add(entry);
            if (minKey == null || key.compareTo(minKey) < 0) {
                minKey = key;
            }
            return entry;
        }

        public boolean isEmpty() {
            return map.isEmpty();
        }

        public AdaptableEntry<K, V> removeMin() {
            if (minKey == null) return null;
            Queue<AdaptableEntry<K, V>> bucket = map.get(minKey);
            AdaptableEntry<K, V> entry = bucket.poll();
            if (bucket.isEmpty()) {
                map.remove(minKey);
                recalculateMinKey();
            }
            return entry;
        }

        public void replaceKey(AdaptableEntry<K, V> entry, K newKey) {
            K oldKey = entry.getKey();
            Queue<AdaptableEntry<K, V>> oldBucket = map.get(oldKey);
            oldBucket.remove(entry);
            if (oldBucket.isEmpty()) {
                map.remove(oldKey);
                if (oldKey.equals(minKey)) recalculateMinKey();
            }
            entry.setKey(newKey);
            map.computeIfAbsent(newKey, k -> new LinkedList<>()).add(entry);
            if (minKey == null || newKey.compareTo(minKey) < 0) {
                minKey = newKey;
            }
        }

        private void recalculateMinKey() {
            minKey = null;
            for (K key : map.keySet()) {
                if (minKey == null || key.compareTo(minKey) < 0) {
                    minKey = key;
                }
            }
        }
    }

    // Main logic class for graph setup and centrality calculation
    static class Graph {
        private final List<Vertex> vertices;

        public Graph(int size) {
            vertices = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                vertices.add(new Vertex(i));
            }
        }

        public void addEdge(int fromId, int toId, int weight) {
            Vertex from = vertices.get(fromId);
            Vertex to = vertices.get(toId);
            from.outgoing.put(to, new Edge(to, weight));
        }

        // Dijkstra's algorithm from a single source
        private Map<Vertex, Integer> dijkstra(Vertex source) {
            Map<Vertex, Integer> visited = new HashMap<>();
            HeapAdaptablePriorityQueue<Integer, Vertex> pq = new HeapAdaptablePriorityQueue<>();
            Map<Vertex, HeapAdaptablePriorityQueue.AdaptableEntry<Integer, Vertex>> entries = new HashMap<>();

            for (Vertex v : vertices) {
                int dist = (v == source) ? 0 : Vertex.INFINITY;
                v.distance = dist;
                v.parent = null;
                entries.put(v, pq.insert(dist, v));
            }

            while (!pq.isEmpty()) {
                HeapAdaptablePriorityQueue.AdaptableEntry<Integer, Vertex> entry = pq.removeMin();
                int dist = entry.getKey();
                Vertex u = entry.getValue();
                if (dist == Vertex.INFINITY) break;

                visited.put(u, dist);

                for (Edge e : u.outgoing.values()) {
                    Vertex t = e.to;
                    if (!visited.containsKey(t)) {
                        int alt = dist + e.weight;
                        if (alt < entries.get(t).getKey()) {
                            pq.replaceKey(entries.get(t), alt);
                            t.distance = alt;
                            t.parent = u;
                        }
                    }
                }
            }

            return visited;
        }

        // Returns ID of the vertex with highest closeness centrality
        public int getMostInfluentialVertex() {
            int n = vertices.size();
            double[] centralitySum = new double[n];

            for (Vertex source : vertices) {
                Map<Vertex, Integer> distances = dijkstra(source);
                for (Map.Entry<Vertex, Integer> e : distances.entrySet()) {
                    Vertex target = e.getKey();
                    int d = e.getValue();
                    if (target.id != source.id && d != Vertex.INFINITY) {
                        centralitySum[target.id] += 1.0 / d;
                    }
                }
            }

            Vertex best = vertices.get(0);
            for (Vertex v : vertices) {
                v.centrality = centralitySum[v.id] * (n - 1);
                if (best.compareTo(v) > 0) {
                    best = v;
                }
            }

            return best.id;
        }
    }

    // Reads input and runs centrality analysis
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] input = reader.readLine().trim().split(" ");
        int numVertices = Integer.parseInt(input[0]);
        int numEdges = Integer.parseInt(input[1]);

        Graph graph = new Graph(numVertices);

        for (int i = 0; i < numEdges; i++) {
            String[] edgeData = reader.readLine().trim().split(" ");
            int from = Integer.parseInt(edgeData[0]);
            int to = Integer.parseInt(edgeData[1]);
            int weight = Integer.parseInt(edgeData[2]);
            graph.addEdge(from, to, weight);
        }

        System.out.println(graph.getMostInfluentialVertex());
        reader.close();
    }
}
