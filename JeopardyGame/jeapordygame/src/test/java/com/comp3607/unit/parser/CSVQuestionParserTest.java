package com.comp3607.unit.parser;

import com.comp3607.service.CSVQuestionParser;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CSVQuestionParserTest {
    
    @Test
    void testParseCSVFile() {
        System.out.println("Testing CSV parsing with MCQ format...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.csv");
        
        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");
        assertEquals(5, questions.size(), "Should parse 5 questions from test file");
        
        // Test first question: Science,100,What is H2O?
        Question first = questions.get(0);
        assertEquals("Science", first.getCategory(), "Category should be Science");
        assertEquals(100, first.getValue(), "Value should be 100");
        assertEquals("Water", first.getAnswer(), "Answer should be Water");
        
        // Verify MCQ formatting
        String firstQuestionText = first.getQuestionText();
        assertTrue(firstQuestionText.contains("What is H2O?"), "Should contain question text");
        assertTrue(firstQuestionText.contains("A. Oxygen"), "Should contain option A");
        assertTrue(firstQuestionText.contains("B. Water"), "Should contain option B");
        assertTrue(firstQuestionText.contains("C. Hydrogen"), "Should contain option C");
        assertTrue(firstQuestionText.contains("D. Gold"), "Should contain option D");
        
        System.out.println("✓ Successfully parsed " + questions.size() + " CSV questions");
    }
    
    @Test
    void testAnswerCheckingWithCSVQuestions() {
        System.out.println("Testing answer checking with CSV-parsed questions...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.csv");
        Question scienceQuestion = questions.get(0); // What is H2O?
        
        System.out.println("Question: " + scienceQuestion.getQuestionText());
        System.out.println("Correct Answer: " + scienceQuestion.getAnswer());
        
        System.out.println("Testing 'Water': " + scienceQuestion.checkAnswer("Water"));
        System.out.println("Testing 'B': " + scienceQuestion.checkAnswer("B"));
        System.out.println("Testing 'A': " + scienceQuestion.checkAnswer("A"));
        
        assertTrue(scienceQuestion.checkAnswer("Water"), "Full text 'Water' should be correct");
        assertTrue(scienceQuestion.checkAnswer("water"), "Case insensitive should work");
        
        boolean letterBWorks = scienceQuestion.checkAnswer("B");
        boolean letterAWorks = scienceQuestion.checkAnswer("A");
        
        System.out.println("Letter B works: " + letterBWorks);
        System.out.println("Letter A works: " + letterAWorks);
        
        assertTrue(scienceQuestion.checkAnswer("Water"), "Water should be correct");
        
        System.out.println("✓ Answer checking test completed");
    }
    
    @Test
    void testCSVQuestionStructure() {
        System.out.println("Testing CSV question structure details...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.csv");
        Question question = questions.get(0);
        
        // Test that question is initially unused
        assertFalse(question.isUsed(), "New question should not be marked as used");
        
        // Test that we can mark it as used
        question.setUsed(true);
        assertTrue(question.isUsed(), "Question should be marked as used after setUsed(true)");
        
        System.out.println("✓ CSV question structure test passed");
    }
    
    @Test
    void testParseNonExistentFile() {
        System.out.println("Testing non-existent file handling...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("non-existent.csv");
        
        assertNotNull(questions, "Should return empty list, not null");
        assertTrue(questions.isEmpty(), "Should return empty list for non-existent file");
        
        System.out.println("✓ Handled non-existent file gracefully");
    }
    
    @Test
    void testParseEmptyFile() {
        System.out.println("Testing empty file handling...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("empty.csv");
        
        assertNotNull(questions, "Should return empty list, not null");
        assertTrue(questions.isEmpty(), "Should return empty list for empty file");
        
        System.out.println("✓ Handled empty file gracefully");
    }
}