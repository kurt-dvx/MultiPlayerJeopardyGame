package com.comp3607.UI;

import com.comp3607.controller.GameController;
import javax.swing.*;
import java.awt.*;

public class GameInfoPanel extends JPanel {
    private JLabel currentPlayerLabel;
    private JLabel scoreLabel;
    private JButton quitButton;
    private GameController controller;
    
    public interface QuitListener {
        void onQuitRequested();
    }
    
    public GameInfoPanel(GameController controller, QuitListener quitListener) {
        this.controller = controller;
        setLayout(new GridLayout(1, 3));
        
        currentPlayerLabel = new JLabel("Current Player: " + controller.getCurrentPlayer().getName());
        scoreLabel = new JLabel("Score: $" + controller.getCurrentPlayer().getScore());
        quitButton = new JButton("Quit Game");
        
        quitButton.addActionListener(e -> {
            if (quitListener != null) {
                quitListener.onQuitRequested();
            }
        });
        
        add(currentPlayerLabel);
        add(scoreLabel);
        add(quitButton);
    }
    
    public void updateInfo() {
        currentPlayerLabel.setText("Current Player: " + controller.getCurrentPlayer().getName());
        scoreLabel.setText("Score: $" + controller.getCurrentPlayer().getScore());
    }
}