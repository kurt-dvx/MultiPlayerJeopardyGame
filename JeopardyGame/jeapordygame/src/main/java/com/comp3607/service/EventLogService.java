package com.comp3607.service;

import com.comp3607.model.GameEvent;
import com.comp3607.observer.GameObserver;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventLogService implements GameObserver {
    private List<GameEvent> events;
    private static final String CSV_HEADER = "Case_ID,Player_ID,Activity,Timestamp,Category,Question_Value,Answer_Given,Result,Score_After_Play";
    private String currentCaseId;
    
    public EventLogService() {
        this.events = new ArrayList<>();
        this.currentCaseId = "GAME" + System.currentTimeMillis() % 10000; // Unique case ID
    }

    public void logEvent(GameEvent event) {
        events.add(event);
    }
    
    @Override
    public void update(GameEvent event) {
        events.add(event);
    }
    
    // Log system events
    public void logSystemEvent(String activity) {
        GameEvent event = new GameEvent(currentCaseId, activity);
        event.setPlayerId("System");
        events.add(event);
    }
    
    public void logFileLoad(String filename, boolean success) {
        GameEvent event = new GameEvent(currentCaseId, "Load File");
        event.setPlayerId("System");
        event.setResult(success ? "Success" : "Failed");
        events.add(event);
    }
    
    public void logPlayerCount(int count) {
        GameEvent event = new GameEvent(currentCaseId, "Select Player Count");
        event.setPlayerId("System");
        event.setAnswerGiven(String.valueOf(count));
        events.add(event);
    }
    
    public void logPlayerName(String playerId, String playerName) {
        GameEvent event = new GameEvent(currentCaseId, "Enter Player Name");
        event.setPlayerId(playerId);
        event.setAnswerGiven(playerName);
        events.add(event);
    }
    
    public void logCategorySelection(String playerId, String category) {
        GameEvent event = new GameEvent(currentCaseId, "Select Category");
        event.setPlayerId(playerId);
        event.setCategory(category);
        events.add(event);
    }
    
    public void logQuestionSelection(String playerId, String category, int value) {
        GameEvent event = new GameEvent(currentCaseId, "Select Question");
        event.setPlayerId(playerId);
        event.setCategory(category);
        event.setQuestionValue(value);
        events.add(event);
    }
    
    public void logAnswer(String playerId, String category, int value, String answer, boolean isCorrect, int scoreAfter) {
        GameEvent event = new GameEvent(currentCaseId, "Answer Question");
        event.setPlayerId(playerId);
        event.setCategory(category);
        event.setQuestionValue(value);
        event.setAnswerGiven(answer);
        event.setResult(isCorrect ? "Correct" : "Incorrect");
        event.setScoreAfterPlay(scoreAfter);
        events.add(event);
    }
    
    public void logScoreUpdate(String playerId, int scoreAfter) {
        GameEvent event = new GameEvent(currentCaseId, "Score Updated");
        event.setPlayerId(playerId);
        event.setScoreAfterPlay(scoreAfter);
        events.add(event);
    }
    
    public void generateEventLog(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(CSV_HEADER + "\n");
            for (GameEvent event : events) {
                writer.write(formatEventForCSV(event) + "\n");
            }
            System.out.println("✅ Event log saved to: " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error saving event log: " + e.getMessage());
        }
    }
    
    private String formatEventForCSV(GameEvent event) {
        return String.join(",",
            event.getCaseId(),
            safeString(event.getPlayerId()),
            safeString(event.getActivity()),
            event.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            safeString(event.getCategory()),
            safeInteger(event.getQuestionValue()),
            safeString(event.getAnswerGiven()),
            safeString(event.getResult()),
            safeInteger(event.getScoreAfterPlay())
        );
    }
    
    private String safeString(String value) {
        return value != null ? "\"" + value.replace("\"", "\"\"") + "\"" : "";
    }
    
    private String safeInteger(Integer value) {
        return value != null ? value.toString() : "";
    }
}