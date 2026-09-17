package com.sece.expert.Controller;

import com.sece.expert.entity.Course;
import com.sece.expert.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable int courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseRepository.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable int courseId,
            @RequestBody Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(courseId);

        if (existingCourse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = existingCourse.get();
        course.setCourseName(updatedCourse.getCourseName());
        course.setDepartment(updatedCourse.getDepartment());
        course.setDuration(updatedCourse.getDuration());
        course.setFees(updatedCourse.getFees());

        return ResponseEntity.ok(courseRepository.save(course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        courseRepository.deleteById(courseId);
        return ResponseEntity.noContent().build();
    }
}