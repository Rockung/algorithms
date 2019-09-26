package org.example.tree;

public class BinarySortTree {
    private Node root;

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};

        BinarySortTree tree = new BinarySortTree();
        for (int val : arr) {
            tree.add(new Node(val));
        }
        tree.infixTraverse();

        System.out.println("=========== after delete ==============");
        tree.delete(1);
        tree.delete(3);
        tree.delete(5);
        tree.delete(9);
        tree.delete(10);
        // tree.delete(12);
        // tree.delete(7);
        tree.infixTraverse();

    }

    public void infixTraverse() {
        if (this.root == null) {
            System.out.println("cannot traverse without a root.");
        } else {
            root.infixTraverse();
        }
    }

    public void add(Node node) {
        if (this.root == null) {
            this.root = node;
        } else {
            this.root.add(node);
        }
    }

    public void delete(int val) {
        if (this.root != null) {
            /**
             * Delete the root is a hard task. The inner node can be self-deleted,
             * but the root cannot. To make sure the parent passed to
             * **Node.deleteChildNode** is not null, the following conditions
             * should be checked.
             */
            if (val == this.root.value) {

                if (this.root.left == null && this.root.right == null) { // no sub-nodes
                    this.root = null;
                    return;
                }

                if (this.root.left != null && this.root.right == null) { // only left node
                    this.root = root.left;
                    return;
                }

                if (this.root.left == null && this.root.right != null) { // only right node
                    this.root = root.right;
                    return;
                }

                // both sub-nodes:
                // it's safety, because **Node.deleteChildNode** doesn't use the parent
            }

            /***
             * After the above checks, even passing a null to parent is ok
             */
            this.root.delete(null, val);
        } else {
            System.out.println("No nodes to be deleted");
        }
    }

    public final static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }

        /**
         * Add a node to the tree
         *
         * @param node
         */
        public void add(Node node) {
            if (node == null) return;

            if (node.value < this.value) { // left sub-tree or node(<)
                if (left == null) {
                    left = node;
                } else {
                    left.add(node);
                }
            } else {                     // right sub-tree or node(>=)
                if (right == null) {
                    right = node;
                } else {
                    right.add(node);
                }
            }
        }

        /**
         * Delete a node with a parent recursively
         * <p>
         * because you have to know the its parent if you want to delete a node,
         * so we pass **parent** node as a parameter in the recursion method.
         * <p>
         * the caller should make sure that parent is not null
         * 1. the inner nodes is ok;
         * 2. the root node has no parent, be careful
         *
         * @param parent the parent of the node
         * @param val
         */
        public void delete(Node parent, int val) {
            if (val == this.value) {                     // I'm the target, kill myself
                deleteChildNode(parent, this);
            } else if (val < this.value) {               // left recursively
                if (this.left != null) {
                    this.left.delete(this, val);
                }
            } else if (val > this.value) {               // right recursively
                if (this.right != null) {
                    this.right.delete(this, val);
                }
            }
        }

        /**
         * Delete a child from a parent
         *
         * @param parent the parent of the child
         * @param child  the child will be deleted
         */
        private void deleteChildNode(Node parent, Node child) {
            if (child.left == null && child.right == null) {         // no sub-nodes
                if (child == parent.left) {
                    parent.left = null;
                } else if (child == parent.right) {
                    parent.right = null;
                }
            } else if (child.left != null && child.right != null) { // two sub-nodes
                // replace the node value with the min value and delete
                // the min node of left subtree on the right sub-node.
                int min = deleteMinNode(child, child.right);
                child.value = min;
            } else {                                                // only one sub-node
                if (child.left != null) {
                    if (child == parent.left) {
                        parent.left = child.left;
                    } else if (child == parent.right) {
                        parent.right = child.left;
                    }
                } else {
                    if (child == parent.left) {
                        parent.left = child.right;
                    } else if (child == parent.right) {
                        parent.right = child.right;
                    }
                }
            }
        }

        /**
         * get the mini-value node from left subtree of **child**
         * and delete this node
         *
         * @param parent
         * @param child
         * @return
         */
        private int deleteMinNode(Node parent, Node child) {
            Node target = child;

            while (target.left != null) {
                parent = target;
                target = target.left;
            }

            if (target.right != null) {
                parent.right = target.right;
            } else {
                parent.right = null;
            }

            return target.value;
        }

        /**
         * infix traverse
         * left->parent->right
         */
        public void infixTraverse() {
            if (left != null) {
                left.infixTraverse();
            }

            System.out.println(this);

            if (right != null) {
                right.infixTraverse();
            }
        }

        @Override
        public String toString() {
            return "Node [value=" + value + "]";
        }
    }
}
