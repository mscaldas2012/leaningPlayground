package edu.msc.math;

/**
 * Created by mcq1 on 3/28/2016.
 */
public class Fibonacci {

    public static int fibonacci(int number) {
        if (number <=1) {
            return number;
        }
        return fibonacci(number -2) + fibonacci(number -1);
    }

}
