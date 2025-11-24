package com.comp3607.service;

import com.comp3607.model.Question;
import java.util.*;
import java.io.*;

public class CSVQuestionParser implements QuestionParser {
    @Override
    public List<Question> parse(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                // Split by comma but handle quoted fields
                String[] values = parseCSVLine(line);
                if (values.length >= 8) {
                    String category = values[0].trim();
                    int value = Integer.parseInt(values[1].trim());
                    String questionText = values[2].trim();
                    
                    // Build the full question text with options
                    String fullQuestion = buildMultipleChoiceQuestion(questionText, values);
                    String correctAnswer = values[7].trim(); // CorrectAnswer column
                    
                    questions.add(new Question(category, value, fullQuestion, correctAnswer));
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
            e.printStackTrace();
        }
        return questions;
    }
    
    private String buildMultipleChoiceQuestion(String questionText, String[] values) {
        StringBuilder fullQuestion = new StringBuilder(questionText);
        fullQuestion.append("\n");
        fullQuestion.append("A. ").append(values[3].trim()).append("\n"); // OptionA
        fullQuestion.append("B. ").append(values[4].trim()).append("\n"); // OptionB  
        fullQuestion.append("C. ").append(values[5].trim()).append("\n"); // OptionC
        fullQuestion.append("D. ").append(values[6].trim()); // OptionD
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
        values.add(currentValue.toString()); // Add last value
        
        return values.toArray(new String[0]);
    }
}