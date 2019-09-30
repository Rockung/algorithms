package org.example.tree;

import java.util.Arrays;

/**
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
 *
 *  Build a tabled tree and a Huffman code table
 */
public class TabledHuffmanTree {
    private char[] chars;
    private int[] weights;

    private HuffTableRow[] huffTable;

    // a code table is [n][2] 2-d array
    // n: chars.length
    // [*][0]: the code
    // [*][1]: the length of the code
    private int[][] codeTable;

    public static void main(String[] args) {
        System.out.println("" + (1 << 3));
        TabledHuffmanTree tree = createHuffmanTree();
        tree.buildHuffTable();
        tree.printHuffTable();
    }

    /**
     * Construct a tabled Huffman tree
     * chars[] and weights[] should have same length
     *
     * @param chars   the characters in the text to be encoded
     * @param weights the weights of characters in the text
     * @throws IllegalArgumentException
     */
    public TabledHuffmanTree(char[] chars, int[] weights) throws IllegalArgumentException {
        if (chars.length != weights.length)
            throw new IllegalArgumentException("The numbers are not equal");

        this.chars = Arrays.copyOf(chars, chars.length);
        this.weights = Arrays.copyOf(weights, weights.length);
        this.huffTable = createHuffTable();
    }

    /**
     * With n-1 iterations, build the Huffman tree table:
     *   1. select two minimum weights, construct a new node as a parent
     *   2. add the node to the left nodes and jump back to 1.
     *
     * Build the Huffman code table from the above table
     */
    public void buildHuffTable() {
        int n = chars.length;

        for (int i = 0; i < n - 1; i++) {
            int w1 = Integer.MAX_VALUE; // min
            int w2 = Integer.MAX_VALUE; // second-min
            int x1 = -1;                // index of min
            int x2 = -1;                // index of second-min

            // find the min and second-min weights
            for (int j = 0; j < n + i; j++) {
                if (huffTable[j].weight < w1 && huffTable[j].parent == -1) {
                    w2 = w1;
                    x2 = x1;

                    w1 = huffTable[j].weight;
                    x1 = j;
                } else if (huffTable[j].weight < w2 && huffTable[j].parent == -1) {
                    w2 = huffTable[j].weight;
                    x2 = j;
                }
            }

            // fill the Huffman table with a new node
            huffTable[n + i].weight = w1 + w2; // sum of two weights
            huffTable[n + i].lchild = x1;      // index of left child
            huffTable[n + i].rchild = x2;      // index of right child
            huffTable[x1].parent = n + i;      // index of parent
            huffTable[x2].parent = n + i;      //
        }

        buildCodeTable();
    }

    /**
     * Build the Huffman code table from the leaf node
     */
    private void buildCodeTable() {
        int rows = this.chars.length;

        // [*][0]: the code
        // [*][1]: the length of the code
        this.codeTable = new int[rows][2];
        for (int i = 0; i < rows; i++) {
            int child = i;
            int parent = huffTable[child].parent;
            int level = 0;
            while (parent != -1) {
                // 0: for left child
                // 1: for right child
                if (huffTable[parent].rchild == child)
                    codeTable[i][0] += (1 << level);

                child = parent;
                parent = huffTable[child].parent;
                level++;
            }
            codeTable[i][1] = level;
        }
    }

    public void printHuffTable() {
        //
        System.out.println("index\tweight\tparent\tlchild\trchild");
        for (int i = 0; i < huffTable.length; i++) {
            HuffTableRow row = huffTable[i];
            System.out.printf("%d\t%d\t%d\t%d\t%d\t", i, row.weight, row.parent, row.lchild, row.rchild);
            System.out.println();
        }

        // print the Huffman coding table
        System.out.println("Huffman coding table");
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i] + ": " + convertToHuffCode(codeTable[i][0], codeTable[i][1]));
        }
    }

    /**
     * Convert an integer to binary code with a defined length
     *
     * @param code integer code
     * @param len  defined length
     * @return binary code
     */
    private String convertToHuffCode(int code, int len) {
        StringBuilder builder = new StringBuilder(Integer.toBinaryString(code));

        while (builder.length() < len) {
            builder.insert(0, '0'); // insert '0' at the front
        }

        return builder.toString();
    }

    /**
     *  An example of Huffman-tree table
     *
     * index weight parent lchild rchild char code
     *   00    5      06     -1     -1     a   1000
     *   01   32      09     -1     -1     b   11
     *   02   18      08     -1     -1     c   00
     *   03    7      06     -1     -1     d   1001
     *   04   25      08     -1     -1     e   01
     *   05   13      07     -1     -1     f   101
     *   06   12      07     00     03
     *   07   25      09     06     05
     *   08   43      10     02     04
     *   09   57      10     07     01
     *   10  100      -1     08     09
     * # of characters: 6, # of rows: 2*6-1=11
     * WPL: weighted path length
     *
     * @return an array of HuffTableRow
     */
    private HuffTableRow[] createHuffTable() {
        int rows = this.chars.length;
        HuffTableRow[] huffTable = new HuffTableRow[2 * rows - 1];

        for (int i = 0; i < rows; i++) {
            huffTable[i] = new HuffTableRow(this.weights[i]);
        }

        for (int i = rows; i < 2 * rows - 1; i++) {
            huffTable[i] = new HuffTableRow(0);
        }

        return huffTable;
    }

    private static TabledHuffmanTree createHuffmanTree() {
        return new TabledHuffmanTree(
                new char[]{'a', 'b', 'c', 'd', 'e', 'f'},
                new int[]{5, 32, 18, 7, 25, 13}
        );
    }

    private static class HuffTableRow {
        int weight;
        int parent;
        int lchild;
        int rchild;

        public HuffTableRow(int weight) {
            this.weight = weight;
            this.parent = -1;
            this.lchild = -1;
            this.rchild = -1;
        }
    }
}
