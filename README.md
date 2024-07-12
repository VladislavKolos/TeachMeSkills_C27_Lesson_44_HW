# TeachMeSkills_C27_Lesson_44_HW
Homework for lesson #44

1. **Task #1**

A Spring Boot web-application has been created to display information about students in a group.
Web-app contains:
- Utility class **"PostgresUtil"** - component for managing connection to a PostgreSQL database;
- Student data model class **"Student"**. Contains student information such as ID, name, email and group;
- Service class **"StudentService"** for student management. Contains methods for creating, getting and deleting students; 
- Controller class **"StudentController"** for student management. Processes requests to create, get and delete students;
- Exception handler class **"StudentExceptionHandler"** - exception handler for controllers associated with operations on students;
- Custom annotation **"EmailUniquenessCheck"** for checking the uniqueness of email;
- Custom validator class **"EmailUniquenessValidator"** Validator for checking the uniqueness of email. Contains validation logic to check email uniqueness;
- Existence validator class **"ExistenceStudentValidator"** for checking the existence of a student in the database. Checks whether the student with the specified email exists in the database;
- Main application class **"Application"** for running a Spring Boot application.

- JSP-pages **"createStudent.html"**, **"studentInfo.html"** and **"deleteStudent.html"** for users interaction;
- JSP-pages **"runtimeExceptionError.html"**, **"nullPointerExceptionError.html"**, **"ioExceptionError.html"**, **"servletExceptionError.html"** and **"anotherExceptionError.html"** to display error messages to users when exceptions occur.

- Resources **"TMS_C27_students.sql"** and **"TMS_C27_students.txt"** files contain a script for creating the "students" table with "id", "name", "email" and "group" fields in the "TMS_C27 database".
