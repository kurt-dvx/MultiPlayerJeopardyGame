package com.comp3607.UI;

import com.comp3607.controller.GameController;
import com.comp3607.model.*;
import com.comp3607.service.QuestionParser;
import com.comp3607.factory.QuestionParserFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class JeopardyFrame extends JFrame {
    private GameController controller;
    private JTextArea gameTextArea;
    private GameBoardPanel gameBoardPanel;
    private GameInfoPanel gameInfoPanel;
    
    public JeopardyFrame() {
        this.controller = new GameController();
        setupUI();
        initializeGame();
    }
    
    private void setupUI() {
        setTitle("🎯 Jeopardy Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Game info panel
        gameInfoPanel = new GameInfoPanel(controller, this::handleQuit);
        add(gameInfoPanel, BorderLayout.NORTH);
        
        // Game board panel
        gameBoardPanel = new GameBoardPanel(controller, this::handleQuestionSelected);
        add(gameBoardPanel, BorderLayout.CENTER);
        
        // Game text area
        gameTextArea = new JTextArea(10, 50);
        gameTextArea.setEditable(false);
        gameTextArea.setLineWrap(true);
        gameTextArea.setWrapStyleWord(true);
        add(new JScrollPane(gameTextArea), BorderLayout.SOUTH);
    }
    
    private void initializeGame() {
        // Setup players
        List<Player> players = List.of(
            new Player("P1", "Player 1"),
            new Player("P2", "Player 2")
        );
        
        // Load questions
        String fileName = "sample_game_CSV.csv";
        QuestionParser parser = QuestionParserFactory.createParser(fileName);
        List<Question> questions = parser.parse(fileName);
        
        if (questions.isEmpty()) {
            questions = createSampleQuestions();
        }
        
        // Initialize game
        controller.initializeGame(players, questions);
        startGame();
    }
    
    private void handleQuestionSelected(String category, int value) {
        gameBoardPanel.setButtonsEnabled(false);
        
        Question question = controller.selectQuestion(category, value);
        if (question != null) {
            QuestionDialog dialog = new QuestionDialog(this, question, category, value, 
                answer -> handleAnswerSubmitted(category, value, question, answer));
            dialog.setVisible(true);
        } else {
            gameBoardPanel.setButtonsEnabled(true);
        }
    }
    
    private void handleAnswerSubmitted(String category, int value, Question question, String answer) {
        boolean isCorrect = controller.submitAnswer(answer);
        showAnswerResult(isCorrect, question, value);
        
        if (!controller.isGameOver()) {
            controller.nextTurn();
            updateGameDisplay();
        } else {
            endGame();
        }
        
        gameBoardPanel.setButtonsEnabled(true);
        gameBoardPanel.updateBoard();
    }
    
    private void handleQuit() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to quit the game?", 
            "Quit Game", 
            JOptionPane.YES_NO_OPTION);
            
        if (result == JOptionPane.YES_OPTION) {
            controller.quitGame();
            JOptionPane.showMessageDialog(this, "Game quit. Reports generated.");
            dispose();
        }
    }
    
    private void showAnswerResult(boolean isCorrect, Question question, int value) {
        String message;
        if (isCorrect) {
            message = "✅ CORRECT! +$" + value;
        } else {
            int currentScore = controller.getCurrentPlayer().getScore();
            int pointsLost = Math.min(value, currentScore + value);
            message = "❌ WRONG! -$" + pointsLost + 
                     "\n💡 Correct answer: " + question.getAnswer();
        }
        
        message += "\n💰 New Score: $" + controller.getCurrentPlayer().getScore();
        gameTextArea.append("\n" + message + "\n");
        JOptionPane.showMessageDialog(this, message, "Answer Result", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateGameDisplay() {
        gameInfoPanel.updateInfo();
    }
    
    private void endGame() {
        StringBuilder results = new StringBuilder("\n🏁 GAME OVER!\n\n📊 FINAL SCORES:\n");
        
        List<Player> players = controller.getPlayers();
        Player winner = players.get(0);
        
        for (Player player : players) {
            results.append("   ").append(player.getName()).append(": $").append(player.getScore()).append("\n");
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        
        results.append("\n🏆 WINNER: ").append(winner.getName()).append(" with $").append(winner.getScore()).append("!");
        gameTextArea.append(results.toString());
        
        controller.generateReports();
        gameTextArea.append("\n\n✅ Reports generated: game_report.txt, game_event_log.csv");
    }
    
    private void startGame() {
        gameTextArea.setText("🎯 Jeopardy Game Started!\n\n");
        gameTextArea.append("Players: ");
        for (Player player : controller.getPlayers()) {
            gameTextArea.append(player.getName() + " ");
        }
        gameTextArea.append("\n\nSelect a question to begin!");
    }
    
    private List<Question> createSampleQuestions() {
        return List.of(
            new Question("Science", 100, "What is H2O?", "Water"),
            new Question("Science", 200, "What planet is known as the Red Planet?", "Mars"),
            new Question("History", 100, "Who was the first president of the United States?", "George Washington"),
            new Question("History", 200, "In what year did World War II end?", "1945")
        );
    }
}