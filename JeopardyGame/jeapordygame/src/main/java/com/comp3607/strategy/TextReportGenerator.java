package com.comp3607.strategy;

import com.comp3607.model.GameSession;
import com.comp3607.model.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TextReportGenerator implements ReportGenerator {
    @Override
    public void generateReport(GameSession session, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("JEOPARDY GAME REPORT\n");
            writer.write("================================\n\n");
            
            // Case ID and Players
            writer.write("Players: ");
            List<Player> players = session.getPlayers();
            for (int i = 0; i < players.size(); i++) {
                writer.write(players.get(i).getName());
                if (i < players.size() - 1) writer.write(", ");
            }
            writer.write("\n\n");
            
            // Gameplay Summary
            writer.write("Gameplay Summary:\n");
            writer.write("-----------------\n");
            
            List<String> turnHistory = session.getTurnHistory();
            if (turnHistory != null && !turnHistory.isEmpty()) {
                for (int i = 0; i < turnHistory.size(); i++) {
                    writer.write("Turn " + (i + 1) + ": " + turnHistory.get(i) + "\n");
                }
            }
            writer.write("\n");
            
            // Final Scores
            writer.write("Final Scores:\n");
            Player winner = players.get(0);
            for (Player player : players) {
                writer.write(player.getName() + ": " + player.getScore() + "\n");
                if (player.getScore() > winner.getScore()) {
                    winner = player;
                }
            }
            
            writer.write("\nReport generated on: " + java.time.LocalDateTime.now() + "\n");
            
        } catch (IOException e) {
            System.err.println("Error generating text report: " + e.getMessage());
        }
    }
}