package com.company;
import java.util.ArrayList;

public class Test extends Question {
    private int numOfOptions;
    private String[] options;
    private ArrayList<Character> labels;

    public Test() throws NullPointerException {
        labels = new ArrayList<>();
        numOfOptions = 4;
        options = new String[numOfOptions];
        for (int i = 0; i < 4; i++)
            labels.add((char) ('A'+i));

    }
    public void setOptions(String[] options) {
        this.options = options;
    }
    public String getOptionAt(int optionIndex) {
        return options[optionIndex];
    }

    @Override
    public String toString() {
        String testText = getDescription()+"\n";
        for (int i = 0; i < numOfOptions; i++) {
            testText =testText+ labels.get(i) + ") " + getOptionAt(i) + "\n";
        }
        testText = testText + "Enter the correct choice: ";
        testText = testText + "\n"+ "----------------------------------";
        return testText;
    }
}
