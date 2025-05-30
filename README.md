# data-structures-and-algorithms-java
A collection of Java implementations for classic data structures and algorithms.

# Max Value Streaming

This Java program tracks the index of the current maximum value in an array as it receives live updates. Useful for scenarios where fast updates and queries are needed.

### Key Concepts
- PriorityQueue with custom comparator
- Efficient stale entry removal
- BufferedReader / PrintWriter for fast I/O

### Input Format
M N
i1 v1
i2 v2

### Sample Input
5 4
2 10
3 5
2 7
0 10


### Sample Output
2
2
2
0
