package com.comp3607.service;

import com.comp3607.model.Question;
import java.util.*;
import java.io.*;

public class CSVQuestionParser extends AbstractQuestionParser {
    
    @Override
    protected List<Question> parseFromStream(InputStream inputStream) throws IOException {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
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
        }
        return questions;
    }

    @Override
    protected String getSupportedFormat() {
        return "CSV";
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