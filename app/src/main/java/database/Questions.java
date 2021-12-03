package database;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private List<String> questions = new ArrayList<>();
    private List<List<String>> answers = new ArrayList<>();

    Questions() {

    }

    public Questions(List<String> questions, List<List<String>> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public List<List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(List<List<String>> answers) {
        this.answers = answers;
    }
}
