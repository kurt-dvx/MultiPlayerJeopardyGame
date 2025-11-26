package com.comp3607.strategy;

public interface InputStrategy {
    String readLine();
    int readInt();
    String readLine(String prompt);
    int readInt(String prompt);
}