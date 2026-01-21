package com.talentquery.controller;

import com.talentquery.model.Candidate;
import com.talentquery.service.SearchService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final SearchService searchService;

    public CandidateController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search for candidates using natural language and optional filters.
     */
    @GetMapping("/search")
    public List<Candidate> search(
            @RequestParam String query,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(defaultValue = "10") int limit) {
        return searchService.search(query, minExperience, limit);
    }

    /**
     * Upload a new resume (accepts text for simplicity in this POC)
     */
    @PostMapping("/upload")
    public Candidate uploadResume(@RequestBody String resumeText) {
        return searchService.saveCandidate(resumeText);
    }
}
