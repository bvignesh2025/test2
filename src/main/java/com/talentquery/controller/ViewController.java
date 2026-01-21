package com.talentquery.controller;

import com.talentquery.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

    private final SearchService searchService;

    public ViewController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String index(Model model, 
                        @RequestParam(required = false) String query,
                        @RequestParam(required = false) Integer minExp) {
        
        if (query != null && !query.isEmpty()) {
            model.addAttribute("results", searchService.search(query, minExp, 10));
            model.addAttribute("query", query);
        }
        
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam String resumeText, RedirectAttributes redirectAttributes) {
        searchService.saveCandidate(resumeText);
        redirectAttributes.addFlashAttribute("message", "Resume indexed successfully!");
        return "redirect:/";
    }
}
