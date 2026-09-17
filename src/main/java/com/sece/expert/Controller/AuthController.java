package com.sece.expert.Controller;

import com.sece.expert.entity.Studententity;
import com.sece.expert.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final StudentRepository studentRepository;

    public AuthController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Studententity student) {
        if (studentRepository.findByUsername(student.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Username already exists"));
        }

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Registration Successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Studententity credentials) {
        Studententity student = studentRepository.findByUsername(credentials.getUsername()).orElse(null);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Username not found"));
        }

        if (!Objects.equals(student.getPassword(), credentials.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid Password"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Login Successful",
                "name", student.getName()));
    }
}