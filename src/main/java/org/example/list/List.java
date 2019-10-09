package org.example.list;

public interface List<T> extends Iterable<T> {
    // add data to the tail of the list
    public void add(T data);

    // add data to the head of the list
    public void addBefore(T data);

    // check whether the lis is empty
    public boolean isEmpty();

    // check whether the list contains the data
    public boolean contains(T data);
}
