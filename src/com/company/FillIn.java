package com.company;

public class FillIn extends Question {
    @Override
    public String toString() {
        String description = getDescription();
        String fillinText =  description.replace("{blank}", "_________");
        fillinText = fillinText + "\n"+ " Type your answer here: ";
        fillinText = fillinText +"\n"+ "----------------------------------";
        return fillinText;
    }
}

