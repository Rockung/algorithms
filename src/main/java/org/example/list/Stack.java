package org.example.list;

/**
 * Simple generic stack, just support push and pop
 *
 * @param <T> the type of items which the stack contains
 */
public class Stack<T> {
    private Item<T> top;

    /**
     * construct an empty stack
     */
    public Stack() {
        this.top = null;
    }

    /**
     * push an item onto the top of stack
     *
     * @param data the item
     */
    public void push(T data) {
        Item<T> item = new Item<>(data);

        if (this.top == null) {
            this.top = item;
        } else {
            item.next = this.top;
            this.top = item;
        }
    }

    /**
     * pop the top of stack out
     *
     * @return the data
     * @throws IllegalStateException if the stack is empty
     */
    public T pop() throws IllegalStateException {
        if (this.top == null)
                throw new IllegalStateException("Stack is empty.");

        Item<T> item = this.top;
        this.top = item.next();
        item.next = null;

        return item.get();
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
        try {
            stack.push(1);
            stack.push(2);
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        stack.push(3);
        System.out.println(stack.pop());
    }

    public static void main(String[] args) {
        test01();
    }
}
