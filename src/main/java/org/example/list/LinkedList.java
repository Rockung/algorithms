package org.example.list;

/**
 * Linked list
 *   adding an element to the head or the tail
 *   repeatable elements
 *   find the first encountering element
 */
public class LinkedList {

    private Node head;
    private Node tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Get the head of the list
     *
     * @return the head
     */
    public Node head() {
        return this.head;
    }

    /**
     * add an element to the tail
     *
     * @param data
     * @return
     */
    public Node add(int data) {
        Node node = new Node(data);

        if (this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }

        return node;
    }

    /**
     * add an element to the head
     *
     * @param data
     * @return
     */
    public Node addBefore(int data) {
        Node node = new Node(data);

        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }

        return node;
    }

    /**
     * Find a node
     *
     * @param data the data to be found
     * @return if found, return the node, else return null
     */
    public Node find(int data) {
        Node current = this.head;
        while (current != null) {
            if (current.data == data)
                    return current;

            current = current.next;
        }

        return null;
    }

    /**
     * Print the list
     */
    public void printList() {
        Node current = this.head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }

        public int get() {
            return this.data;
        }

        public Node next() {
            return this.next;
        }
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        test03();
    }

    private static void test01() {
        LinkedList list = createList01();
        list.printList();
    }

    private static void test02() {
        LinkedList list = createList01();

        Node node = null;

        node = list.find(2);
        if (node != null) System.out.println("found");

        node = list.find(4);
        if (node == null) System.out.println("not found");

    }

    private static void test03() {
        LinkedList list = createList02();
        list.printList();
    }

    private static LinkedList createList01() {
        LinkedList list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

    private static LinkedList createList02() {
        LinkedList list = new LinkedList();
        list.addBefore(1);
        list.addBefore(2);
        list.addBefore(3);
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

}
