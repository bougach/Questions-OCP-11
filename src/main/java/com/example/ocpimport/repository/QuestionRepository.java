package com.example.ocpimport.repository;

import com.example.ocpimport.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT DISTINCT q.category FROM Question q")
    List<String> findDistinctCategories();

    
   

    Page<Question> findByCategory(String category, Pageable pageable);

    Page<Question> findByLinkContainingIgnoreCase(String keyword, Pageable pageable);



}
