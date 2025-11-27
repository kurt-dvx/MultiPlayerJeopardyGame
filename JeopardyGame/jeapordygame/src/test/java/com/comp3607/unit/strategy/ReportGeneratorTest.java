package com.comp3607.unit.strategy;

import com.comp3607.strategy.PDFReportGenerator;
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
        
        // Save to project directory
        File reportFile = new File("test-reports/test_report.txt");
        reportFile.getParentFile().mkdirs();
        
        generator.generateReport(session, reportFile.getAbsolutePath());
        
        assertTrue(reportFile.exists());
        assertTrue(reportFile.length() > 0);
        
        // Verify report content
        try {
            String content = Files.readString(reportFile.toPath());
            assertTrue(content.contains("JEOPARDY GAME REPORT"));
            assertTrue(content.contains("Alice"));
            assertTrue(content.contains("Bob"));
            assertTrue(content.contains("300"));
            assertTrue(content.contains("200"));
            assertTrue(content.contains("WINNER: Alice"));
            assertTrue(content.contains("Questions Answered: 2/2"));
            assertTrue(content.contains("Turn 1: Alice answered Science 100 correctly"));
            
            // Print first few lines to console
            System.out.println("Text report content preview:");
            String[] lines = content.split("\n");
            for (int i = 0; i < Math.min(10, lines.length); i++) {
                System.out.println("  " + lines[i]);
            }
        } catch (Exception e) {
            fail("Failed to read report file: " + e.getMessage());
        }
        
        System.out.println("Text report saved to: " + reportFile.getAbsolutePath());
        System.out.println("Text report test passed");
    }
    
    @Test
    void testPDFReportGeneration() {
        System.out.println("Testing PDF report generation...");
        PDFReportGenerator generator = new PDFReportGenerator();
        
        GameSession session = createMockGameSession();
        
        // Save to project directory
        File reportFile = new File("test-reports/test_report.pdf");
        reportFile.getParentFile().mkdirs();
        
        generator.generateReport(session, reportFile.getAbsolutePath());
        
        assertTrue(reportFile.exists());
        assertTrue(reportFile.length() > 0);
        
        System.out.println("PDF report saved to: " + reportFile.getAbsolutePath());
        System.out.println("PDF file size: " + reportFile.length() + " bytes");
        System.out.println("PDF report test passed");
    }
    
    private GameSession createMockGameSession() {
        Player player1 = new Player("P1", "Alice");
        player1.setScore(300);
        
        Player player2 = new Player("P2", "Bob"); 
        player2.setScore(200);
        
        Question question1 = new Question("Science", 100, "What is H2O?", "Water");
        question1.setUsed(true);
        
        Question question2 = new Question("History", 200, "First US president?", "Washington");
        question2.setUsed(true);
        
        // Create GameSession with players and questions
        GameSession session = new GameSession(
            Arrays.asList(player1, player2),
            Arrays.asList(question1, question2)
        );
        
        // Add turn history
        session.addTurnHistory("Alice answered Science 100 correctly (+100)");
        session.addTurnHistory("Bob answered History 200 correctly (+200)");
        session.addTurnHistory("Alice answered Science 300 correctly (+300)");
        
        return session;
    }
}