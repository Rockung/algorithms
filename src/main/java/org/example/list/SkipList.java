package org.example.list;

import java.util.Random;

/**
 * A simple skip-list
 *
 * See https://www.bilibili.com/video/av64757276/?p=61
 */
public class SkipList {

    private Node head;
    private Node tail;
    private int size;
    private int height;
    private Random random;

    public SkipList() {
        this.head = new Node(null, TYPE_HEAD);
        this.tail = new Node(null, TYPE_TAIL);
        this.head.right = this.tail;
        this.tail.left = this.head;
        this.random = new Random(System.currentTimeMillis());
    }

    public Integer get(Integer element) {
        Node node = this.locate(element);
        return (node.value == element) ? node.value : null;
    }

    public boolean contains(Integer element) {
        Node node = this.locate(element);
        return (node.value == element);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return this.size;
    }

    /**
     * add an element into the list
     *
     * @param element
     */
    public void add(Integer element) {
        Node locNode = this.locate(element);
        Node newNode = new Node(element);

        // insert into the underlying linked list
        newNode.left = locNode;
        newNode.right = locNode.right;
        locNode.right.left = newNode;
        locNode.right = newNode;

        // lift up
        int currLevel = 0;
        while (random.nextDouble() < 0.3d) {
            if (currLevel >= height) {
                this.height++;

                // create a level
                Node dumyHead = new Node(null, TYPE_HEAD);
                Node dumyTail = new Node(null, TYPE_TAIL);
                dumyHead.right = dumyTail;
                dumyTail.left = dumyHead;

                // link up level with down level
                head.up = dumyHead;
                tail.up = dumyTail;
                dumyHead.down = head;
                dumyTail.down = tail;

                // switch to the new level
                head = dumyHead;
                tail = dumyTail;
            }

            // climb the ladder from left
            while ((locNode != null) && locNode.up == null) {
                locNode = locNode.left;
            }
            locNode = locNode.up;

            // insert the node into the new level
            Node upNode = new Node(element);
            upNode.left = locNode;
            upNode.right = locNode.right;
            upNode.down = newNode;

            locNode.right.left = upNode;
            locNode.right = upNode;
            newNode.up = upNode;

            // continue to lift up
            newNode = upNode;
            currLevel++;
        }

        // count the elements
        size++;
    }

    /**
     * display the skip-list structure
     */
    public void dumpSkipList() {
        Node temp = head;
        int i = height + 1;
        while (temp != null) {
            System.out.printf("Total: %d, height: %d", height + 1, i--);
            Node node = temp.right;
            while (node.type == TYPE_DATA) {
                System.out.printf("->%d ", node.value);
                node = node.right;
            }
            System.out.printf("\n");
            temp = temp.down;
        }
        System.out.println("========================================");
    }

    /**
     * locate the place after which the element will be added
     *
     * @param element the element to be added
     * @return the place
     */
    private Node locate(Integer element) {
        Node curr = head;

        for (; ; ) {
            while (curr.right.type != TYPE_TAIL
                    &&  element >= curr.right.value) {
                curr = curr.right;
            }

            if (curr.down != null) {
                curr = curr.down;
            } else {
                break;
            }
        }

        return curr; // curr <= element < curr.right(if exist)
    }

    private final static byte TYPE_HEAD = (byte) -1;
    private final static byte TYPE_DATA = (byte) 0;
    private final static byte TYPE_TAIL = (byte) 1;

    private static class Node {
        private Integer value;
        private Node up, down, left, right;
        private byte type;

        public Node(Integer value) {
            this(value, TYPE_DATA);
        }

        public Node(Integer value, byte type) {
            this.value = value;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        SkipList list = new SkipList();

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            list.add(random.nextInt(100));
        }

        list.dumpSkipList();
    }
}
