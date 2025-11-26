package com.comp3607.service;

import com.comp3607.model.Question;
import java.util.*;
import java.io.*;

public class CSVQuestionParser implements QuestionParser {
    @Override
    public List<Question> parse(String filePath) {
        List<Question> questions = new ArrayList<>();
        
        // Try to load from classpath first
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        
        if (inputStream != null) {
            // File found in classpath (src/main/resources/)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                questions = parseFromReader(br);
                System.out.println("✓ Loaded questions from classpath: " + filePath);
            } catch (Exception e) {
                System.err.println("Error parsing CSV from classpath: " + e.getMessage());
            }
        } else {
            // Try as direct file path
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                questions = parseFromReader(br);
                System.out.println("✓ Loaded questions from file path: " + filePath);
            } catch (Exception e) {
                System.err.println("Error parsing CSV file: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return questions;
    }
    
    private List<Question> parseFromReader(BufferedReader br) throws IOException {
        List<Question> questions = new ArrayList<>();
        String line;
        boolean firstLine = true;
        
        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue; // Skip header
            }
            
            String[] values = parseCSVLine(line);
            if (values.length >= 8) {
                String category = values[0].trim();
                int value = Integer.parseInt(values[1].trim());
                String questionText = values[2].trim();
                
                String fullQuestion = buildMultipleChoiceQuestion(questionText, values);
                String correctAnswer = values[7].trim();
                
                questions.add(new Question(category, value, fullQuestion, correctAnswer));
            }
        }
        return questions;
    }
    
    private String buildMultipleChoiceQuestion(String questionText, String[] values) {
        StringBuilder fullQuestion = new StringBuilder(questionText);
        fullQuestion.append("\n");
        fullQuestion.append("A. ").append(values[3].trim()).append("\n");
        fullQuestion.append("B. ").append(values[4].trim()).append("\n");  
        fullQuestion.append("C. ").append(values[5].trim()).append("\n");
        fullQuestion.append("D. ").append(values[6].trim());
        return fullQuestion.toString();
    }
    
    private String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentValue = new StringBuilder();
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        values.add(currentValue.toString());
        
        return values.toArray(new String[0]);
    }
}