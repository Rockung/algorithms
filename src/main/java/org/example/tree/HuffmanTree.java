package org.example.tree;

import java.util.ArrayList;
import java.util.Collections;

public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {1,3,6,7,8,13,29};

        Node node = createTree(arr);
        node.preOrderTraverse();
    }

    public final static Node createTree(int[] arr) {
        // convert an int array to node list
        ArrayList<Node> nodes = new ArrayList<>();
        for (int val: arr) {
            nodes.add(new Node(val));
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes); // sort ascending

            // get the first two nodes as children
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            // remove the two nodes
            nodes.remove(0);
            nodes.remove(0);

            // add the parent to the list, the parent keep the tree
            nodes.add(parent);
        }

        return nodes.get(0); // the root of the tree
    }

    public final static class Node implements Comparable<Node> {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }

        /**
         * pre-order traverse
         *   parent->left->right
         */
        public void preOrderTraverse() {
            System.out.println(this);

            if (left != null) {
                left.preOrderTraverse();
            }

            if (right != null) {
                right.preOrderTraverse();
            }
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }

        @Override
        public int compareTo(Node o) {
            return this.value - o.value; // ascending
        }
    }
}
