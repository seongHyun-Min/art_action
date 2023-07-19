package com.example.artaction.domain.repository;

import com.example.artaction.domain.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findById(Long id);
}
