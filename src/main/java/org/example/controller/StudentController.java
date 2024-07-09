package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Student;
import org.example.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controller for student management.
 * Processes requests to create, get and delete students.
 */
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/create")
    public String createStudentGet(Student student, Model model) {
        model.addAttribute("student", student);

        return "createStudent";
    }

    @PostMapping("/create")
    public String createStudentPost(@Valid Student student, BindingResult bindingResult) throws SQLException {
        String url;

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error);
            }
            url = "createStudent";
        } else {
            studentService.createStudent(student);
            url = "redirect:/student/create";
        }
        return url;
    }

    @GetMapping("/get")
    public String getStudentsGet(Student student, Model model) throws SQLException {
        Map<String, List<Student>> students = studentService.getStudents();
        model.addAttribute("students", students);
        model.addAttribute("student", student);

        return "studentInfo";
    }

    @GetMapping("/delete")
    public String deleteStudentGet(Student student, Model model) {
        model.addAttribute("student", student);

        return "deleteStudent";
    }

    @PostMapping("/delete")
    public String deleteStudentPost(@Valid Student student, BindingResult bindingResult) throws SQLException {
        String url;

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error);
            }
            url = "deleteStudent";
        } else {
            studentService.deleteStudent(student.getId());
            url = "redirect:/student/delete";
        }

        return url;
    }
}
