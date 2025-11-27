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
        
        assertNotNull(questions);
        assertEquals(2, questions.size());
        
        // Verify first question
        Question first = questions.get(0);
        assertEquals("History", first.getCategory());
        assertEquals(100, first.getValue());
        assertEquals("Who invented telephone?", first.getQuestionText());
        assertEquals("Alexander Graham Bell", first.getAnswer());
        
        // Verify second question
        Question second = questions.get(1);
        assertEquals("History", second.getCategory());
        assertEquals(200, second.getValue());
        assertEquals("Ancient Egyptian writing system?", second.getQuestionText());
        assertEquals("Hieroglyphics", second.getAnswer());
        
        System.out.println("✓ Parsed " + questions.size() + " XML questions successfully");
    }
    
    @Test
    void testParseNonExistentXMLFile() {
        System.out.println("Testing non-existent XML file handling...");
        XMLQuestionParser parser = new XMLQuestionParser();
        
        List<Question> questions = parser.parse("non-existent.xml");
        
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
        
        System.out.println("✓ Handled non-existent XML file gracefully");
    }
}