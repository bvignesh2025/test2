package com.talentquery.service;

import com.talentquery.model.Candidate;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ResumeParser resumeParser;
    private final EmbeddingModel embeddingModel;

    public AiService(ResumeParser resumeParser, EmbeddingModel embeddingModel) {
        this.resumeParser = resumeParser;
        this.embeddingModel = embeddingModel;
    }

    /**
     * Parses resume text and generates embeddings for the bio/skills
     */
    public Candidate processResume(String text) {
        Candidate candidate = resumeParser.parse(text);
        
        // Generate embedding for the bio and skills combined for better semantic search
        String embeddingInput = candidate.getBio() + " " + String.join(" ", candidate.getSkills());
        float[] vector = embeddingModel.embed(embeddingInput).content().vector();
        
        candidate.setEmbedding(vector);
        return candidate;
    }

    /**
     * Converts a natural language query into a vector
     */
    public float[] getQueryVector(String query) {
        return embeddingModel.embed(query).content().vector();
    }
}
