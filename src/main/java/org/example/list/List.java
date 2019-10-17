package org.example.list;

/**
 * single-forward list which supports add to head or tail
 *
 * @param <E> the element type in the list
 */
public interface List<E> extends Iterable<E> {
    // add data to the tail of the list
    public void add(E data);

    // add data to the head of the list
    public void addBefore(E data);

    // check whether the lis is empty
    public boolean isEmpty();

    // check whether the list contains the data
    public boolean contains(E data);
}
