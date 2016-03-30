package edu.msc.structures;

import org.junit.Test;

/**
 * Created by marcelo on 3/25/16.
 */
public class InfiniteStackTest {
    @Test
    public void testPush() throws Exception {
        ContainerList q = new InfiniteStack();
        System.out.println(q.toString());
        q.push(1);
        System.out.println(q.toString());
        q.push(2);
        System.out.println(q.toString());
        q.push(3);
        assert(q.size() == 3);
        System.out.println(q.toString());
    }

    @Test
    public void testPop() throws Exception {
        ContainerList q = new InfiniteStack();
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
    public void testPeek() throws Exception {

    }
}
