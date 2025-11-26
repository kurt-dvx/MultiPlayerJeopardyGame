package com.comp3607.UI;

import com.comp3607.controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameBoardPanel extends JPanel {
    private JButton[][] questionButtons;
    private GameController controller;
    private QuestionSelectionListener listener;
    
    public interface QuestionSelectionListener {
        void onQuestionSelected(String category, int value);
    }
    
    public GameBoardPanel(GameController controller, QuestionSelectionListener listener) {
        this.controller = controller;
        this.listener = listener;
        setLayout(new BorderLayout());
        setupBoard();
    }
    
    private void setupBoard() {
        List<String> categories = controller.getCategories();
        JPanel gridPanel = new JPanel(new GridLayout(categories.size() + 1, 6));
        
        // Header row
        gridPanel.add(new JLabel("Categories", JLabel.CENTER));
        for (int value : new int[]{100, 200, 300, 400, 500}) {
            gridPanel.add(new JLabel("$" + value, JLabel.CENTER));
        }
        
        // Question buttons
        questionButtons = new JButton[categories.size()][5];
        
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);
            gridPanel.add(new JLabel(category, JLabel.CENTER));
            
            for (int j = 0; j < 5; j++) {
                int value = (j + 1) * 100;
                JButton button = createQuestionButton(category, value);
                questionButtons[i][j] = button;
                gridPanel.add(button);
            }
        }
        
        add(gridPanel, BorderLayout.CENTER);
    }
    
    private JButton createQuestionButton(String category, int value) {
        JButton button = new JButton("$" + value);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        List<Integer> availableValues = controller.getAvailableValues(category);
        if (!availableValues.contains(value)) {
            button.setEnabled(false);
            button.setText("---");
        } else {
            button.addActionListener(e -> {
                if (listener != null) {
                    listener.onQuestionSelected(category, value);
                }
            });
        }
        
        return button;
    }
    
    public void updateBoard() {
        List<String> categories = controller.getCategories();
        
        for (int i = 0; i < categories.size(); i++) {
            String category = categories.get(i);
            List<Integer> availableValues = controller.getAvailableValues(category);
            
            for (int j = 0; j < 5; j++) {
                int value = (j + 1) * 100;
                JButton button = questionButtons[i][j];
                
                boolean isAvailable = availableValues.contains(value);
                button.setEnabled(isAvailable);
                button.setText(isAvailable ? "$" + value : "---");
            }
        }
    }
    
    public void setButtonsEnabled(boolean enabled) {
        for (JButton[] row : questionButtons) {
            for (JButton button : row) {
                if (button.isEnabled()) {
                    button.setEnabled(enabled);
                }
            }
        }
    }
}