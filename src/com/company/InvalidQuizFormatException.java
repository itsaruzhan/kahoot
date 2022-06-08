package com.company;


public class InvalidQuizFormatException extends Exception{

    public InvalidQuizFormatException(String errorMessage) {
        super(errorMessage);
    }
    public InvalidQuizFormatException(){
        super();
    }

}
