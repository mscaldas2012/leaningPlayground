package edu.msc.structures;

/**
 * Created by marcelo on 3/25/16.
 * This implementation uses a Linked List for having an infinite queue.
 * (Or as much memory as you might have!)
 *
 */
public class InfiniteQueue<E> implements ContainerList<E> {

    private QueueItem top;
    private QueueItem last;

    @Override
    public void push(E e) throws Exception {
        QueueItem item = new QueueItem(e);
        if (top == null) { //Queue is empty.. initializing with first element
            top = item;
        } else {
            last.setNext(item);
        }
        last = item;
    }

    @Override
    public E pop() {
        if (top == null) { //Queue is empty
            return null;
        }
        QueueItem result = top;
        //Move to the next item in the list...
        top = top.getNext();
        return (E)result.getItem();
    }

    @Override
    public E peek() {
        if (top != null) {
            return (E)top.getItem();
        } else {
            return null;
        }
    }

    @Override
    /**
     * Made a dynamic implementation just for illustration purposes.
     */
    public int size() {
        int size = 0;
        QueueItem walker =top;
        while (walker != null ) {
            size++;
            walker = walker.getNext();
        }
        return size;
    }
    public String toString() {
        StringBuffer sb = new StringBuffer("Elements in InfiniteQueue : [");
        if (top != null) {
            QueueItem walker = top;
            while (walker != null ) {
                sb.append(walker.getItem());
                if (walker.getNext() != null) {
                    sb.append(", ");
                }
                walker = walker.getNext();
            }
            sb.append("]");
            return sb.toString();
        } else {
            return "Queue is Empty";
        }
    }
}

