package com.company;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;

public class Quiz  {
    private String name;
    private ArrayList<Question> questions;

    public static Quiz loadFromFile(String pathForTxt) throws InvalidQuizFormatException, FileNotFoundException  {
        Quiz my_quiz  = new Quiz();
        File file = new File(pathForTxt);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String new_question = sc.nextLine();
            ArrayList<String> listOfAnswers = new ArrayList<>();
            String answer = sc.nextLine();
            while (!(answer.isEmpty())){
                listOfAnswers.add(answer);
                if (sc.hasNextLine()){
                    answer = sc.nextLine();}
                else{
                    break;}
            }
            int size = listOfAnswers.size();

            String correctAns = listOfAnswers.get(0);
            if (size > 1) {
                Test test_question = new Test();
                test_question.setAnswer(correctAns);
                test_question.setDescription(new_question);

                Collections.shuffle(listOfAnswers);
                String[] options = new String[size];
                int index = 0;
                for ( String ans: listOfAnswers){
                    options[index] = ans;
                    index++;
                }
                my_quiz.addQuestion(test_question);
                test_question.setOptions(options);

            } else if (size==1){

                FillIn fill_in_question = new FillIn();
                my_quiz.addQuestion(fill_in_question);
                fill_in_question.setAnswer(correctAns);
                fill_in_question.setDescription(new_question);
            }
            else{
                throw new InvalidQuizFormatException("Wrong Format of Quiz!!!");
            }
        }
        Collections.shuffle(my_quiz.questions);

        return my_quiz;
    }

    public String getName() {
        return name;
    }
    public void setName(String nameOfTheQuiz) {
        name = nameOfTheQuiz;
    }
    public Quiz() throws InvalidQuizFormatException {
        questions = new ArrayList<>();
    }
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question new_question) {
        questions.add(new_question);
    }


    public boolean check(Question question, int ind, String your_answer){
        if (question instanceof FillIn) {
            if (!your_answer.toLowerCase().equals(questions.get(ind).getAnswer())){
                System.out.println("Incorrect answer!");
            return false;}
            System.out.println("Correct answer!");
                return true;


        } else if (question instanceof Test) {

                    String correctAns = questions.get(ind).getAnswer();
                    if (your_answer.equals(correctAns)) {
                        return true;
                    }
                        return false;

        }return false;
    }
    @Override
    public String toString() {
        return  "____________________________________________\n" +
                "Welcome to my \"" + getName() + "\" QUIZ! Good Luck!\n";
    }
}


