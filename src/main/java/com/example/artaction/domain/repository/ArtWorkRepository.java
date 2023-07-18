package com.example.artaction.domain.repository;

import com.example.artaction.contant.CategoryType;
import com.example.artaction.domain.entity.ArtWork;
import com.example.artaction.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtWorkRepository extends JpaRepository<ArtWork, Long> {
    Optional<List<ArtWork>> findByCategory(CategoryType category);

    Optional<List<ArtWork>> findByUser(User user);

     Optional<ArtWork> findByIdAndUserId(Long artworkId, Long userId);

}
