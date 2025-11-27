package com.comp3607.config;

import com.comp3607.strategy.ReportGenerator;
import com.comp3607.strategy.TextReportGenerator;

public class GameConfig {
    // File paths
    public static final String DEFAULT_QUESTIONS_FILE = "sample_game_CSV.csv";
    public static final String TEXT_REPORT_FILE = "game_report.txt";
    public static final String EVENT_LOG_FILE = "game_event_log.csv";
    
    // Game settings
    public static final int MIN_PLAYERS = 1;
    public static final int MAX_PLAYERS = 4;
    
    // Simple quit system
    public static final String CATEGORY_QUIT_COMMAND = "QUIT";
    
    // Factory methods
    public static ReportGenerator getDefaultReportGenerator() {
        return new TextReportGenerator();
    }
    
    public static boolean isValidPlayerCount(int count) {
        return count >= MIN_PLAYERS && count <= MAX_PLAYERS;
    }
}