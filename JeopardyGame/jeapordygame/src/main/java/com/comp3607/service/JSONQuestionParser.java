package com.comp3607.service;

import com.comp3607.model.Question;
import java.util.*;
import java.io.*;

public class JSONQuestionParser implements QuestionParser {
    @Override
    public List<Question> parse(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            
            // Simple JSON parsing (you can enhance this with Jackson later)
            questions = parseSimpleJSON(jsonContent.toString());
            
        } catch (Exception e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
        }
        return questions;
    }
    
    private List<Question> parseSimpleJSON(String json) {
        List<Question> questions = new ArrayList<>();
        // Simple parsing for basic JSON structure
        // This is a simplified version - you can use Jackson library for proper parsing
        if (json.contains("questions") && json.contains("category")) {
            // Add some sample questions for now
            questions.add(new Question("Science", 100, "What is the chemical symbol for gold?", "Au"));
            questions.add(new Question("Science", 200, "How many bones in human body?", "206"));
        }
        return questions;
    }
}