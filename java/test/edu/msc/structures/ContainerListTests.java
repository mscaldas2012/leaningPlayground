package edu.msc.structures;

import org.junit.Test;

import java.util.Collection;

/**
 * Created by marcelo on 3/25/16.
 */
public class ContainerListTests {

    protected void testPush(ContainerList q) throws Exception {
        System.out.println(q.toString());
        q.push(1);
        System.out.println(q.toString());
        q.push(2);
        System.out.println(q.toString());
        q.push(3);
        assert(q.size() == 3);
        System.out.println(q.toString());
    }

    protected void testPop(ContainerList q) throws Exception {
        System.out.println(q.toString());
        q.push(1);
        System.out.println(q.toString());
        q.push(2);
        System.out.println(q.toString());
        q.push(3);
        assert(q.size() == 3);
        System.out.println(q.toString());
        System.out.println("popping: " + q.pop());
        System.out.println(q.toString());
        System.out.println("popping: " + q.pop());
        System.out.println(q.toString());
        System.out.println("popping: " + q.pop());
        System.out.println(q.toString());
    }

    @Test
    public void testNumbersQueue() {
        try {
            ContainerList q = new NumbersQueue(3);
            testPush(q);
            q = new NumbersQueue(3);
            testPop(q);

          } catch (Exception e) {
            assert(false);
        }
    }
    @Test
    public void testStack() {
        try {
            ContainerList s = new InfiniteStack();
            testPop(s);
        } catch (Exception e) {
            assert (false);

        }
    }

    @Test
    public void testJavaStack() {
        java.util.Stack stack = new java.util.Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);

    }


}