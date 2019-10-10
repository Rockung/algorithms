package org.example.list;

/**
 *
 * @param <T>
 *
 *  See https://www.bilibili.com/video/av47473308/?p=2
 */
public interface Vector<T> {
    // get # of elements in the vector
    int size();

    // get the element of rank r
    T get(int r);

    // replace the element of rank r with e
    void put(int r, T e);

    // insert e before rank r
    void insert(int r, T e);

    // remove the element of rank r and return the element
    T remove(int r);

    // find rank of the element of e
    int find(T e);

    // search e and return the element which is less than or equal to e
    // with the greatest rank
    T search(T e);

    //
    void sort();
    void traverse();
}
