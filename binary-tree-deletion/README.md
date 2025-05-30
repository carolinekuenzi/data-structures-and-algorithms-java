# Even Subtree Pruner (Java)

This project removes subtrees from a binary tree if **all** nodes in that subtree contain even values. The tree is input in level-order, and the final pruned tree is printed in level-order format.


## Problem Description

You are given a binary tree (in level-order format) where each node is either an integer or `"null"`. The task is to remove any subtree where **every node has an even value**, including the root of that subtree.


## Sample Input

9
4 2 6 8 null null 5 null null

## Sample Output

4 null 6 null 5

### Explanation
- Subtree rooted at `2` (`2 → 8`) is removed because all values are even.
- `6` is kept because its right child is `5` (an odd number), making the subtree not entirely even.

