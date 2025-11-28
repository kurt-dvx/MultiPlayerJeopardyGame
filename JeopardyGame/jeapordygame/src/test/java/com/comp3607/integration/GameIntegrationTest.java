package com.comp3607.integration;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.observer.GameNotifier;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameIntegrationTest {
    
    @Test
    void testCompleteGameFlow() {
        // Setup
        System.out.println("Starting complete game flow test...");
        
        GameNotifier notifier = new GameNotifier();
        EventLogService logService = new EventLogService();
        notifier.addObserver(logService);
        GameService gameService = new GameService(notifier, logService);
        
        List<Player> players = Arrays.asList(
            new Player("P1", "Alice"),
            new Player("P2", "Bob")
        );
        
        List<Question> questions = Arrays.asList(
            new Question("Science", 100, "What is H2O?", "Water"),
            new Question("History", 200, "First US president?", "Washington")
        );
        
        // Execute game flow
        gameService.startGame(players, questions);
        
        // Player 1's turn
        Question question1 = gameService.selectQuestion("Science", 100);
        assertNotNull(question1);
        boolean correct1 = gameService.submitAnswer("Water");
        assertTrue(correct1);
        assertEquals(100, players.get(0).getScore());
        
        gameService.nextTurn();
        
        // Player 2's turn  
        Question question2 = gameService.selectQuestion("History", 200);
        assertNotNull(question2);
        boolean correct2 = gameService.submitAnswer("Washington");
        assertTrue(correct2);
        assertEquals(200, players.get(1).getScore());
        
        // Verify game completion
        assertTrue(gameService.isGameOver());
        assertTrue(logService.getEventCount() > 0);
        
        System.out.println("Game flow test passed");
    }
    
    @Test
    void testGameWithIncorrectAnswers() {
        System.out.println("Testing incorrect answers...");
        
        GameNotifier notifier = new GameNotifier();
        EventLogService logService = new EventLogService();
        GameService gameService = new GameService(notifier, logService);
        
        List<Player> players = Arrays.asList(new Player("P1", "Test"));
        List<Question> questions = Arrays.asList(
            new Question("Test", 100, "Test?", "Correct")
        );
        
        gameService.startGame(players, questions);
        
        gameService.selectQuestion("Test", 100);
        boolean correct = gameService.submitAnswer("Wrong");
        
        assertFalse(correct);
        assertEquals(0, players.get(0).getScore());
        
        System.out.println("Incorrect answer test passed");
    }
}