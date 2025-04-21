package com.example.ocpimport.controller;

import com.example.ocpimport.model.Question;
import com.example.ocpimport.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.stream.IntStream;

import java.util.List;

@Controller
public class QuestionWebController {

    private final QuestionRepository repo;

    public QuestionWebController(QuestionRepository repo) {
        this.repo = repo;
    }



    @GetMapping("/")
    public String showAll(
            Model model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 10); // 10 questions par page
        Page<Question> questionsPage;

        if ((category == null || category.isEmpty()) && (keyword == null || keyword.isEmpty())) {
            questionsPage = repo.findAll(pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            questionsPage = repo.findByLinkContainingIgnoreCase(keyword, pageable);
        } else {
            questionsPage = repo.findByCategory(category, pageable);
        }

        model.addAttribute("questionsPage", questionsPage);
        model.addAttribute("questions", questionsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", questionsPage.getTotalPages());


        model.addAttribute("pageIndexes", IntStream.range(0, questionsPage.getTotalPages()).toArray());

        model.addAttribute("selectedCategory", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categories", repo.findDistinctCategories());

        return "questions";
    }

}
