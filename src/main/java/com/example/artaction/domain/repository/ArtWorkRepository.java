package com.example.artaction.domain.repository;

import com.example.artaction.domain.entity.ArtWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtWorkRepository extends JpaRepository<ArtWork, Long> {

}
