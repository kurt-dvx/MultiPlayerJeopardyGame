package com.comp3607.unit.service;

import com.comp3607.service.EventLogService;
import com.comp3607.model.GameEvent;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

class EventLogServiceTest {
    
    @Test
    void testEventLogging() {
        System.out.println("Testing event logging...");
        EventLogService logService = new EventLogService();
        
        // Test initial state
        assertEquals(0, logService.getEventCount(), "Should start with 0 events");
        
        // Log some events
        logService.logSystemEvent("Start Game");
        logService.logPlayerCount(2);
        logService.logPlayerName("P1", "Alice");
        
        assertEquals(3, logService.getEventCount(), "Should have 3 events after logging");
        
        System.out.println("✓ Event logging test passed");
    }
    
    @Test
    void testEventLogGeneration() {
        System.out.println("Testing event log file generation...");
        EventLogService logService = new EventLogService();
        
        // Log some sample events
        logService.logSystemEvent("Test Event");
        logService.logPlayerName("P1", "TestPlayer");
        logService.logAnswer("P1", "Science", 100, "Water", true, 100);
        
        // Generate log file with absolute path for visibility
        String testLogPath = "test_event_log.csv";
        File logFile = new File(testLogPath);
        
        System.out.println("Creating event log at: " + logFile.getAbsolutePath());
        
        // Clean up any existing file
        if (logFile.exists()) {
            boolean deleted = logFile.delete();
            System.out.println("Deleted existing file: " + deleted);
        }
        
        logService.generateEventLog(testLogPath);
        
        // Verify file was created
        assertTrue(logFile.exists(), "Event log file should be created at: " + logFile.getAbsolutePath());
        assertTrue(logFile.length() > 0, "Event log file should not be empty");
        
        System.out.println("File exists: " + logFile.exists());
        System.out.println("File size: " + logFile.length() + " bytes");
        System.out.println("File path: " + logFile.getAbsolutePath());
        
        // Verify CSV format
        try {
            String content = Files.readString(logFile.toPath());
            System.out.println("Event log content:");
            System.out.println(content);
            
            assertTrue(content.contains("Case_ID,Player_ID,Activity"), "Should contain CSV header");
            assertTrue(content.contains("System,Test Event"), "Should contain system event");
            assertTrue(content.contains("P1,Enter Player Name"), "Should contain player event");
            assertTrue(content.contains("P1,Answer Question"), "Should contain answer event");
            
            // let's us see the file
            System.out.println("✓ Event log generation test passed - file created successfully");
            
        } catch (Exception e) {
            fail("Failed to read event log: " + e.getMessage());
        }
    }
    
    @Test
    void testGameObserverImplementation() {
        System.out.println("Testing GameObserver implementation...");
        EventLogService logService = new EventLogService();
        
        // Test that it implements GameObserver
        assertTrue(logService instanceof com.comp3607.observer.GameObserver, 
                  "EventLogService should implement GameObserver");
        
        // Test update method (from GameObserver interface)
        GameEvent testEvent = new GameEvent("TEST001", "P1", "Test Activity", 
                                          "Category", "100", "Answer", "Result", "500");
        logService.update(testEvent);
        
        assertEquals(1, logService.getEventCount(), "Should have 1 event after update");
        
        System.out.println("✓ GameObserver implementation test passed");
    }
}