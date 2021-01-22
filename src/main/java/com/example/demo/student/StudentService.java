package com.example.demo.student;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service // semantic version of @Component
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long studentId) {
        Optional<Student> studentToUpdate = studentRepository.findById(studentId);

        if (studentToUpdate.isEmpty()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }

        Student student = studentToUpdate.get();
        return student;
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException(student.getEmail() + " already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Optional<Student> studentToUpdate = studentRepository.findById(studentId);
        if (studentToUpdate.isEmpty()) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }

        Student target = studentToUpdate.get();

        if (target.getName() != null && target.getName().length() > 0 && !target.getName().equals(name)) {
            target.setName(name);
        }

        if (target.getEmail() != null && target.getEmail().length() > 0 && !target.getEmail().equals(email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Student with email " + email + " already exists");
            } else {
                target.setEmail(email);
            }
        }
        studentRepository.save(target);
    }
}
