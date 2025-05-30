# Kahn's Topological Completion Check (Java)

This project implements **Kahn's Algorithm** to determine whether it’s possible to complete all courses given a list of prerequisites. It checks for cycles in a directed graph using **topological sort**.

---

## Problem Description

You are given `N` courses, labeled `0` to `N-1`, and a list of `M` prerequisite relationships where `A B` means course `A` must be completed before course `B`. Determine if it’s **possible** to finish all courses, i.e., if the graph is **acyclic**.

---

## Sample Input Case #1:

4 3
0 1
1 2
2 3

### Explanation:

- There are 4 courses.
- 3 prerequisites: 0 → 1 → 2 → 3 (a straight line)

---
## Sample Input Case #2:

If you introduce a cycle, for example by adding `3 0`...

4 4
0 1
1 2
2 3
3 0
 → IMPOSSIBLE

### Explanation:
No cycle can exist, as is the case with college courses (and their prerequisites). 
