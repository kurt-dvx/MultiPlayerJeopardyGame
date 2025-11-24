package com.comp3607.strategy;

import com.comp3607.model.GameSession;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.List;

public class PDFReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(GameSession session, String filename) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set up fonts and starting position
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                float titleY = 750;
                float currentY = titleY;
                
                // Title
                contentStream.beginText();
                contentStream.newLineAtOffset(100, currentY);
                contentStream.showText("JEOPARDY GAME REPORT");
                contentStream.endText();
                
                currentY -= 30;
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                
                // Final Scores Section
                contentStream.beginText();
                contentStream.newLineAtOffset(100, currentY);
                contentStream.showText("FINAL SCORES:");
                contentStream.endText();
                currentY -= 20;
                
                List<Player> players = session.getPlayers();
                for (Player player : players) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(120, currentY);
                    contentStream.showText(player.getName() + ": $" + player.getScore());
                    contentStream.endText();
                    currentY -= 15;
                }
                
                currentY -= 20;
                
                // Game Details Section
                contentStream.beginText();
                contentStream.newLineAtOffset(100, currentY);
                contentStream.showText("GAME DETAILS:");
                contentStream.endText();
                currentY -= 20;
                
                // Add game statistics
                int totalQuestions = session.getQuestions().size();
                int questionsUsed = (int) session.getQuestions().stream()
                    .filter(Question::isUsed)
                    .count();
                
                contentStream.beginText();
                contentStream.newLineAtOffset(120, currentY);
                contentStream.showText("Questions: " + questionsUsed + "/" + totalQuestions + " answered");
                contentStream.endText();
                currentY -= 15;
                
                // Add timestamp
                contentStream.beginText();
                contentStream.newLineAtOffset(120, currentY);
                contentStream.showText("Report generated on: " + java.time.LocalDateTime.now());
                contentStream.endText();
            }
            
            // Save the document
            document.save(filename);
            System.out.println("PDF report generated: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error generating PDF report: " + e.getMessage());
        }
    }
}