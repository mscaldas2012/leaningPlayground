package edu.msc.structures;

import java.security.InvalidParameterException;

/**
 * Created by mcq1 on 3/24/2016.
 *
 * Queues follow a First In / First Out (FIFO) paradigm. New elements are placed at the end of the queue.
 * When an element is requested, the first element is given back.
 * This queue only accepts Integers as Elements.
 * This implementation ensures that the Queue has a maximum size. If the queue is full, new elements won't be
 * placed in the queue.
 */
public class NumbersQueue implements ContainerList {
    private  int capacity;
    int arr[] ;
    int top = -1;
    int rear = 0;

    public NumbersQueue(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
    }


    @Override
    public void push(Object newElement) throws Exception {
        if (!(newElement instanceof Integer)) {
            throw new InvalidParameterException("Invlaid Element " + newElement.getClass().getName() +
                    " only accepts Integers. " );
        }
        Integer pushedElement = (Integer)newElement;
        if (top < capacity - 1) {
            arr[++top] = pushedElement;
        } else {
            throw new Exception("Queue is Full!");
        }

    }


    @Override
    public Integer pop() {
        if (top >= rear) {
            return arr[rear++];
        } else {
            //TODO::Log this
            return null;
        }
    }

    @Override
    public Object peek() {
        return arr[top];
    }

    @Override
    public int size() {
        return top +1;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Elements in NumbersQueue : [");
        if (top >= rear) {
            for (int i = rear; i <= top; i++) {
               sb.append(arr[i]);
                if (i < top) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        } else {
            return "Queue is Empty";
        }
    }
}
