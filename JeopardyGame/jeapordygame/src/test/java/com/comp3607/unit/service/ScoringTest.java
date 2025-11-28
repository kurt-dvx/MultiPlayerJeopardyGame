package com.comp3607.unit.service;

import com.comp3607.model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoringTest {
    
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
        
        System.out.println("Adding 200 points...");
        player.addScore(200);
        System.out.println("Score after +200: " + player.getScore());
        assertEquals(200, player.getScore(), "Score should be 200 after final addition");
        
        System.out.println("✓ Score operations test passed\n");
    }
    
    @Test
    void testMultipleScoreOperations() {
        System.out.println("=== Testing Multiple Score Operations ===");
        Player player = new Player("P1", "TestPlayer");
        
        System.out.println("Performing multiple operations: +100, +200, -50");
        player.addScore(100);
        System.out.println("After +100: " + player.getScore());
        
        player.addScore(200);
        System.out.println("After +200: " + player.getScore());
        
        player.subtractScore(50);
        System.out.println("After -50: " + player.getScore());
        
        assertEquals(250, player.getScore(), "Cumulative score should be 250");
        
        System.out.println("✓ Multiple operations test passed\n");
    }
    
    @Test
    void testScoreNeverGoesNegative() {
        System.out.println("=== Testing Score Never Goes Negative ===");
        Player player = new Player("P1", "TestPlayer");
        
        System.out.println("Starting score: " + player.getScore());
        
        System.out.println("Subtracting 100 from 0...");
        player.subtractScore(100);
        assertEquals(0, player.getScore(), "Score should remain 0 when subtracting from 0");
        System.out.println("Score after subtraction from 0: " + player.getScore());
        
        System.out.println("Adding 50 then subtracting 100...");
        player.addScore(50);
        player.subtractScore(100);
        assertEquals(0, player.getScore(), "Score should be 0 after large subtraction");
        System.out.println("Score after +50 then -100: " + player.getScore());
        
        System.out.println("✓ Score boundary test passed\n");
    }
    
    @Test
    void testPlayerToStringWithScore() {
        System.out.println("=== Testing Player toString With Score ===");
        Player player = new Player("P1", "Alice");
        player.addScore(150);
        
        String toString = player.toString();
        System.out.println("toString output: " + toString);
        
        assertTrue(toString.contains("Alice"), "toString should contain player name");
        assertTrue(toString.contains("150"), "toString should contain player score");
        assertTrue(toString.contains("P1"), "toString should contain player ID");
        
        System.out.println("✓ Player toString test passed\n");
    }
}