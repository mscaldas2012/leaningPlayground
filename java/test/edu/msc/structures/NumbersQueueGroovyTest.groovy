package edu.msc.structures
import edu.msc.structures.NumbersQueue;

/**
 * Created by marcelo on 3/25/16.
 */

class NumbersQueueGroovyTest extends GroovyTestCase {
    void testPush() {
        q =  NumbersQueue.newInstance(3);
        q.push(1);
        q.push(2);
        q.push(3);

    }

    void testPop() {

    }


}
