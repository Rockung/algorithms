package org.example.tree;

public class BinarySortTree {
    private Node root;

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};

        BinarySortTree tree = new BinarySortTree();
        for (int val : arr) {
            tree.add(val);
        }
        tree.inOrder();

        System.out.println("======== after remove ==========");

        tree.remove(9);
        tree.remove(7);
        tree.inOrder();
    }

    public void add(int n) {
        if (this.root == null) {
            this.root = new Node(n);
            return;
        }

        Node parent = null;       // track the parent
        Node current = this.root;
        while(true) {
            parent = current;
            if ( n < current.value) {
                current = current.left;
                if (current == null) {
                    parent.left = new Node(n);
                    break;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = new Node(n);
                    break;
                }
            }
        }
    }

    public void remove(int val) {
        // reduce and replace
        this.root = removeNode(this.root, val);
    }

    public void inOrder() {
        if (this.root == null) {
            System.out.println("cannot traverse without a root.");
            return;
        }

        inOrder(root);
    }

    /**
     * in-order traverse the sub-tree recursively
     *
     * @param node
     */
    private void inOrder(Node node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node);
            inOrder(node.right);
        }
    }

    /**
     * Remove the node of **val** from sub-tree **node** and return the reduced
     * sub-tree. the sub-tree may be reduced to null, or replaced by sub-sub
     * tree, or changed with value.
     *
     * When returning, the node will be assigned to the parent node as a subtree.
     *
     * @param node
     * @param val
     * @return the reduced node that passed in
     */
    private Node removeNode(Node node, int val) {
        if (node == null) return null;

        if (val == node.value) {
            if (node.left == null && node.right == null) {
                return null;          // reduced to null
            }
            if (node.left == null) {
                return node.right;    // reduced to the right sub-tree
            }
            if (node.right == null) {
                return node.left;    // reduced to the left sub-tree
            }

            // the value is replaced by min-value in the right sub-tree
            // the right sub-tree is reduced by the min-value
            Node temp = getSmallest(node.right);
            node.value = temp.value;
            node.right = removeNode(node.right, temp.value);
            return node;
        } else if (val < node.value) {
            // the left sub-tree is reduced
            node.left = removeNode(node.left, val);
            return node;
        } else {
            // the right -sub-tree is reduced
            node.right = removeNode(node.right, val);
            return node;
        }
    }

    /**
     *  get the smallest node from left sub-tree of **node**
     *
     * @param node
     * @return
     */
    private Node getSmallest(Node node) {
        Node target = node;

        while (target.left != null) {
            target = target.left;
        }

        return target;
    }

    public final static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }
    }
}
