package com.example.demo.student;

import com.example.demo.exception.BadRequestException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/student")
@Validated
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    @ExceptionHandler(value = NoSuchElementException.class)
    public Student getStudentById(@PathVariable("studentId") Long studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping(path = "email")
    public Student getStudentByEmail(@RequestParam("email") @Email @NotEmpty(message = "Please provide an email") String email) {
        return studentService.getStudentByEmail(email);
    }

    @PostMapping
    @ExceptionHandler(value = BadRequestException.class)
    public void registerNewStudent(@Valid @RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam("name") @NotEmpty(message = "Please provide a name") String name,
            @RequestParam("email") @NotEmpty(message = "Please provide an email") String email
    ) {
        studentService.updateStudent(studentId, name, email);
    }
}
