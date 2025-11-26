package com.comp3607.strategy;

public interface OutputStrategy {
    void display(String message);
    void displayError(String message);
    void displayFormatted(String message);
    void displayBanner(String message);
}