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
    void testPlayerEquality() {
        System.out.println("=== Testing Player Equality ===");
        Player player1 = new Player("P1", "Alice");
        Player player2 = new Player("P1", "Alice");
        Player player3 = new Player("P2", "Bob");
        
        System.out.println("Player1: " + player1);
        System.out.println("Player2: " + player2);
        System.out.println("Player3: " + player3);
        
        System.out.println("Testing player1.equals(player2)...");
        assertEquals(player1, player2, "Players with same ID should be equal");
        
        System.out.println("Testing player1.equals(player3)...");
        assertNotEquals(player1, player3, "Players with different IDs should not be equal");
        
        System.out.println("Testing hashCode consistency...");
        assertEquals(player1.hashCode(), player2.hashCode(), "Equal players should have same hashCode");
        
        System.out.println("✓ Player equality test passed\n");
    }
    
    @Test
    void testPlayerToString() {
        System.out.println("=== Testing Player toString ===");
        Player player = new Player("P1", "Alice");
        player.setScore(100);
        
        String toString = player.toString();
        System.out.println("toString output: " + toString);
        
        assertTrue(toString.contains("Alice"), "toString should contain player name");
        assertTrue(toString.contains("100"), "toString should contain player score");
        assertTrue(toString.contains("P1"), "toString should contain player ID");
        
        System.out.println("✓ Player toString test passed\n");
    }
}