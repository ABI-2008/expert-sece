package com.sece.expert.Controller;

import com.sece.expert.entity.Studententity;
import com.sece.expert.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Studententity> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello, Students!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Studententity> getStudentById(@PathVariable int id) { 
        Optional<Studententity> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Studententity> createStudent(@RequestBody Studententity student) {
        Studententity savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Studententity> updateStudent(@PathVariable int id, @RequestBody Studententity updatedStudent) {
        Optional<Studententity> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            Studententity student = existingStudent.get();
            student.setName(updatedStudent.getName());
            student.setDepartment(updatedStudent.getDepartment());
            student.setAge(updatedStudent.getAge());
            Studententity savedStudent = studentRepository.save(student);
            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        Optional<Studententity> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

