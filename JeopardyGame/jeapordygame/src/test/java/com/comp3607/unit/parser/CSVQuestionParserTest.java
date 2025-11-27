package com.comp3607.unit.parser;

import com.comp3607.service.CSVQuestionParser;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CSVQuestionParserTest {
    
    @Test
    void testParseCSVFile() {
        System.out.println("Testing CSV parsing...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("test-questions.csv");
        
        assertNotNull(questions);
        assertFalse(questions.isEmpty());
        assertEquals(5, questions.size());
        
        // Verify first question structure
        Question first = questions.get(0);
        assertEquals("Science", first.getCategory());
        assertEquals(100, first.getValue());
        assertEquals("Water", first.getAnswer());
        
        System.out.println("✓ Parsed " + questions.size() + " questions successfully");
    }
    
    @Test
    void testParseNonExistentFile() {
        System.out.println("Testing non-existent file handling...");
        CSVQuestionParser parser = new CSVQuestionParser();
        
        List<Question> questions = parser.parse("non-existent.csv");
        
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
        
        System.out.println("✓ Handled non-existent file gracefully");
    }
}