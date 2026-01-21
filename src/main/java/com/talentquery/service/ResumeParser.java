package com.talentquery.service;

import com.talentquery.model.Candidate;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

@AiService
public interface ResumeParser {

    @SystemMessage("You are a recruiter's assistant. You must extract candidate information from resume text. " +
                   "Return ONLY a valid JSON object. Do not include markdown formatting, backticks, or preamble.")
    @UserMessage("Extract candidate details from the following resume text: {{text}}. " +
                 "JSON structure: { \"name\": \"string\", \"email\": \"string\", \"yearsOfExperience\": number, \"skills\": [\"string\"], \"bio\": \"string\" }")
    Candidate parse(@V("text") String text);
}
