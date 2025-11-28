package com.comp3607.unit.parser;

import com.comp3607.service.JSONQuestionParser;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JSONQuestionParserTest {
    
    @Test
    void testParseJSONFile() {
        System.out.println("Testing JSON parsing...");
        JSONQuestionParser parser = new JSONQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.json");
        
        assertNotNull(questions);
        assertEquals(2, questions.size());
        
        // Verify first question
        Question first = questions.get(0);
        assertEquals("Science", first.getCategory());
        assertEquals(100, first.getValue());
        assertEquals("What is the chemical symbol for gold?", first.getQuestionText());
        assertEquals("Au", first.getAnswer());
        
        // Verify second question
        Question second = questions.get(1);
        assertEquals("Science", second.getCategory());
        assertEquals(200, second.getValue());
        assertEquals("How many bones in human body?", second.getQuestionText());
        assertEquals("206", second.getAnswer());
        
        System.out.println("✓ Parsed " + questions.size() + " JSON questions successfully");
    }
    
    @Test
    void testParseNonExistentJSONFile() {
        System.out.println("Testing non-existent JSON file handling...");
        JSONQuestionParser parser = new JSONQuestionParser();
        
        List<Question> questions = parser.parse("non-existent.json");
        
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
        
        System.out.println("✓ Handled non-existent JSON file");
    }
}
