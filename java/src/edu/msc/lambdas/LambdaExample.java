package edu.msc.lambdas;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by caldama on 5/15/17.
 */
public class LambdaExample {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Charles:", "Dickens", 60),
                new Person("Lewis", "Carroll", 42),
                new Person("Thomas", "Carlyle", 51),
                new Person("Charlotte", "Bronte", 45),
                new Person("Matthew", "Arnold", 39)
        );

        Collections.sort(people, (p1, p2) -> p1.getLastname().compareTo(p2.getLastname()));
        printConditionally(people, p ->true, p -> System.out.println(p));
        System.out.println("Cs:");
        printConditionally(people, p -> p.getLastname().startsWith("C"), p -> System.out.println(p.getFirstname()));

    }

    public static void printConditionally(List<Person> people, Predicate<Person> predicate, Consumer<Person> consumer) {
        for (Person p: people) {
            if (predicate.test(p)) {
                consumer.accept(p);
            }
        }
    }
}







