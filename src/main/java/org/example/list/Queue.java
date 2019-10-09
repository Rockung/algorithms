package org.example.list;

/**
 * Simple generic FIFO queue, just support enqueue and dequeue
 *
 * @param <T> the type of data in the queue
 */
public class Queue<T> {
    private Node<T> head;
    private Node<T> tail;

    /**
     * construct an empty queue
     */
    public Queue() {
        this.head = null;
        this.tail = null;
    }

    /**
     * put the data into the queue
     * @param data the data
     */
    public void enQueue(T data) {
        Node<T> node = new Node<>(data);
        if (this.tail == null) { // an empty queue
            this.head = node;
            this.tail = node;
        } else {                // append to the tail
            this.tail.next = node;
            this.tail = node;
        }
    }

    public T deQueue() {
        if (this.head == null) // an empty queue
            throw new IllegalStateException("No contents in the queue.");

        Node<T> node = this.head;

        if (this.head == this.tail) { // only one element
            this.head = null;
            this.tail = null;
        } else {                     // remove the head
            this.head = node.next;
        }

        node.next = null;            // security

        return node.data;
    }

    /**
     * check whether the queue is empty
     *
     * @return true if the head is null
     */
    public boolean isEmpty() {
        return this.head == null;
    }

    /**
     * package data as a node
     * single linked list
     *
     * @param <T> the type of the data
     */
    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();

        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        try {
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        queue.enQueue(4);
        queue.enQueue(5);
        queue.enQueue(6);
        try {
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
            System.out.println(queue.deQueue());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
