package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.Student;
import org.example.service.StudentService;
import org.example.validator.custom_validator.StudentValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final StudentValidator studentValidator;

    @GetMapping("/create")
    public String createStudent(Student student, Model model) {
        model.addAttribute("student", student);

        return "createStudent";
    }

    @PostMapping("/create")
    public String createStudent(@Valid Student student, BindingResult bindingResult) throws SQLException {
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
    public String getStudents(Model model) throws SQLException {
        Map<String, List<Student>> students = studentService.getStudents();
        model.addAttribute("students", students);

        return "studentInfo";
    }

    @GetMapping("/delete")
    public String deleteStudentGet(Student student, Model model) {
        model.addAttribute("id", student.getId());

        return "deleteStudent";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam Integer id, Model model) throws SQLException {
        String url;

        if (studentValidator.isValidID(id)) {
            studentService.deleteStudent(id);

            url = "redirect:/student/delete";
        } else {
            System.out.println("ID is not valid");

            model.addAttribute("id", id);

            url = "deleteStudent";
        }
        return url;
    }
}


