package org.example.list;

import java.util.Iterator;

/**
 * Linked single list with generic data type
 *   adding an element to the head or the tail
 *   repeatable elements
 *   find the first encountering element
 *   enhanced for loop
 */
public class LinkedList<E> implements List<E> {

    // to support insert elements to head or tail
    private Node<E> head;
    private Node<E> tail;

    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    /**
     * return a list from a lot of elements
     *
     * @param elements the elements
     * @param <E> the type of element
     * @return the list
     */
    public static <E> LinkedList<E> of(E... elements) {
        final LinkedList<E> list = new LinkedList<>();

        if (elements.length > 0) {
            for(E e: elements) {
                list.add(e);
            }
        }

        return list;
    }

    /**
     * add an element to the tail
     *
     * @param data
     */
    @Override
    public void add(E data) {
        Node<E> node = new Node<>(data);

        if (this.tail == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
    }

    /**
     * add an element to the head
     *
     * @param data
     */
    @Override
    public void addBefore(E data) {
        Node<E> node = new Node<>(data);

        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
    }

    /**
     * Check whether the list contains the data
     *
     * @param data the data to be checked
     */
    @Override
    public boolean contains(E data) {
        Node<E> current = this.head;
        while (current != null) {
            if (current.data == data)
                    return true;

            current = current.next;
        }

        return false;
    }

    /**
     * Print the list
     */
    public void printList() {
        Node<E> current = this.head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListItr(this.head).iterator();
    }

    /**
     * support enhanced for loop
     *
     * @param <T> the type of data
     */
    private static class ListItr<T> implements Iterable<T> {
        private Node<T> current;

        public ListItr(Node<T> head) {
            this.current = head;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    Node<T> r = current;
                    current = current.next;
                    return r.data;
                }
            };
        }
    }

    /**
     * package data into a node in the list
     *
     * @param <E> the type of the data
     */
    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private static void test01() {
        LinkedList<Integer> list = createList01();
        list.printList();
    }

    private static void test02() {
        LinkedList<Integer> list = createList01();

        if (list.contains(2)) System.out.println("found");
        if (list.contains(4)) System.out.println("not found");
    }

    private static void test03() {
        LinkedList<Integer> list = createList02();
        list.printList();
    }

    private static void test04() {
        List<Integer> list = createList02();

        for(int i: list) {
            System.out.println(i);
        }
    }

    private static void test05() {
        List<String> list = LinkedList.of("hello", "world", "thank you!");
        for(String s: list) {
            System.out.println(s);
        }
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
        // test03();
        // test04();
        test05();
    }
}
