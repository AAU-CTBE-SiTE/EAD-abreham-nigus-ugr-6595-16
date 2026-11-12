package com.craft;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// The TARGET object: it only contains business logic.
// Logging, validation, timing and caching are cross-cutting concerns, so they live in the aspects.
@Service
public class StudentService {

    private final Map<Integer, Student> students = new LinkedHashMap<>();

    public Student addStudent(Student student) {
        System.out.println("    StudentService.addStudent() is running");
        students.put(student.getId(), student);
        return student;
    }

    public Student getStudent(int id) {
        System.out.println("    StudentService.getStudent() is running (reading from the store)");
        return students.get(id);
    }

    // @TrackTime is our own annotation; PerformanceAspect times every method that has it
    @TrackTime("load all students")
    public List<Student> getAllStudents() {
        System.out.println("    StudentService.getAllStudents() is running");
        try {
            Thread.sleep(100); // pretend this is slow work, like a database query
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ArrayList<>(students.values());
    }

    public void deleteStudent(int id) {
        System.out.println("    StudentService.deleteStudent() is running");
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("Student " + id + " not found");
        }
        students.remove(id);
    }

    // Self-invocation: this calls addStudent() on "this" object, not on the Spring proxy,
    // so the advice on addStudent() does NOT run for these inner calls
    public void registerAll(List<Student> list) {
        System.out.println("    StudentService.registerAll() is running");
        for (Student student : list) {
            addStudent(student);
        }
    }
}
