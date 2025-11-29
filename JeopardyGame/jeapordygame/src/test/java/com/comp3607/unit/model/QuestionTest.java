package com.comp3607.unit.model;

import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {
    
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