package edu.msc.tricks;

/**
 * Created by marcelo on 3/25/16.
 * This class has a method that swaps the value between two variables without using a temporary one.
 *
 * The bet trick is to use XOR operator
 */
public class XORSwap {

    /**
     * the reason this works is as follows:
     *  - XOR operation is:
     *     - Cumulative: A ^ B = B ^ A
     *     - Assocaitive: A ^ (B ^ C) = (A ^ B ) ^ C
     *     -  A ^ 0 = A
     *     - A ^ A = 0
     * @param x first value
     * @param y second value
     * @return an array with x and y inverted. /// Pure didactically
     */
    public static int[] swap(Integer x, Integer y) {
        x = x ^ y; // x = X ^ Y
        y = y ^ x; // Y = Y ^ (X ^ Y) = X ^ (Y ^ Y) = X ^ 0 = X
        x = x ^ y; // X = (X ^ Y) ^ X = Y ^ (X ^ X) = Y ^ 0 = Y
        return new int[] {x, y};
    }

    public static void main(String[] args) {
        Integer x = 5;
        Integer y = 8;
        int[] result = XORSwap.swap(x,y);
        System.out.println(result[0] + " -- > " + result[1]);
        System.out.println(x + " --> " + y);
    }
}
