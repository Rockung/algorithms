package org.example.tree;

public class AVLTree {
    private Node root;

    public static void main(String[] args) {
        // int[] arr = {4, 3, 6, 5, 7, 8};          // left rotation
        // int[] arr = { 10, 12, 8, 9, 7, 6 };   // right rotation
        int[] arr = { 10, 11, 7, 6, 8, 9 };   // double rotation

        AVLTree tree = new AVLTree();
        for (int i : arr) {
            tree.add(i);
        }

        tree.inOrder();
        System.out.println("          tree length: " + tree.root.getHeight());
        System.out.println(" left sub-tree length: " + tree.root.getLeftHeight());
        System.out.println("right sub-tree length: " + tree.root.getRightHeight());
    }


    public void add(int val) {
        if (this.root == null) {
            this.root = new Node(val);
        } else {
            this.root.add(val);
        }
    }

    public void inOrder() {
        if (this.root == null) {
            System.out.println("No contents in the tree.");
        } else {
            this.root.inOrder();
        }
    }

    public final static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }

        public void add(int val) {
            if (val < this.value) {
                if (this.left == null) {
                    this.left = new Node(val);
                } else {
                    this.left.add(val);
                }
            } else {
                if (this.right == null) {
                    this.right = new Node(val);
                } else {
                    this.right.add(val);
                }
            }

            if (this.getRightHeight() - this.getLeftHeight() > 1) {
                if (this.right != null && this.right.getLeftHeight() > this.right.getRightHeight()) {
                    this.right.rightRotate();
                }
                this.leftRotate();
            } else if (this.getLeftHeight() - this.getRightHeight() > 1) {
                if(this.left != null && this.left.getRightHeight() > this.left.getLeftHeight()) {
                    this.left.leftRotate();
                }
                this.rightRotate();
            }
        }

        // left rotation
        private void leftRotate() {
            Node newNode = new Node(this.value);
            newNode.left = this.left;
            newNode.right = this.right.left;

            this.value = this.right.value;
            this.right = this.right.right;
            this.left = newNode;
        }

        // right rotation
        private void rightRotate() {
            Node newNode = new Node(this.value);
            newNode.right = this.right;
            newNode.left = this.left.right;

            this.value = this.left.value;
            this.left = this.left.left;
            this.right = newNode;
        }

        public void inOrder() {
            if (this.left != null) {
                this.left.inOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                right.inOrder();
            }
        }

        public int getLeftHeight() {
            return left == null ? 0 : left.getHeight();
        }

        public int getRightHeight() {
            return right == null ? 0 : right.getHeight();
        }

        public int getHeight() {
            return Math.max(left == null ? 0 : left.getHeight(), right == null ? 0 : right.getHeight()) + 1;
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }
    }

}
