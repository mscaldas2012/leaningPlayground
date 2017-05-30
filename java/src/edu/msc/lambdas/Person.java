package edu.msc.lambdas;

/**
 * Created by caldama on 5/15/17.
 */

public class Person {
        String lastname;
        String firstname;
        int age;

        public Person(String lastname, String firstname, int age) {
            this.lastname = lastname;
            this.firstname = firstname;
            this.age = age;
        }

        public String getLastname() {

            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }


    @Override
    public String toString() {
        return "Person{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", age=" + age +
                '}';
    }
}
