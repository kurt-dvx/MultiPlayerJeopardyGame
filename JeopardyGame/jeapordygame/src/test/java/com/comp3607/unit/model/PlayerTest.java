package com.comp3607.unit.model;

import com.comp3607.model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    
    @Test
    void testPlayerCreation() {
        System.out.println("=== Testing Player Creation ===");
        Player player = new Player("P1", "Test Player");
        
        System.out.println("Player ID: " + player.getId());
        System.out.println("Player Name: " + player.getName());
        System.out.println("Initial Score: " + player.getScore());
        
        assertEquals("P1", player.getId(), "Player ID should match");
        assertEquals("Test Player", player.getName(), "Player name should match");
        assertEquals(0, player.getScore(), "Initial score should be 0");
        
        System.out.println("✓ Player creation test passed\n");
    }
    
    @Test
    void testPlayerScoreOperations() {
        System.out.println("=== Testing Player Score Operations ===");
        Player player = new Player("P1", "TestPlayer");
        
        System.out.println("Initial score: " + player.getScore());
        assertEquals(0, player.getScore(), "Initial score should be 0");
        
        System.out.println("Adding 100 points...");
        player.addScore(100);
        assertEquals(100, player.getScore(), "Score should be 100 after addition");
        System.out.println("Score after addition: " + player.getScore());
        
        System.out.println("Subtracting 50 points...");
        player.subtractScore(50);
        assertEquals(50, player.getScore(), "Score should be 50 after subtraction");
        System.out.println("Score after subtraction: " + player.getScore());
        
        System.out.println("Subtracting 100 points (testing minimum)...");
        player.subtractScore(100);
        assertEquals(0, player.getScore(), "Score should not go below 0");
        System.out.println("Score after large subtraction: " + player.getScore());
        
        System.out.println("✓ Score operations test passed\n");
    }
    
    @Test
    void testPlayerToString() {
        System.out.println("=== Testing Player toString ===");
        Player player = new Player("P1", "Alice");
        player.addScore(100);
        
        String toString = player.toString();
        System.out.println("toString output: " + toString);
        
        assertTrue(toString.contains("Alice"), "toString should contain player name");
        assertTrue(toString.contains("100"), "toString should contain player score");
        assertTrue(toString.contains("P1"), "toString should contain player ID");
        
        System.out.println("✓ Player toString test passed\n");
    }
}