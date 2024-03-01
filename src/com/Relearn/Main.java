package com.Relearn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Integer five = 5;
        Integer[] others = {0, 5, 10, -50, 50};

        for(Integer i : others){
            int value = five.compareTo(i);

            System.out.printf("%d %s %d compareTo = %d%n", five, (value == 0 ? "==" : (value < 0) ? "<" : ">"), i, value);

            /*
            to see the compareTo values:
            == -> 0 (returns 0 when it's equal to what it's compared to)
            < -> -1 (returns -1 when it's less than what it's compared to)
            > -> 1 (returns 1 when it's greater than what it's compared to)
             */
        }
        System.out.println("-".repeat(12));
        String banana = "banana";
        String[] fruits = {"apple", "banana", "pear", "BANANA"};

        for (String s : fruits){
            int value = banana.compareTo(s);

            System.out.printf("%s %s %s compareTo = %d%n", banana, (value == 0 ? "==" : (value < 0) ? "<" : ">"), s, value);

            /*
            banana > apple compareTo = 1
            banana == banana compareTo = 0
            banana < pear compareTo = -14
            banana > BANANA compareTo = 32

            ?????!
             */
        }

        System.out.println("-".repeat(12));

        //the result looks weird at first...
        Arrays.sort(fruits); // -> sorted as: [BANANA, apple, banana, pear]
        System.out.println(Arrays.toString(fruits));

        System.out.println("-".repeat(12));
        //helping see what's going on...
        System.out.println("A-" + (int) 'A' + " a-" + (int) 'a'); // -> A-65 a-97
        System.out.println("B-" + (int) 'B' + " b-" + (int) 'b'); // -> B-66 b-98
        System.out.println("P-" + (int) 'P' + " p-" + (int) 'p'); // -> P-80 p-112

        //so when we compare strings, it compares the values of the letters as par ASCII, hence our confusing-at-first result above
        //When we compare the values of a string, the compareTo() is actually comparing the char values.
        //It checks the first letters, and if they're the same, it goes on to the second. If they're not the same, like most of the time in our case...
        //then it just returns the 'DIFFERENCE'. So when we compared 'b'anana to 'p'ear, it was comparing 98 to 112,
        // and since they're not the same, it gives us the difference -> -14.
        // This is how the method was designed in Java



        System.out.println("-".repeat(12));

        Student jey = new Student("jey");
        Student[] students = {
                new Student("Zedek"),
                new Student("Damo"),
                new Student("Lover")
        };

        Arrays.sort(students);
        System.out.println(Arrays.toString(students)); // any class that this interface will work on has to somehow implement Comparable, otherwise we get a ClassCastException

        System.out.println("result = " + jey.compareTo(new Student("jey"))); //the difference b/w any same letters but different cases is 32

        System.out.println("-".repeat(35));
        Comparator<Student> gpaSorter = new StudentGPAComparator();
        Arrays.sort(students, gpaSorter.reversed()); //sort(array, comparator) -> It accepts a comparator with which one may want to sort their array. we want the highest to lowest so we reverse it, instead of making it so in our Comparator class (not best practise)
        System.out.println(Arrays.toString(students));

        //so we can create our own custom sorting rules with the Comparator and Comparable interfaces


                            //Comparable vs Comparator
        /*
        *   Comparable's compareTo() compares the argument with the current instance...
            Comparator's compare() compares two arguments of the same type with each other...

        *   Comparable is called from the instance of the class that implements it (Comparable)
            Comparator is called from an instance of Comparator


        *   Comparator does not require the class itself to implement Comparator, like with our Student class, but C...
            Arrays.sort(T[] elements) requires T to implement Comparable  vs  Arrays.sort(T[] elements, Comparator<T>) does not require T to implement Comparator
         */
    }


}

//comparable is to be used when your object has a natural order -> returns 0 when two objects compared're equal
// or the equals() method returns true when the compareTo method returns 0; If you had a list of students which could be easily ID'd by name or id,
// then this interface is cool. You'll compare a lot
class Student implements Comparable<Student>{
    private static int LAST_ID = 1000;
    private static Random random = new Random();
    String name;
    private int id;
    protected double gpa;

    public Student(String name) {
        this.name = name;
        id = LAST_ID++;
        gpa = random.nextDouble(1.0, 4.0);
    }

    @Override
    public String toString() {
//            return super.toString();
        return  "%d - %s (%.2f)".formatted(id, name, gpa);
    }


    @Override
    public int compareTo(Student o) {
        return Integer.valueOf(id).compareTo(Integer.valueOf(o.id)); //id - 0.id
    }


    //this class helps us make our method really comparable, but doesn't stop us from using illegal types ---
//    @Override
//    public int compareTo(Object o) {
//        // return 0; if it always returns 0, then no student is greater thn another, sorting or comparing will not work, it'll just print them in whatever form
//        //we want to compare students by their names
//        Student other = (Student) o;
//        return name.compareTo(other.name);
//    }


}

class StudentGPAComparator implements Comparator<Student>{

    //implementing that compareTo method to compare two students when the class is called... relative to their student IDs
    //comparing their scores, but if there's a tie (same scores), we're comparing their names (alphabetically)
    @Override
    public int compare(Student s1, Student s2){
        return (s1.gpa + s1.name).compareTo(s2.gpa + s2.name);
    }

    //Best made a nested class though...
}
