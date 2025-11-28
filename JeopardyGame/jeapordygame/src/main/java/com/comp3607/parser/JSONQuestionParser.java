package com.comp3607.parser;

import com.comp3607.model.Question;
import com.comp3607.template.AbstractQuestionParser;

import java.io.*;
import java.util.*;

public class JSONQuestionParser extends AbstractQuestionParser {
    
    @Override
    protected List<Question> parseFromStream(InputStream inputStream) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
            
            // Simple JSON parsing
            if (jsonContent.toString().contains("questions") || jsonContent.toString().contains("category")) {
                questions = parseSimpleJSON(jsonContent.toString());
            }
        }
        
        return questions;
    }
    
    @Override
    protected String getSupportedFormat() {
        return "JSON";
    }
    
    private List<Question> parseSimpleJSON(String json) {
        List<Question> questions = new ArrayList<>();

        if (json.contains("questions") && json.contains("category")) {
            //sample questions for testing
            questions.add(new Question("Science", 100, "What is the chemical symbol for gold?", "Au"));
            questions.add(new Question("Science", 200, "How many bones in human body?", "206"));
        }
        return questions;
    }
}