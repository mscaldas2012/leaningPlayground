package edu.msc.structures;

/**
 * Created by marcelo on 3/25/16.
 *
 *
 */
public interface ContainerList<E> {
    /**
     * Adds newElement to the queue if still has capacity.
     *
     * @param e element to be added to the queu.
     * @throws Exception If Queue is full
     */
    public void push(E e) throws Exception;

    /**
     * Returns the First element in the queue and removes it from the list.
     *
     * @return The First element added to the queu.
     */
    public E pop();

    /**
     * Checks what's the next element to be returned from the list.
     * @return
     */
    public E peek();
    public int size();
}
