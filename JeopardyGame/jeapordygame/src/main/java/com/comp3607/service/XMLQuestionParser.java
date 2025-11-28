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
            
            // Actually parse the XML content
            questions = parseXMLContent(xmlContent.toString());
            
        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
        }
        
        return questions;
    }
    
    @Override
    protected String getSupportedFormat() {
        return "XML";
    }
    
    private List<Question> parseXMLContent(String xml) {
        List<Question> questions = new ArrayList<>();
        
        // Simple XML parsing 
        String[] questionBlocks = xml.split("<question>");
        
        for (String block : questionBlocks) {
            if (block.contains("</question>")) {
                String questionContent = block.substring(0, block.indexOf("</question>"));
                
                String category = extractTagContent(questionContent, "category");
                String valueStr = extractTagContent(questionContent, "value");
                String text = extractTagContent(questionContent, "text");
                String answer = extractTagContent(questionContent, "answer");
                
                if (category != null && valueStr != null && text != null && answer != null) {
                    try {
                        int value = Integer.parseInt(valueStr.trim());
                        questions.add(new Question(category.trim(), value, text.trim(), answer.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid value in XML: " + valueStr);
                    }
                }
            }
        }
        
        return questions;
    }
    
    private String extractTagContent(String xmlBlock, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        
        int startIndex = xmlBlock.indexOf(startTag);
        if (startIndex == -1) return null;
        
        startIndex += startTag.length();
        int endIndex = xmlBlock.indexOf(endTag, startIndex);
        if (endIndex == -1) return null;
        
        return xmlBlock.substring(startIndex, endIndex);
    }
}