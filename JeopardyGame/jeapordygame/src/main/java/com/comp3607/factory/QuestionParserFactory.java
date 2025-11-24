package com.comp3607.factory;

import com.comp3607.service.QuestionParser;
import com.comp3607.service.CSVQuestionParser;
import com.comp3607.service.JSONQuestionParser;
import com.comp3607.service.XMLQuestionParser;

public class QuestionParserFactory {
    public static QuestionParser createParser(String filePath) {
        if (filePath.endsWith(".csv")) {
            return new CSVQuestionParser();
        } else if (filePath.endsWith(".json")) {
            return new JSONQuestionParser();
        } else if (filePath.endsWith(".xml")) {
            return new XMLQuestionParser();
        }
        throw new IllegalArgumentException("Unsupported file format");
    }
}