package org.example;

import org.example.model.Student;
import org.example.repository.StudentRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new StudentRepository();

        //studentRepository.createStudent(new Student("john", "john@gmail.com", 30));

        //studentRepository.updateStudent();

        //studentRepository.deleteStudent();

        studentRepository.getStudentById(2L);


    }
}
