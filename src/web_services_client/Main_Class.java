package web_services_client;

import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main_Class 
{
    public static void main(String[] args) throws ParseException
    {
        Services_Client client = new Services_Client();
        
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Username: ");
        String given_username = scan.next();
        System.out.print("Password: ");
        String given_password = scan.next();
        
        String token_response = client.Login(given_username, given_password);
        
        System.out.println("\n---------------------------------------------------------");
        System.out.println("JSON Response: " + token_response);
        System.out.println("---------------------------------------------------------\n");
        
        //turn the token response from String to JSONObject
        JSONParser parser = new JSONParser();
        JSONObject token_object = (JSONObject) parser.parse(token_response);

        String token = (String) token_object.get("token");

        if(token.equals("#ERROR#"))
        {
            System.out.println("Incorrect username or password. Permission denied.");
        }
        else
        {
            System.out.println("User's token (expires in 90 secs): " + token + "\n");
            
            System.out.println("Search the dictionary for words that...");
            System.out.print("...start with the letter... ");
            String input_first_letter = scan.next();
            String first_letter = input_first_letter.substring(0, 1);

            System.out.print("...and end with the letter... ");
            String input_last_letter = scan.next();
            String last_letter = input_last_letter.substring(0, 1);

            
            String dictionary_response = client.Words(first_letter, last_letter, token);
            
            System.out.println("\n---------------------------------------------------------");
            System.out.println("JSON Response: " + dictionary_response);
            System.out.println("---------------------------------------------------------\n");
            
            //turn the dictionary response from String to JSONObject
            JSONObject dictionary_object = (JSONObject) parser.parse(dictionary_response);
            
            String retrieved_username = (String) dictionary_object.get("user");
            JSONArray word_list = (JSONArray) dictionary_object.get("words");
           
            if(word_list.isEmpty()) //if no words are retrieved, either the token has expired either no words surrounded by those letters exist
            {
                if(retrieved_username.equals("#Permission Denied#"))
                    System.out.println("Token expired. Permission denied.");
                else
                    System.out.println("No words found that start with \"" + first_letter + "\" and end with \"" + last_letter + "\"");
            }
            else
            {
                System.out.println("Retrieved username: " + retrieved_username + "\n");
            
                System.out.println("Words that start with \"" + first_letter + "\" and end with \"" + last_letter + "\":");
                for(Object word : word_list)
                    System.out.println("\t" + word.toString());
            }
        }
        client.close();
    }
}