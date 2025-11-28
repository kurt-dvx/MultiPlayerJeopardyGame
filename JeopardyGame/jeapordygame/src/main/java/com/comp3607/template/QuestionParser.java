package com.comp3607.template;

import com.comp3607.model.Question;
import java.util.List;

public interface QuestionParser {
    List<Question> parse(String filePath);
}