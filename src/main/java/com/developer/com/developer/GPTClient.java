 package com.developer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;


public class GPTClient {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/engines/davinci-codex/completions";
    private static final String API_KEY = "YOUR_API_KEY";

    public static String generateCode() {
        // User input
        String userPrompt = "/* Your prompt text here */";

        // Construct the API request payload
        String requestBody = "{\"prompt\":\"" + userPrompt + "\",\"max_tokens\":100}";

        try {
            // Create the URL object
            URL url = new URL(API_ENDPOINT);

            // Create the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

            // Enable output and send the request payload
            connection.setDoOutput(true);
            connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

            // Get the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Extract and return the generated code from the response
            return extractGeneratedCode(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
 private static String extractGeneratedCode(String response) {
    // Parse the response JSON and extract the generated code
    // Modify this method based on the specific structure of the response JSON
    
    String generatedCode = null;
    
    try {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray choices = jsonObject.getJSONArray("choices");
        if (choices.length() > 0) {
            JSONObject firstChoice = choices.getJSONObject(0);
            String text = firstChoice.getString("text");
            
            // Check if the generated text resembles code
            if (isCode(text)) {
                generatedCode = text;
            }
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }

    // If the generated code is null, assume it's plain text or a command
    if (generatedCode == null) {
        // Check if the response resembles a command
        if (isCommand(response)) {
            // Execute the command
            executeCommand(response);
        } else {
            // Plain text response
            generatedCode = response;
        }
    }

    return generatedCode;
}

private static boolean isCode(String text) {
    // Add your logic to determine if the text resembles code
    // You can use regex, language-specific patterns, or other heuristics
    // Return true if the text is identified as code, false otherwise
    return false;
}

private static boolean isCommand(String text) {
    // Add your logic to determine if the text resembles a command
    // You can check for specific keywords, patterns, or formats
    // Return true if the text is identified as a command, false otherwise
    return false;
}

private static void executeCommand(String command) {
    // Execute the command using the CommandExecutor class or your preferred method
    // Add your logic to run the command on the user's terminal
}

   
}

