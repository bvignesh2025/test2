package com.talentquery.service;

import com.talentquery.model.Candidate;
import com.talentquery.repository.CandidateRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {

    private final CandidateRepository candidateRepository;
    private final AiService aiService;

    public SearchService(CandidateRepository candidateRepository, AiService aiService) {
        this.candidateRepository = candidateRepository;
        this.aiService = aiService;
    }

    /**
     * Executes a hybrid search: Translates query to vector and applies SQL filters.
     */
    public List<Candidate> search(String query, Integer minExperience, int limit) {
        float[] queryVector = aiService.getQueryVector(query);
        
        if (minExperience != null) {
            return candidateRepository.findHybrid(queryVector, minExperience, limit);
        } else {
            return candidateRepository.findNear(queryVector, limit);
        }
    }

    public Candidate saveCandidate(String resumeText) {
        Candidate candidate = aiService.processResume(resumeText);
        return candidateRepository.save(candidate);
    }
}
