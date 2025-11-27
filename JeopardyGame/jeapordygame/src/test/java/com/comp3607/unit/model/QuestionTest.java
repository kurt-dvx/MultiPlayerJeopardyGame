package com.comp3607.unit.model;

import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    
    @Test
    void testQuestionCreation() {
        System.out.println("=== Testing Question Creation ===");
        Question question = new Question("Science", 100, "What is H2O?", "Water");
        
        System.out.println("Category: " + question.getCategory());
        System.out.println("Value: " + question.getValue());
        System.out.println("Question: " + question.getQuestionText());
        System.out.println("Answer: " + question.getAnswer());
        System.out.println("Used: " + question.isUsed());
        
        assertEquals("Science", question.getCategory(), "Category should match");
        assertEquals(100, question.getValue(), "Value should match");
        assertEquals("What is H2O?", question.getQuestionText(), "Question text should match");
        assertEquals("Water", question.getAnswer(), "Answer should match");
        assertFalse(question.isUsed(), "New question should not be used");
        
        System.out.println("✓ Question creation test passed\n");
    }
    
    @Test
    void testCheckAnswer() {
        System.out.println("=== Testing Answer Checking ===");
        Question question = new Question("Science", 100, "What is H2O?", "Water");
        
        System.out.println("Testing correct answer 'Water'...");
        assertTrue(question.checkAnswer("Water"), "Exact match should be correct");
        
        System.out.println("Testing case insensitive 'water'...");
        assertTrue(question.checkAnswer("water"), "Case insensitive should be correct");
        
        System.out.println("Testing with spaces ' WATER '...");
        assertTrue(question.checkAnswer(" WATER "), "Trimmed answer should be correct");
        
        System.out.println("Testing wrong answer 'H2O'...");
        assertFalse(question.checkAnswer("H2O"), "Wrong answer should be false");
        
        System.out.println("Testing empty answer...");
        assertFalse(question.checkAnswer(""), "Empty answer should be false");
        
        System.out.println("Testing null answer...");
        assertFalse(question.checkAnswer(null), "Null answer should be false");
        
        System.out.println("✓ Answer checking test passed\n");
    }
    
    @Test
    void testMultipleChoiceAnswer() {
        System.out.println("=== Testing Multiple Choice Answers ===");
        Question question = new Question("Science", 100, "What is H2O?", "A");
        
        System.out.println("Testing correct choice 'A'...");
        assertTrue(question.checkMultipleChoiceAnswer("A"), "Correct choice should be true");
        
        System.out.println("Testing case insensitive 'a'...");
        assertTrue(question.checkMultipleChoiceAnswer("a"), "Case insensitive should be true");
        
        System.out.println("Testing wrong choice 'B'...");
        assertFalse(question.checkMultipleChoiceAnswer("B"), "Wrong choice should be false");
        
        System.out.println("✓ Multiple choice test passed\n");
    }
    
    @Test
    void testQuestionUsage() {
        System.out.println("=== Testing Question Usage ===");
        Question question = new Question("Science", 100, "What is H2O?", "Water");
        
        System.out.println("Initial used state: " + question.isUsed());
        assertFalse(question.isUsed(), "New question should not be used");
        
        question.setUsed(true);
        System.out.println("After setUsed(true): " + question.isUsed());
        assertTrue(question.isUsed(), "Question should be marked as used");
        
        question.setUsed(false);
        System.out.println("After setUsed(false): " + question.isUsed());
        assertFalse(question.isUsed(), "Question should be marked as unused");
        
        System.out.println("✓ Question usage test passed\n");
    }
}