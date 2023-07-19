package com.example.artaction.domain.repository;

import com.example.artaction.domain.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Long> {
}
