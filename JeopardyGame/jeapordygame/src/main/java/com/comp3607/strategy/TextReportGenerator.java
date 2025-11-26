package com.comp3607.strategy;

import com.comp3607.model.GameSession;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TextReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(GameSession session, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("JEOPARDY GAME REPORT\n");
            writer.write("===================\n\n");
            
            // Final Scores Section
            writer.write("FINAL SCORES:\n");
            writer.write("-------------\n");
            List<Player> players = session.getPlayers();
            for (Player player : players) {
                writer.write(player.getName() + ": $" + player.getScore() + "\n");
            }
            writer.write("\n");
            
            // Game Statistics
            writer.write("GAME STATISTICS:\n");
            writer.write("----------------\n");
            int totalQuestions = session.getQuestions().size();
            int questionsUsed = (int) session.getQuestions().stream()
                .filter(Question::isUsed)
                .count();
            writer.write("Questions Answered: " + questionsUsed + "/" + totalQuestions + "\n");
            writer.write("\n");
            
            // Winner Information
            Player winner = findWinner(players);
            if (winner != null) {
                writer.write("WINNER: " + winner.getName() + " with $" + winner.getScore() + "\n");
            } else {
                writer.write("WINNER: No players in game\n");
            }
            writer.write("\n");
            
            // Turn History (if available)
            writer.write("TURN HISTORY:\n");
            writer.write("-------------\n");
            // This would need access to turn history from GameSession
            writer.write("Turn-by-turn history would be displayed here.\n");
            writer.write("\n");
            
            writer.write("Report generated on: " + java.time.LocalDateTime.now() + "\n");
            
            System.out.println("Text report generated: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error generating text report: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Player findWinner(List<Player> players) {
        if (players == null || players.isEmpty()) {
            return null; // or create a dummy player
        }
        
        Player winner = players.get(0);
        for (Player player : players) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        return winner;
    }
}