package com.comp3607.unit.service;

import com.comp3607.model.Player;
import com.comp3607.model.Question;
import com.comp3607.observer.GameNotifier;
import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    private GameService gameService;
    private List<Player> players;
    private List<Question> questions;
    
    @BeforeEach
    void setUp() {
        GameNotifier notifier = new GameNotifier();
        EventLogService logService = new EventLogService();
        gameService = new GameService(notifier, logService); 
        
        players = Arrays.asList(
            new Player("P1", "Alice"),
            new Player("P2", "Bob")
        );
        
        questions = Arrays.asList(
            new Question("Science", 100, "What is H2O?", "Water"),
            new Question("History", 200, "First US president?", "Washington")
        );
        
        gameService.startGame(players, questions);
    }
    
    @Test
    void testGameInitialization() {
        assertEquals(2, gameService.getPlayers().size());
        assertEquals("Alice", gameService.getCurrentPlayer().getName());
        assertFalse(gameService.isGameOver());
        System.out.println("Game initialization test passed");
    }
    
    @Test
    void testSelectQuestion() {
        Question question = gameService.selectQuestion("Science", 100);
        
        assertNotNull(question);
        assertEquals("Science", question.getCategory());
        assertEquals(100, question.getValue());
        assertTrue(question.isUsed());
        System.out.println("Question selection test passed");
    }
    
    @Test
    void testSubmitCorrectAnswer() {
        gameService.selectQuestion("Science", 100);
        boolean isCorrect = gameService.submitAnswer("Water");
        
        assertTrue(isCorrect);
        assertEquals(100, players.get(0).getScore()); 
        System.out.println("Correct answer test passed");
    }
    
    @Test
    void testSubmitWrongAnswer() {
        gameService.selectQuestion("Science", 100);
        boolean isCorrect = gameService.submitAnswer("WrongAnswer");
        
        assertFalse(isCorrect);
        assertEquals(0, players.get(0).getScore());
        System.out.println("Wrong answer test passed");
    }
    
    @Test
    void testNextTurn() {
        Player firstPlayer = gameService.getCurrentPlayer();
        gameService.nextTurn();
        Player secondPlayer = gameService.getCurrentPlayer();
        
        assertNotEquals(firstPlayer, secondPlayer);
        assertEquals("Bob", secondPlayer.getName());
        System.out.println("Turn management test passed");
    }
    
    @Test
    void testGameOverWhenAllQuestionsUsed() {
        gameService.selectQuestion("Science", 100);
        gameService.selectQuestion("History", 200);
        
        assertTrue(gameService.isGameOver());
        System.out.println("Game over condition test passed");
    }
}