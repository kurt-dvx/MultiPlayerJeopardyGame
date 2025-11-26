package com.comp3607.UI;

import com.comp3607.model.Question;
import javax.swing.*;
import java.awt.*;

public class QuestionDialog extends JDialog {
    private String userAnswer;
    
    public interface AnswerSubmitListener {
        void onAnswerSubmitted(String answer);
    }
    
    public QuestionDialog(JFrame parent, Question question, String category, int value, 
                         AnswerSubmitListener listener) {
        super(parent, "Question - $" + value, true);
        setupDialog(question, category, value, listener);
    }
    
    private void setupDialog(Question question, String category, int value, AnswerSubmitListener listener) {
        setLayout(new BorderLayout());
        setSize(500, 300);
        setLocationRelativeTo(getParent());
        
        // Question area
        JTextArea questionArea = new JTextArea(question.getQuestionText());
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Arial", Font.BOLD, 14));
        add(new JScrollPane(questionArea), BorderLayout.CENTER);
        
        // Answer panel
        JPanel answerPanel = new JPanel(new BorderLayout());
        JTextField answerField = new JTextField();
        JButton submitButton = new JButton("Submit Answer");
        
        submitButton.addActionListener(e -> {
            String answer = answerField.getText().trim();
            if (!answer.isEmpty()) {
                userAnswer = answer;
                if (listener != null) {
                    listener.onAnswerSubmitted(answer);
                }
                dispose();
            }
        });
        
        answerPanel.add(new JLabel("Your answer: "), BorderLayout.WEST);
        answerPanel.add(answerField, BorderLayout.CENTER);
        answerPanel.add(submitButton, BorderLayout.EAST);
        
        add(answerPanel, BorderLayout.SOUTH);
    }
    
    public String getUserAnswer() {
        return userAnswer;
    }
}