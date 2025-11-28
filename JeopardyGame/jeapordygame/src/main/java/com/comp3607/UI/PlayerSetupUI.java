package com.comp3607.UI;

import com.comp3607.service.EventLogService;
import com.comp3607.model.Player;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class PlayerSetupUI {
    private EventLogService logService;
    private Scanner scanner;
    
    public PlayerSetupUI(EventLogService logService, Scanner scanner) {
        this.logService = logService;
        this.scanner = scanner;
    }
    
    public List<Player> setupPlayers() {
        List<Player> players = new ArrayList<>();
        int playerCount = getPlayerCount();
        
        for (int i = 0; i < playerCount; i++) {
            String name = getPlayerName(i + 1);
            String playerId = "P" + (i + 1);
            players.add(new Player(playerId, name));
            logService.logPlayerName(playerId, name);
        }
        
        logService.logPlayerCount(players.size());
        System.out.println("Players setup: " + players.size() + " players ready");
        return players;
    }
    
    private int getPlayerCount() {
        while (true) {
            System.out.print("Enter number of players (1-4): ");
            try {
                int count = Integer.parseInt(scanner.nextLine());
                if (count >= 1 && count <= 4) return count;
                System.out.println("Please enter a number between 1 and 4");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }
    
    private String getPlayerName(int playerNumber) {
        System.out.print("Enter name for Player " + playerNumber + ": ");
        String name = scanner.nextLine().trim();
        return name.isEmpty() ? "Player " + playerNumber : name;
    }
}