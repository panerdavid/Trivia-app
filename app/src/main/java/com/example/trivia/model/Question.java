package com.example.trivia.model;

public class Question {
    private String question;
    private Boolean answerTrue;

    public Question(String question, Boolean answerTrue) {
        this.question = question;
        this.answerTrue = answerTrue;
    }

    public void setQuestion(String answer) {
        this.question = answer;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    public String getQuestion() {
        return question;
    }

    public Boolean getAnswerTrue() {
        return answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
