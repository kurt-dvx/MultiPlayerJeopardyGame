package com.comp3607.service;

import com.comp3607.model.GameEvent;
import com.comp3607.observer.GameObserver;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventLogService implements GameObserver {
    private List<GameEvent> events;
    private String currentCaseId;
    private static int gameCounter = 1;
    private static final String CSV_HEADER = "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play";
    
    public EventLogService() {
        this.events = new ArrayList<>();
        this.currentCaseId = "GAME" + String.format("%03d", gameCounter++);
    }

    @Override
    public void update(GameEvent event) {
        events.add(event);
    }

    public void logEvent(GameEvent event) {
        events.add(event);
    }

    public int getEventCount() {
        return events.size();
    }
    
    public void logSystemEvent(String activity) {
        GameEvent event = new GameEvent(currentCaseId, "System", activity, "", "", "", "", "");
        events.add(event);
    }
    
    public void logFileLoad(String filename, boolean success) {
        String result = success ? "Success" : "Failed";
        GameEvent event = new GameEvent(currentCaseId, "System", "Load File", "", "", "", result, "");
        events.add(event);
    }
    
    public void logPlayerCount(int count) {
        GameEvent event = new GameEvent(currentCaseId, "System", "Select Player Count", "", "", "", String.valueOf(count), "N/A");
        events.add(event);
    }
    
    public void logPlayerName(String playerId, String playerName) {
        GameEvent event = new GameEvent(currentCaseId, playerId, "Enter Player Name", "", "", "", playerName, "N/A");
        events.add(event);
    }
    
    public void logCategorySelection(String playerId, String category) {
        GameEvent event = new GameEvent(currentCaseId, playerId, "Select Category", category, "", "", "", "0");
        events.add(event);
    }
    
    public void logQuestionSelection(String playerId, String category, int value) {
        GameEvent event = new GameEvent(currentCaseId, playerId, "Select Question", category, String.valueOf(value), "", "", "0");
        events.add(event);
    }
    
    public void logAnswer(String playerId, String category, int value, String answer, boolean isCorrect, int scoreAfter) {
        String result = isCorrect ? "Correct" : "Incorrect";
        GameEvent event = new GameEvent(currentCaseId, playerId, "Answer Question", category, String.valueOf(value), answer, result, String.valueOf(scoreAfter));
        events.add(event);
    }
    
    public void generateEventLog(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(CSV_HEADER + "\n");
            for (GameEvent event : events) {
                writer.write(event.toCSV() + "\n");
            }
            System.out.println("Event log saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving event log: " + e.getMessage());
        }
    }
    
    public String getCurrentCaseId() {
        return currentCaseId;
    }
}