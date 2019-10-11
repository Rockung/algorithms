package org.example.tree;

import java.util.Arrays;

/**
 * A heap is a binary tree inside an array, so it does not use parent/child
 * pointers. A heap is sorted based on the "heap property" that determines
 * the order of the nodes in the tree.
 *
 * There are two kinds of heaps: a max-heap and a min-heap which are different
 * by the order in which they store the tree nodes.
 *
 * In a max-heap, parent nodes have a greater value than each of their children.
 * In a min-heap, every parent node has a smaller value than its child nodes.
 * This is called the "heap property", and it is true for every single node in
 * the tree.
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {
    // the underline array
    private Array<E> data;

    /**
     * construct a heap with a capacity
     *   when it reaches, it grows
     *
     * @param capacity the capacity allocated at first
     */
    public MaxHeap(int capacity) {
        this.data = new Array<>(capacity);
    }

    /**
     * return # of elements in the heap
     *
     * @return # of elements
     */
    public int size() {
        return this.data.getSize();
    }

    /**
     * check whether there are elements in the heap
     *
     * @return true, if no elements
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * return the capacity of the heap
     *
     * @return the capacity
     */
    public int capacity() {
        return this.data.getCapacity();
    }

    /**
     * add an element to the heap
     *
     * @param e the element
     */
    public void add(E e) {
        // add to the last and sift up
        this.data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    /**
     * peek the top element of the heap
     *
     * @return the top element
     */
    public E peek(){
        if(data.getSize() == 0)
            throw new IllegalArgumentException("The heap is empty.");

        return data.get(0);
    }

    /**
     *  remove the top element of the heap
     *
     * @return the top element
     */
    public E remove(){
        E ret = peek();

        // exchange the top element with the last one,
        // remove the last one and sift down
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    private int parent(int index) {
        if (index == 0)
            throw new IllegalArgumentException("inde 0 does not hava a parent.");

        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        // index<<1 does not work
        return index * 2 + 1;
    }

    /**
     * the index of right child equals the one of left child plus one
     *
     * @param index
     * @return
     */
    private int rightChild(int index) {
        // index<<1 does not work
        return index * 2 + 2;
    }

    /**
     * adjust the tree upward
     *
     * @param k from the position k up to the top
     */
    private void siftUp(int k) {
        while (k > 0) {
            int parent = parent(k);

            // child <= parent, break
            // child > parent, continue
            if (data.get(parent).compareTo(data.get(k)) >= 0)
                    break;

            // exchange child with parent
            this.data.swap(k, parent);

            k = parent;
        }
    }

    /**
     * adjust the tree downward
     *
     * @param k from the position k down to the bottom
     */
    private void siftDown(int k) {
        // down to the leaf node from left subtree
        int j = leftChild(k);

        while(j < data.getSize()) {
            // exchange with the maximum of left child and right child
            int right = j + 1;

            // right < data.getSize() indicates right child exists
            if(right < data.getSize()) {
                // right child > left child
                if (data.get(right).compareTo(data.get(j)) > 0 ) {
                    j++;
                }
            }

            // parent >= child, break
            // parent < child, continue
            if(data.get(k).compareTo(data.get(j)) >= 0 )
                break;

            // exchange
            data.swap(k, j);

            k = j;
            j = leftChild(k);
        }
    }

    /**
     * auto-growing array
     * the growing policy is copied from java PriorityQueue
     *
     * @param <E> the type of element
     */
    private static class Array<E> {
        private int size;
        private E[] data;

        public Array(int capacity) {
            if (capacity < 1) throw new IllegalArgumentException("The capacity is too small");

            this.data = (E[]) new Object[capacity];
            this.size = 0;
        }

        public void addLast(E e) {
            if (this.size >= this.data.length) {
                grow(this.size);
            }

            this.data[this.size] = e;
            this.size++;
        }

        public void removeLast() {
            this.size--;
        }

        public void swap(int i, int j) {
            E temp = this.data[i];
            this.data[i] = this.data[j];
            this.data[j] = temp;
        }

        public E get(int index) {
            return this.data[index];
        }

        public int getSize() {
            return this.size;
        }

        public int getCapacity() {
            return this.data.length;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        /**
         * The maximum size of array to allocate.
         * Some VMs reserve some header words in an array.
         * Attempts to allocate larger arrays may result in
         * OutOfMemoryError: Requested array size exceeds VM limit
         */
        private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

        /**
         * Increases the capacity of the array.
         *
         * @param minCapacity the desired minimum capacity
         */
        private void grow(int minCapacity) {
            int oldCapacity = this.data.length;
            // Double size if small; else grow by 50%
            int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                    (oldCapacity + 2) :
                    (oldCapacity >> 1));
            // overflow-conscious code
            if (newCapacity - MAX_ARRAY_SIZE > 0)
                newCapacity = hugeCapacity(minCapacity);
            this.data = Arrays.copyOf(this.data, newCapacity);
        }

        private static int hugeCapacity(int minCapacity) {
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return (minCapacity > MAX_ARRAY_SIZE) ?
                    Integer.MAX_VALUE :
                    MAX_ARRAY_SIZE;
        }
    }

    public static void main(String[] args) {
        MaxHeap<Integer> heap = new MaxHeap<>(5);
        heap.add(1);
        heap.add(3);
        heap.add(2);
        heap.add(5);
        heap.add(4);
        System.out.println("     max: " + heap.peek());
        System.out.println("    size: " + heap.size());
        System.out.println("capacity: " + heap.capacity());
        heap.add(6);
        System.out.println("     max: " + heap.peek());
        System.out.println("    size: " + heap.size());
        System.out.println("capacity: " + heap.capacity());

        System.out.println("  remove: " + heap.remove());
        System.out.println("  remove: " + heap.remove());
        System.out.println("  remove: " + heap.remove());
        System.out.println("  remove: " + heap.remove());
        System.out.println("  remove: " + heap.remove());
        System.out.println("  remove: " + heap.remove());

        // exception
        System.out.println("  remove: " + heap.remove());
    }

}
