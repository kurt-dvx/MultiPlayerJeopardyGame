package com.comp3607.service;

import com.comp3607.model.Question;
import java.util.*;
import java.io.*;

public class XMLQuestionParser implements QuestionParser {
    @Override
    public List<Question> parse(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line);
            }
            
            // Simple XML parsing
            questions = parseSimpleXML(xmlContent.toString());
            
        } catch (Exception e) {
            System.err.println("Error parsing XML file: " + e.getMessage());
        }
        return questions;
    }
    
    private List<Question> parseSimpleXML(String xml) {
        List<Question> questions = new ArrayList<>();
        // Simple parsing for basic XML structure
        if (xml.contains("<question>") && xml.contains("<category>")) {
            // Add some sample questions for now
            questions.add(new Question("History", 100, "Who invented telephone?", "Alexander Graham Bell"));
            questions.add(new Question("History", 200, "Ancient Egyptian writing system?", "Hieroglyphics"));
        }
        return questions;
    }
}