package edu.msc.structures;

import java.security.InvalidParameterException;

/**
 * Created by marcelo on 3/25/16.
 * This Stack only accepts Integers as Elements.
 * IF follows First In - Last Out (FILO) approach - or LIFO (Last In, First Out)
 * This implementation ensures that the Stack has a maximum size. If the stack is full, new elements won't be
 * placed in the Stack.
 */
public class NumbersStack implements ContainerList {
    private  int capacity;
    int arr[] ;
    int top = -1;

    public NumbersStack(int capacity) {
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
            throw new Exception("Stack is Full!");
        }

    }


    @Override
    public Integer pop() {
        if (top >= 0) {
            return arr[top--];
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
        StringBuffer sb = new StringBuffer("Elements in NumbersStack : [");
            for (int i = top; i >= 0 ; i--) {
                sb.append(arr[i]);
                if (i > 0) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();

    }
}
