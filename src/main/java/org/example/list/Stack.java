package org.example.list;

/**
 * Simple generic stack
 *
 * @param <T> the type of items which the stack contains
 */
public class Stack<T> {
    private int size;
    private Item<T> top;

    /**
     * construct an empty stack
     */
    public Stack() {
        this.size = 0;
        this.top = null;
    }

    /**
     * push an item onto the top of stack
     * cannot push an null onto the stack
     *
     * @param data the item
     * @throws IllegalArgumentException
     */
    public void push(T data) throws IllegalArgumentException {
        if (data == null)
            throw new IllegalArgumentException("The argument can not be null, cannot push.");

        Item<T> item = new Item<>(data);

        if (this.top == null) {
            this.top = item;
        } else {
            item.next = this.top;
            this.top = item;
        }

        this.size++;
    }

    /**
     * pop the top of stack out
     *
     * @return the data
     * @throws IllegalStateException if the stack is empty
     */
    public T pop() throws IllegalStateException {
        if (this.top == null)
                throw new IllegalStateException("The stack is empty, cannot pop.");

        Item<T> item = this.top;
        this.top = item.next();
        item.next = null;

        this.size--;

        return item.get();
    }

    /**
     * check whether the stack is empty
     *
     * @return true, if top is null
     */
    public boolean isEmpty() {
       return this.top == null;
    }

    /**
     * return # of items in the stack
     *
     * @return # of items in the stack
     */
    public int size() {
        return this.size;
    }

    /**
     * return the top of the stack.
     * if the return value is null, the stack is empty
     *
     * @return the top of the stack
     */
    public T top() throws IllegalStateException {
        if (this.top == null) return null;

        return this.top.data;
    }

    /**
     * Package data as an item
     *
     * @param <T> the type of item
     */
    private static class Item<T> {
        private T data;
        private Item<T> next;

        public Item(T data) {
            this.data = data;
            this.next = null;
        }

        public T get() {
            return this.data;
        }

        public Item<T> next() {
            return this.next;
        }
    }

    private static void test01() {
        Stack<Integer> stack = new Stack<>();
        System.out.println(stack.isEmpty());

        try {
            stack.push(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            stack.push(1);
            stack.push(2);
            System.out.println("size: " + stack.size());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        stack.push(3);
        System.out.println(stack.isEmpty());
        System.out.println(stack.pop());

        System.out.println("size: " + stack.size());
        System.out.println(stack.isEmpty());
        System.out.println(stack.top());
    }

    public static void main(String[] args) {
        test01();
    }
}
