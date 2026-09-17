package com.sece.expert.repository;
import com.sece.expert.entity.Studententity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Studententity, Integer> {
    
}
