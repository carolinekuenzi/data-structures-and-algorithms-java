import java.io.*;
import java.util.*;

/**
 * EvenSubtreePruner.java
 *
 * Given a binary tree, this program removes all subtrees whose nodes all contain even values.
 * The tree is constructed from a level-order input, with "null" used for missing nodes.
 * The resulting tree is printed in level-order format.
 */
public class EvenSubtreePruner {

    // Binary Tree Node
    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Returns true if the entire subtree rooted at this node consists only of even values.
     */
    private static boolean isAllEven(TreeNode node) {
        if (node == null) return true;
        if (node.val % 2 != 0) return false;
        return isAllEven(node.left) && isAllEven(node.right);
    }

    /**
     * Recursively deletes subtrees made entirely of even-valued nodes.
     */
    public static TreeNode deleteTree(TreeNode root) {
        if (root == null) return null;
        if (isAllEven(root)) return null;

        root.left = deleteTree(root.left);
        root.right = deleteTree(root.right);
        return root;
    }

    /**
     * Reads a binary tree from input and prints the pruned tree in level order.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());  // Number of elements
        Integer[] A = new Integer[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; ++i) {
            String token = st.nextToken();
            A[i] = token.equals("null") ? null : Integer.parseInt(token);
        }

        // Build binary tree from level-order array
        TreeNode root = buildTree(A);

        // Remove all-even subtrees
        root = deleteTree(root);

        // Print the result
        printTreeLevelOrder(root);
    }

    /**
     * Builds a binary tree from a level-order array with nulls.
     */
    private static TreeNode buildTree(Integer[] A) {
        if (A.length == 0 || A[0] == null) return null;

        TreeNode root = new TreeNode(A[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i = 1;

        while (!queue.isEmpty() && i < A.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < A.length) {
                Integer val = A[i++];
                if (val != null) {
                    current.left = new TreeNode(val);
                    queue.add(current.left);
                }
            }

            // Right child
            if (i < A.length) {
                Integer val = A[i++];
                if (val != null) {
                    current.right = new TreeNode(val);
                    queue.add(current.right);
                }
            }
        }

        return root;
    }

    /**
     * Prints the binary tree in level-order with "null" for missing nodes.
     */
    private static void printTreeLevelOrder(TreeNode root) throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        if (root == null) {
            out.print("null");
            out.close();
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean hasMore = true;

        while (hasMore) {
            int size = queue.size();
            hasMore = false;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node == null) {
                    out.print("null ");
                    queue.add(null);
                    queue.add(null);
                } else {
                    out.print(node.val + " ");
                    queue.add(node.left);
                    queue.add(node.right);
                    if (node.left != null || node.right != null) {
                        hasMore = true;
                    }
                }
            }
        }

        out.close();
    }
}
