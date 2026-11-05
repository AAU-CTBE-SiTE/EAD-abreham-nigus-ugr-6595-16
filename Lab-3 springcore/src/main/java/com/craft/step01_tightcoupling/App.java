package com.craft.step01_tightcoupling;

// Problem: Tight Coupling
// App creates Student itself with "new", so App is directly coupled to Student.
public class App {
    public static void main(String[] args) {

        Student student = new Student();

        student.getHello();
    }
}
