package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;
//interface for syncing our api pulls
public interface AnswerListAsyncResponse {
    void processFinish(ArrayList<Question> questionArrayList);

}
