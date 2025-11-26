package com.comp3607.service;

import com.comp3607.model.Question;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractQuestionParser implements QuestionParser {
    
    // TEMPLATE METHOD PATTERN - defines the skeleton
    @Override
    public final List<Question> parse(String filePath) {
        List<Question> questions = new ArrayList<>();
        
        try (InputStream inputStream = getInputStream(filePath)) {
            if (inputStream != null) {
                questions = parseFromStream(inputStream);
                System.out.println("✓ Loaded " + questions.size() + " questions from: " + filePath);
            } else {
                System.err.println("❌ File not found: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("❌ Error parsing " + getSupportedFormat() + " file: " + e.getMessage());
        }
        
        return questions;
    }
    
    // PRIMITIVE OPERATIONS - to be implemented by subclasses
    protected abstract List<Question> parseFromStream(InputStream inputStream) throws IOException;
    protected abstract String getSupportedFormat();
    
    // COMMON IMPLEMENTATION - reusable by all subclasses
    protected InputStream getInputStream(String filePath) throws FileNotFoundException {
        // Try classpath first (for resources in src/main/resources/)
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            // Try as direct file path
            inputStream = new FileInputStream(filePath);
        }
        return inputStream;
    }
}