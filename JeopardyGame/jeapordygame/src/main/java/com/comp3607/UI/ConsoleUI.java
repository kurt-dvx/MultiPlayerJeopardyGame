package com.comp3607.UI;

import com.comp3607.service.GameService;
import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import com.comp3607.model.Question;
import java.util.Scanner;
import java.util.List;

public class ConsoleUI implements GameUI {
    private EventLogService logService;
    private PlayerSetupUI playerSetup;
    private GameplayUI gameplay;
    private ResultsUI results;
    
    public ConsoleUI(EventLogService logService, Scanner scanner) {
        this.logService = logService;
        this.playerSetup = new PlayerSetupUI(logService, scanner);
        this.gameplay = new GameplayUI(logService, scanner);
        this.results = new ResultsUI();
    }
    
    @Override public void displayWelcome() { results.displayWelcome(); }
    @Override public List<Player> setupPlayers() { return playerSetup.setupPlayers(); }
    @Override public void displayPlayerTurn(Player player) { gameplay.displayPlayerTurn(player); }
    @Override public String selectCategory(GameService gameService, Player player) { return gameplay.selectCategory(gameService, player); }
    @Override public int selectQuestionValue(GameService gameService, String category, Player player) { return gameplay.selectQuestionValue(gameService, category, player); }
    @Override public String askQuestion(Question question, String category, int value) { return gameplay.askQuestion(question, category, value); }
    @Override public void displayAnswerResult(boolean isCorrect, int value, Question question, Player player) { gameplay.displayAnswerResult(isCorrect, value, question, player); }
    @Override public void showFinalResults(GameService gameService) { results.showFinalResults(gameService); }
    @Override public void showCurrentScores(GameService gameService) { results.showCurrentScores(gameService); }
}