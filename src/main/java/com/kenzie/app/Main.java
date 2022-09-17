package com.kenzie.app;

// import necessary libraries


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.dto.CluesDTO;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    /* Java Fundamentals Capstone project:
       - Define as many variables, properties, and methods as you decide are necessary to
       solve the program requirements.
       - You are not limited to only the class files included here
       - You must write the HTTP GET call inside the CustomHttpClient.sendGET(String URL) method
         definition provided
       - Your program execution must run from the main() method in Main.java
       - The rest is up to you. Good luck and happy coding!

     */


    public static void main(String[] args) {
        try {
            //declare variables
            String URL = "https://jservice.kenzie.academy/api/clues";
            String httpResponse;
            //connect to URL to get a list of questions
            httpResponse = CustomHttpClient.sendGET(URL);
            //1. instantiate objectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            //2. declare DTO object
            CluesDTO cluesDTOObj;
            //3. read data-readValue()
            cluesDTOObj = objectMapper.readValue(httpResponse, CluesDTO.class);

            ArrayList<String> questionList = new ArrayList<>();
            for (int i = 0; i < cluesDTOObj.getClues().size(); i++) {
                questionList.add("\"Category: " + cluesDTOObj.getClues().get(i).getCategory().getTitle() +

                        "\" \"Question: " + cluesDTOObj.getClues().get(i).getQuestion());
            }
            Random random = new Random();
            int randomNum = random.nextInt(questionList.size());
            Scanner scanner = new Scanner(System.in);
            String userInput;
            int totalScore = 0;
            int count = 1;
            System.out.println("Please answer the following 10 questions.");
            while (count <= 10) {
                System.out.println(questionList.get(randomNum));
                userInput = scanner.nextLine();
                if (userInput.isEmpty() || userInput.isBlank()) {
                    System.out.println("Invalid entry. Please answer question.");
                } else if (userInput.equalsIgnoreCase(cluesDTOObj.getClues().get(randomNum).getAnswer())) {
                    System.out.printf("Congratulations! Your answer is correct.");
                    totalScore++;
                    System.out.println("\nYour total score is: " + totalScore);
                    count++;
                    randomNum = random.nextInt(questionList.size());
                } else {
                    System.out.println("Sorry, your answer is not correct.");
                    System.out.println("Correct answer is: " + cluesDTOObj.getClues().get(randomNum).getAnswer());
                    System.out.println("Your total score is: " + totalScore);
                    randomNum = random.nextInt(questionList.size());
                    count++;
                }
            }
            System.out.println("You have answered 10 questions. Your total score is: " + totalScore);

        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}

