package org.example.tree;

public class PriorityQueue<E extends Comparable<E>> {
    private MaxHeap<E> heap;

    public PriorityQueue() {
        this.heap = new MaxHeap<>(10);
    }

    public void enQueue(E e) {
        this.heap.add(e);
    }

    public E deQueue() {
        return this.heap.remove();
    }

    public int size() {
        return this.heap.size();
    }

    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    public static void main(String[] args) {
        PriorityQueue queue = new PriorityQueue();
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(5);
        queue.enQueue(3);
        queue.enQueue(4);

        while (!queue.isEmpty()) {
            System.out.println("dequeue: " + queue.deQueue());
        }
    }
}
