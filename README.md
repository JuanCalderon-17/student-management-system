# Student Management System

A Java Swing desktop application for managing student records. Supports adding students, enrolling them in courses, assigning grades, and updating their information through a simple GUI.

## Features

- **Add Student** — Register a new student with a unique ID and name
- **Enroll Student** — Enroll a student in one of the available courses (Mathematics, Physics, Chemistry)
- **Assign Grade** — Assign a letter grade to a student for a course they are enrolled in
- **Update Student** — Change a student's name or ID; enrollment and grade data carries over automatically
- **View Student Details** — View all students with their enrolled courses and current grades

## Requirements

- Java 11 or higher
- No external libraries required (uses standard Java SE + Swing)

## How to Run

### In VS Code
1. Open the project folder in VS Code with the Java Extension Pack installed.
2. Click **Run** on the `main` method inside `StudentManagementSystem.java`, or press `F5`.

### From the command line
```bash
# Compile
javac -d bin src/*.java

# Run
java -cp bin StudentManagementSystem
```

## Project Structure

```
StudentManagementSystem/
├── src/
│   ├── StudentManagementSystem.java   # Main window and data layer
│   ├── AddStudentForm.java            # Dialog to add a new student
│   ├── EnrollStudentForm.java         # Dialog to enroll a student in a course
│   ├── AssignGradeForm.java           # Dialog to assign a grade
│   └── UpdateStudentForm.java         # Dialog to update student info
├── bin/                               # Compiled .class files (auto-generated)
└── .vscode/
    └── settings.json
```

## Notes

- Student IDs must be unique. Duplicate IDs are rejected on add and update.
- Grades can only be assigned to courses the student is already enrolled in.
- A student cannot be enrolled in the same course twice.
