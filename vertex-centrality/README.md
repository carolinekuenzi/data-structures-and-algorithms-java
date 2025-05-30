# Closeness Centrality in a Directed Graph (Java)

This Java project calculates the **closeness centrality** of all vertices in a directed, weighted graph using **Dijkstra’s algorithm** and a custom **adaptable priority queue**. It returns the **most influential vertex**, i.e., the one with the highest centrality score.

---

## Problem Overview

Given a weighted, directed graph, the program:
1. Runs Dijkstra’s algorithm from every vertex to calculate shortest paths.
2. For each node, calculates closeness centrality:

### centrality(v) = sum of (1 / distance from every other node to v)

3. Normalizes scores and returns the vertex with the highest centrality (breaking ties by ID).

---

### Sample Input
5 6
0 1 1
1 2 1
2 3 1
3 4 1
4 0 1
0 2 5

### Sample Output
2

---

### Explanation
Vertex `2` has the best balance of closeness to all other nodes.

