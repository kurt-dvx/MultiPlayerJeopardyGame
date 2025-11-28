package com.comp3607.unit.strategy;

import com.comp3607.strategy.TextReportGenerator;
import com.comp3607.model.GameSession;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    
    @Test
    void testTextReportGeneration() {
        System.out.println("Testing text report generation...");
        TextReportGenerator generator = new TextReportGenerator();
        
        GameSession session = createMockGameSession();
        
        String testReportPath = "test_report.txt";
        File reportFile = new File(testReportPath);
        
        if (reportFile.exists()) {
            reportFile.delete();
        }
        
        generator.generateReport(session, testReportPath);
        
        assertTrue(reportFile.exists(), "Report file should be created");
        assertTrue(reportFile.length() > 0, "Report file should not be empty");
        
        try {
            String content = Files.readString(reportFile.toPath());
            System.out.println("Generated report content:");
            System.out.println(content);
            
            // Check for ACTUAL header from the output
            assertTrue(content.contains("JEOPARDY GAME REPORT"), "Should contain actual report header");
            assertTrue(content.contains("Alice"), "Should contain player Alice");
            assertTrue(content.contains("Bob"), "Should contain player Bob");
            assertTrue(content.contains("300"), "Should contain score 300");
            assertTrue(content.contains("200"), "Should contain score 200");
            
            // Clean up
            reportFile.delete();
            
        } catch (Exception e) {
            fail("Failed to read report file: " + e.getMessage());
        }
        
        System.out.println("Text report test passed");
    }
    
    private GameSession createMockGameSession() {
        Player player1 = new Player("P1", "Alice");
        player1.addScore(300);
        
        Player player2 = new Player("P2", "Bob"); 
        player2.addScore(200);
        
        Question question1 = new Question("Science", 100, "What is H2O?", "Water");
        question1.setUsed(true);
        
        Question question2 = new Question("History", 200, "First US president?", "Washington");
        question2.setUsed(true);
        
        // Create GameSession with players and questions
        GameSession session = new GameSession(
            Arrays.asList(player1, player2),
            Arrays.asList(question1, question2)
        );
        
        // Add turn history (simpler format)
        session.addTurnHistory("Alice selected Science for 100 pts");
        session.addTurnHistory("Bob selected History for 200 pts");
        
        return session;
    }
}