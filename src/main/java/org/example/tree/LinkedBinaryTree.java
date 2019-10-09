package org.example.tree;

import org.example.list.Queue;

/**
 *  A binary tree is an ordered tree with the following properties:
 *    1. every node has at most two children
 *    2. each child node is labeled as being either a **left child** or as
 *       **right child**
 *    3. a left child precedes a right child in the order of children of a node
 *
 *  left subtree/right subtree of v
 *    the subtree rooted at a left or right child of an internal node v
 *
 *  proper/full binary tree
 *    each node has either zero or two children
 *
 *  complete binary tree
 *
 *  properties of binary tree
 *
 *
 *  tasks
 *    1. write for traversing a tree (parent, left, right)
 *       1.1 pre-order traversal      (P->L->R)  from up to bottom, from left to right
 *       1.2 in-order traversal       (L->P->R)
 *       1.3 post-order traversal     (L->R->P)  from left to right, from bottom to up
 *       1.4 breadth-first traversal
 *       1.5 pre-order traversal with empty-filling
 *    2. write for creating a tree
 *       2.1 from an array
 *       2.2 from a pre-order empty-filled series
 *       2.3 from an in-order series and a pre-order series
 *       2.4 from an in-order series and a post-order series
 *
 *  activities
 *    a1. write a linked structure for binary tree
 *    a2. write a static method to create the root manually
 *    a3. write a constructor with root parameter
 *    a4. write the methods for pre-order traversal(P->L->R)
 *    a5. write main method for test
 *    a6. finish in-order and post-order traversal
 *    a7. finish breath-first traversal
 *    a8. finish pre-order traversal with empty-filling
 */
public class LinkedBinaryTree {
    private Node root;

    // a5. write main method for test
    public static void main(String[] args) {
        LinkedBinaryTree tree = new LinkedBinaryTree(createRootManually());
        tree.preOderTraverse();
        System.out.println();

        tree.inOderTraverse();
        System.out.println();

        tree.postOderTraverse();
        System.out.println();

        tree.layerTraverse();  // breath-first
        System.out.println();

        tree.preOderTraverseFilled();
        System.out.println();
    }

    // a3. write a constructor with root parameter
    public LinkedBinaryTree(Node root) {
        this.root = root;
    }

    // a8. finish pre-order traversal with empty-filling
    // if a node has no left or right sub-node, just print '#'
    public void preOderTraverseFilled() {
        if (root != null) {
            preOrderTraversalFilled(root);
        } else {
            System.out.println("no contents in the tree");
        }
    }

    private void preOrderTraversalFilled(Node subtree) {
        if (subtree != null) {
            System.out.print(subtree.data);
            preOrderTraversalFilled(subtree.left);
            preOrderTraversalFilled(subtree.right);
        } else {
            System.out.print("#");
        }
    }

    // a7. finish breath-first traversal
    // use ArrayList as FIFO queue
    public void layerTraverse() {
        if (this.root == null) {
            return;
        }

        Queue<Node> queue = new Queue<>();
        queue.enQueue(this.root);
        while(!queue.isEmpty()) {
            Node node = queue.deQueue();
            System.out.print(node.data + " ");

            if (node.left != null) queue.enQueue(node.left);
            if (node.right != null) queue.enQueue(node.right);
        }
    }

    // a6. finish in-order and post-order traversal
    public void inOderTraverse() {
        if (root != null) {
            inOrderTraversal(root);
        } else {
            System.out.println("no contents in the tree");
        }
    }

    private void inOrderTraversal(Node subtree) {
        if (subtree != null) {
            inOrderTraversal(subtree.left);
            System.out.print(subtree.data + " ");
            inOrderTraversal(subtree.right);
        }
    }

    public void postOderTraverse() {
        if (root != null) {
            postOrderTraversal(root);
        } else {
            System.out.println("no contents in the tree");
        }
    }

    private void postOrderTraversal(Node subtree) {
        if (subtree != null) {
            postOrderTraversal(subtree.left);
            postOrderTraversal(subtree.right);
            System.out.print(subtree.data + " ");
        }
    }

    // a4. write the methods for pre-order traversal(P->L->R)
    public void preOderTraverse() {
        if (root != null) {
            preOrderTraversal(root);
        } else {
            System.out.println("no contents in the tree");
        }
    }

    private void preOrderTraversal(Node subtree) {
        if (subtree != null) {
            System.out.print(subtree.data + " ");
            preOrderTraversal(subtree.left);
            preOrderTraversal(subtree.right);
        }
    }

    // a2. write a static method to create the root manually
    //               A                   PLR: ABDECFG
    //           B      C                LPR: DBEAFGC
    //        D    E  F                  LRP: DEBGFCA
    //                   G             Layer: ABCDEFG
    private static Node createRootManually() {
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');

        // A
        a.left = b;
        a.right = c;

        // B
        b.left = new Node('D');
        b.right = new Node('E');

        // F
        Node f = new Node('F');
        f.right = new Node('G');

        // C
        c.left = f;

        return a;
    }

    // a1. write a linked structure for binary tree
    private static class Node {
        char data;
        Node left;
        Node right;

        public Node(char data) { this.data = data; }
    }
}
