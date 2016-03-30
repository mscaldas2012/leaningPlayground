package edu.msc.structures;

/**
 * Created by marcelo on 3/25/16.
 *  This implementation uses a Linked List for having an infinite Stack.
 * (Or as much memory as you might have!)
 *
 */
public class InfiniteStack<E> implements ContainerList<E> {
    private QueueItem top;

    @Override
    public void push(E e) throws Exception {
        QueueItem newItem = new QueueItem(e);
        if (top == null) { //Initializing Stack...
            top = newItem;
        } else {
            newItem.setNext(top);
            top = newItem;

        }
    }

    @Override
    public E pop() {
        if (top != null) {
            QueueItem result = top;
            top = top.getNext();
            return (E)result.getItem();
        }
        return null;
    }

    @Override
    public E peek() {
        if (top == null) {
            return (E)top.getItem();
        }
        return null;
    }

    @Override
    /**
     * Implementation for didatical purposes only...
     */
    public int size() {
        QueueItem walker = top;
        int size = 0;
        while (walker != null) {
            size++;
            walker = walker.getNext();
        }
        return size;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Elements in InfiniteStack : [");
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
            return "Stack is Empty";
        }
    }
}
