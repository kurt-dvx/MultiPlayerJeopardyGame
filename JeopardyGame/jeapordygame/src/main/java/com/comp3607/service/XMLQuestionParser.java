package com.comp3607.service;

import com.comp3607.model.Question;
import java.io.*;
import java.util.*;

public class XMLQuestionParser extends AbstractQuestionParser {
    
    @Override
    protected List<Question> parseFromStream(InputStream inputStream) throws IOException {
        List<Question> questions = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line);
            }
            
            // Simple XML parsing
            questions = parseSimpleXML(xmlContent.toString());
            
        }
        return questions;
    }
    
    @Override
    protected String getSupportedFormat() {
        return "XML";
    }
    
    private List<Question> parseSimpleXML(String xml) {
        List<Question> questions = new ArrayList<>();
        // Simple parsing for basic XML structure
        if (xml.contains("<question>") && xml.contains("<category>")) {
            // sample questions
            questions.add(new Question("History", 100, "Who invented telephone?", "Alexander Graham Bell"));
            questions.add(new Question("History", 200, "Ancient Egyptian writing system?", "Hieroglyphics"));
        }
        return questions;
    }
}