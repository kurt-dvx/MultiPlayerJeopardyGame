package com.comp3607;

import com.comp3607.service.GameOrchestrator;
import com.comp3607.service.EventLogService;
import com.comp3607.strategy.ConsoleIOStrategy;
import com.comp3607.UI.ConsoleUI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        launchConsole();
    }
    
    private static void launchConsole() {
        Scanner scanner = new Scanner(System.in);
        EventLogService logService = new EventLogService();
        ConsoleIOStrategy ioStrategy = new ConsoleIOStrategy(scanner);
        ConsoleUI ui = new ConsoleUI(logService, ioStrategy, ioStrategy);
        
        try {
            // USE THE ORCHESTRATOR - ALL GAME LOGIC IS NOW HERE!
            GameOrchestrator orchestrator = new GameOrchestrator(ui, logService);
            boolean success = orchestrator.runGame();
            
            if (success) {
                System.out.println("🎉 Game completed successfully!");
            } else {
                System.out.println("⚠️ Game completed with issues");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Fatal error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("👋 Thank you for playing Jeopardy!");
        }
    }
}