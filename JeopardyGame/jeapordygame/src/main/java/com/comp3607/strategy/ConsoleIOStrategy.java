package com.comp3607.strategy;

import java.util.Scanner;

public class ConsoleIOStrategy implements InputStrategy, OutputStrategy {
    private Scanner scanner;
    
    public ConsoleIOStrategy(Scanner scanner) {
        this.scanner = scanner;
    }
    
    // InputStrategy implementations
    @Override
    public String readLine() {
        return scanner.nextLine();
    }
    
    @Override
    public int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }
    
    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    @Override
    public int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }
    
    
    @Override
    public void display(String message) {
        System.out.println(message);
    }
    
    @Override
    public void displayError(String message) {
        System.err.println("❌ " + message);
    }
    
    @Override
    public void displayFormatted(String message) {
        System.out.println(message);
    }
    
    @Override
    public void displayBanner(String message) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🎯 " + message);
        System.out.println("=".repeat(50));
    }
}