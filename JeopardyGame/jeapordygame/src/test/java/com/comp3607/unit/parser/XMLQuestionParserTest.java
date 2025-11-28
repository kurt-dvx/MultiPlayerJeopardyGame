package com.comp3607.unit.parser;

import com.comp3607.service.XMLQuestionParser;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class XMLQuestionParserTest {
    
    @Test
    void testParseXMLFile() {
        System.out.println("Testing XML parsing...");
        XMLQuestionParser parser = new XMLQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.xml");
        
        assertNotNull(questions, "Questions list should not be null");
        assertFalse(questions.isEmpty(), "Questions list should not be empty");
        assertEquals(2, questions.size(), "Should parse 2 questions from test file");
        
        // Verify first question
        Question first = questions.get(0);
        assertEquals("History", first.getCategory(), "Category should be History");
        assertEquals(100, first.getValue(), "Value should be 100");
        assertEquals("Who invented telephone?", first.getQuestionText());
        assertEquals("Alexander Graham Bell", first.getAnswer());
        
        // Verify second question
        Question second = questions.get(1);
        assertEquals("History", second.getCategory(), "Category should be History");
        assertEquals(200, second.getValue(), "Value should be 200");
        assertEquals("Ancient Egyptian writing system?", second.getQuestionText());
        assertEquals("Hieroglyphics", second.getAnswer());
        
        System.out.println("✓ Parsed " + questions.size() + " XML questions successfully");
    }
    
    @Test
    void testParseNonExistentXMLFile() {
        System.out.println("Testing non-existent XML file handling...");
        XMLQuestionParser parser = new XMLQuestionParser();
        
        List<Question> questions = parser.parse("non-existent.xml");
        
        assertNotNull(questions, "Should return empty list, not null");
        assertTrue(questions.isEmpty(), "Should return empty list for non-existent file");
        
        System.out.println("✓ Handled non-existent XML file gracefully");
    }
    
    @Test
    void testXMLQuestionStructure() {
        System.out.println("Testing XML question structure...");
        XMLQuestionParser parser = new XMLQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.xml");
        Question question = questions.get(0);
        
        // Test that question is initially unused
        assertFalse(question.isUsed(), "New question should not be marked as used");
        
        // Test that we can mark it as used
        question.setUsed(true);
        assertTrue(question.isUsed(), "Question should be marked as used after setUsed(true)");
        
        // Test answer checking
        assertTrue(question.checkAnswer("Alexander Graham Bell"), "Correct answer should work");
        assertTrue(question.checkAnswer("alexander graham bell"), "Case insensitive should work");
        assertFalse(question.checkAnswer("Thomas Edison"), "Wrong answer should be false");
        
        System.out.println("✓ XML question structure test passed");
    }
}