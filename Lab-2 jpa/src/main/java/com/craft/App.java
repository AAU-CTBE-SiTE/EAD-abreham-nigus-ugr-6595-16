package com.craft;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App {

    // The factory is class-wide because it should be created only once
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // CREATE
    public static void createStudent(Student student) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(student);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // READ
    public static Student getStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        Student student = entityManager.find(Student.class, id);
        entityManager.close();
        return student;
    }

    // UPDATE (name only)
    public static void updateStudentName(String newName, int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class, id);
        student.setName(newName);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // UPDATE (name and tech)
    public static void updateStudentNameAndTech(String newName, String newTech, int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class, id);
        student.setName(newName);
        student.setTech(newTech); // the manual has setName(newTech) here by mistake

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // DELETE
    public static void deleteStudentById(int id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Student student = entityManager.find(Student.class, id);
        if (student != null) { // remove(null) would throw an exception
            entityManager.remove(student);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // contains(entity) returns true only if the object is currently MANAGED
    // by this EntityManager (i.e. it was loaded or persisted by it and not yet
    // detached). A plain "new Student()" or an object from another
    // EntityManager is not managed, so contains() returns false.
    public static void containsDemo(int id) {
        EntityManager entityManager = getEntityManager();

        Student newStudent = new Student();
        System.out.println("contains(new Student())        -> " + entityManager.contains(newStudent));

        Student found = entityManager.find(Student.class, id);
        System.out.println("contains(student from find())  -> " + entityManager.contains(found));

        entityManager.detach(found);
        System.out.println("contains(after detach())       -> " + entityManager.contains(found));

        entityManager.close();
    }

    public static void main(String[] args) {
        // Read the student saved in Part 1
        System.out.println("Fetched: " + getStudentById(1));

        // Create
        Student student1 = new Student();
        student1.setName("Mohammed ali");
        student1.setTech("Python");
        createStudent(student1);
        System.out.println("Created: " + student1);

        // Read
        System.out.println("Fetched: " + getStudentById(student1.getId()));

        // Update name
        updateStudentName("Alemayehu", student1.getId());
        System.out.println("After updateStudentName: " + getStudentById(student1.getId()));

        // Update name and tech (on a temporary student), then delete it
        Student student2 = new Student();
        student2.setName("Temp Student");
        student2.setTech("C++");
        createStudent(student2);
        updateStudentNameAndTech("Abebe Bikila", "JavaScript", student2.getId());
        System.out.println("After updateStudentNameAndTech: " + getStudentById(student2.getId()));

        deleteStudentById(student2.getId());
        System.out.println("After deleteStudentById: " + getStudentById(student2.getId()));

        // EntityManager.contains()
        containsDemo(1);

        emf.close();
    }
}
