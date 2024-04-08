package com.exercises.alura.Screen.Music.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ChatGPT {
    public static String obtainData(String text){
        OpenAiService service = new OpenAiService("${GPT_APIKey}");

        CompletionRequest requisition = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("Who is : " + text)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var response = service.createCompletion(requisition);
        return response.getChoices().get(0).getText();
    }
}
