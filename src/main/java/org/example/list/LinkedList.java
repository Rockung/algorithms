package org.example.list;

/**
 * Linked list with generic data type
 *   adding an element to the head or the tail
 *   repeatable elements
 *   find the first encountering element
 */
public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Get the head of the list
     *
     * @return the head
     */
    public Node<T> head() {
        return this.head;
    }

    /**
     * add an element to the tail
     *
     * @param data
     * @return the node containing the data
     */
    public Node<T> add(T data) {
        Node<T> node = new Node<>(data);

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
     * @return the node containing the data
     */
    public Node<T> addBefore(T data) {
        Node<T> node = new Node<>(data);

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
    public Node<T> find(T data) {
        Node<T> current = this.head;
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
        Node<T> current = this.head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T get() {
            return this.data;
        }

        public Node<T> next() {
            return this.next;
        }
    }

    private static void test01() {
        LinkedList<Integer> list = createList01();
        list.printList();
    }

    private static void test02() {
        LinkedList<Integer> list = createList01();

        Node<Integer> node = null;

        node = list.find(2);
        if (node != null) System.out.println("found");

        node = list.find(4);
        if (node == null) System.out.println("not found");

    }

    private static void test03() {
        LinkedList<Integer> list = createList02();
        list.printList();
    }

    private static LinkedList createList01() {
        LinkedList<Integer> list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

    private static LinkedList createList02() {
        LinkedList<Integer> list = new LinkedList();
        list.addBefore(1);
        list.addBefore(2);
        list.addBefore(3);
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

    public static void main(String[] args) {
        // test01();
        // test02();
        test03();
    }
}
