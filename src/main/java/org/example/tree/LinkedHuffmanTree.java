package org.example.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Huffman tree and code
 * =====================
 * <p>
 * Huffman tree generated from the exact frequencies of the text "this is an
 * example of a huffman tree".
 * <p>
 * The frequencies and codes of each character are below. Encoding the sentence
 * with this code requires 195 (or 147) bits, as opposed to 288 (or 180) bits
 * if 36 characters of 8 (or 5) bits were used. (This assumes that the code tree
 * structure is known to the decoder and thus does not need to be counted as
 * part of the transmitted information.)
 * <p>
 * see https://en.wikipedia.org/wiki/Huffman_coding
 * <p>
 * Build a linked tree and a Huffman code table
 * ============================================
 * <p>
 * when you transfer a message between nodes, you encode the message at one
 * end and decode at the other end. you compute the frequencies of each
 * character in the message, build a Huffman code table for this set.
 * <p>
 * <p>
 * How to build the Huffman code table
 * ===================================
 * <p>
 * 1. build a Huffman tree from the weights of characters
 * <p>
 * 1.1. initialize the set of weight nodes for characters
 * 1.2. pick out the two smallest nodes from the set
 * 1.3. construct a new node with the weights of the sum of the above one
 * 1.4. add the new node to the set, repeat step 2-4 until the set has
 * only one node
 * 1.5. build a binary tree which root is the only one node
 * <p>
 * 2. build a Huffman code table from Huffman tree
 * for each character
 * 2.1. set huff-code to ""
 * 2.2. locate it's parent from its leaf weight node
 * 2.3. if parent.right == node
 * set huff-code = "1" + huff-code
 * else
 * set huff-code = "0" + huff-code
 * 3.4. repeat from bottom to up until parent is null
 * 3.5. huff-code is the Huffman code for the character
 */
public class LinkedHuffmanTree {
    private char[] chars;
    private Node[] weightNodes;

    private Node root;
    private String[] codes;

    public static void main(String[] args) {
        LinkedHuffmanTree tree = createHuffmanTree();
        tree.buildHuffTree();
        tree.preOrderTraverse();
        tree.buildCodeTable();
        tree.printCodeTable();
    }

    public LinkedHuffmanTree(char[] chars, int[] weights) {
        if (chars.length != weights.length)
            throw new IllegalArgumentException("The lengths are not eqaul");

        this.chars = Arrays.copyOf(chars, chars.length);
        this.weightNodes = new Node[chars.length];

        for (int i = 0; i < weights.length; i++) {
            this.weightNodes[i] = new Node(weights[i]);
        }
    }

    public final void buildHuffTree() {
        // add the leaf nodes to the list
        ArrayList<Node> nodes = new ArrayList<>();
        for (Node node : this.weightNodes) {
            nodes.add(node);
        }

        while (nodes.size() > 1) {
            Collections.sort(nodes); // sort ascending

            // get the first two nodes as children
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            // construct the parent node
            Node parent = new Node(leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            leftNode.parent = parent;
            rightNode.parent = parent;

            // remove the two nodes
            nodes.remove(0);
            nodes.remove(0);

            // add the parent to the list, the parent keep the tree
            nodes.add(parent);
        }

        // set the root
        this.root = nodes.get(0);
    }

    public void buildCodeTable() {
        this.codes = new String[this.chars.length];

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < weightNodes.length; i++) {

            // from leaf node to the root
            Node child = weightNodes[i];
            Node parent = child.parent;
            while (parent != null) {
                if (parent.right == child) {
                    builder.insert(0, '1'); // right edge -> 1
                } else {
                    builder.insert(0, '0'); // left edge -> 0
                }

                child = parent;
                parent = child.parent;
            }

            this.codes[i] = builder.toString();
            builder.delete(0, builder.length());
        }
    }

    public void printCodeTable() {
        System.out.println("Huffman coding table");
        for (int i = 0; i < chars.length; i++) {
            System.out.println("\t" + chars[i] + ": " + codes[i]);
        }
    }

    public void preOrderTraverse() {
        if (this.root != null) {
            this.root.preOrderTraverse();
        } else {
            System.out.println("No contents");
        }
    }

    private static LinkedHuffmanTree createHuffmanTree() {
        return new LinkedHuffmanTree(
                new char[]{'a', 'b', 'c', 'd', 'e', 'f'},
                new int[]{5, 32, 18, 7, 25, 13}
        );
    }

    public final static class Node implements Comparable<Node> {
        int weight;

        Node parent;
        Node left;
        Node right;

        public Node(int weight) {
            this.weight = weight;
        }

        /**
         * pre-order traverse
         * parent->left->right
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
            return "Node [value=" + weight + "]";
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight; // ascending
        }
    }
}
