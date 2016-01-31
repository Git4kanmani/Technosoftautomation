package com.technosoft.auto.framework.chunks;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Generators {

	/*
	 * Retrieve current time in the format that is required for RFR request.
	 * Payload: YYYY-MM-DD
	 * 
	 * 
	 * Author: Michael Ovchinnikov
	 * Last modified: 11/30/2015
	 */
	    public static String getCurrentDate() {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        System.out.println(df.format(date));
	        String currentDate = df.format(date);
	        return currentDate;
	    }
	
	/*
	 * Below method will generate a phone number for a new guest
	 * 
	 * Author: Michael Ovchinnikov
	 * Last modified: 11/30/2015
	 */
	    public static String generatePhoneNumber() throws IOException 
	    {
	        System.out.println("===============Started generating a phone number================");
	        String phonePrefix = "917555";  
	        
	        // DEPRECATED (Reading from a SQL database instead): Generating working directory and path
	        /*String workingDirectory = System.getProperty("user.dir");
	        String pathBlueprint = workingDirectory + "/src/main/java/com/reserve/widget/utilities/generatedPhoneNumber";
	        Path path = Paths.get(pathBlueprint);
	        
	        // Reading the file and extracting count as string
	        Properties p = new Properties();
	        BufferedReader reader = Files.newBufferedReader(path);
	        p.load(reader);
	        reader.close();
	        String currentCountAsString;
	        currentCountAsString = p.getProperty("phone");*/
	        
	        String currentCountAsString = DbUtils.getCounter("phone");
	        System.out.println("Extracted current count: " + currentCountAsString);
	        
	        // Converting counter to an integer and incrementing it +1 
	        Integer counter;
	        counter = (Integer.valueOf(currentCountAsString.substring(0, 4))) + 1;
	        System.out.println("Incremented counter: " + counter);
	        
	        // Converting new counter to a string using decimal formatter
	        String newCountAsString;
	        DecimalFormat myFormatter = new DecimalFormat("0000");
	        newCountAsString = myFormatter.format(counter);
	        DbUtils.setCounter("phone", newCountAsString);
	        /*// DEPRECATED (Writing to a SQL database instead): Writing new counter value to file
	        BufferedWriter writer = Files.newBufferedWriter(path);
	        writer.write("phone=" + newCountAsString + "\n");
	        writer.close();*/
	        
	        
	        String phoneNumber = phonePrefix + newCountAsString;
	        System.out.printf("New generated and saved phone number is: %s%n", phoneNumber);
	        return phoneNumber;
	    }
	    
	    /*
	     * Below method will generate a restaurant name
	     * 
	     * Author: Michael Ovchinnikov
	     * Last modified: 11/30/2015
	     */
	        public static String generateRestaurantName(String restaurantName) throws IOException 
	        {
	            System.out.println("=============== Started generating a new restaurant name ================"); 
	            
	            // Generating working directory and path
	            String workingDirectory = System.getProperty("user.dir");
	            String pathBlueprint = workingDirectory + "/src/main/java/com/reserve/widget/utilities/generatedRestaurantCount";
	            Path path = Paths.get(pathBlueprint);
	            
	            // Reading the file and extracting count as string
	            Properties p = new Properties();
	            BufferedReader reader = Files.newBufferedReader(path);
	            p.load(reader);
	            reader.close();
	            String currentCountAsString;
	            currentCountAsString = p.getProperty("count");
	            System.out.println("Extracted current count: " + currentCountAsString);
	            
	            // Converting counter to an integer and incrementing it +1 
	            Integer counter;
	            counter = (Integer.valueOf(currentCountAsString.substring(0, 4))) + 1;
	            System.out.println("Incremented counter: " + counter);
	            
	            // Converting new counter to a string using decimal formatter
	            String newCountAsString;
	            DecimalFormat myFormatter = new DecimalFormat("0000");
	            newCountAsString = myFormatter.format(counter);
	            
	            // Writing new counter value to file
	            BufferedWriter writer = Files.newBufferedWriter(path);
	            writer.write("count=" + newCountAsString + "\n");
	            writer.close();
	            String newRestaurantName = restaurantName + "(" + newCountAsString + ")";
	            System.out.printf("New generated restaurant name is: %s%n", newRestaurantName);
	            return newRestaurantName;
	        }

	        /*
	         * Below method will generate a new email
	         * 
	         * Author: Michael Ovchinnikov
	         * Last modified: 11/30/2015
	         */
	            public static String generateEmail() throws IOException 
	            {
	                System.out.println("=============== Started email generation ================");
	                String emailPrefix = "qatester";  
	                String emailSuffix = "@reserve.com";
	                
	                // Generating working directory and path
	                String workingDirectory = System.getProperty("user.dir");
	                String pathBlueprint = workingDirectory + "/src/main/java/com/reserve/widget/utilities/generatedEmailAddress";
	                Path path = Paths.get(pathBlueprint);
	                
	                // Reading the file and extracting count as string
	                Properties p = new Properties();
	                BufferedReader reader = Files.newBufferedReader(path);
	                p.load(reader);
	                reader.close();
	                String currentCountAsString;
	                currentCountAsString = p.getProperty("count");
	                System.out.println("Extracted current count: " + currentCountAsString);
	                
	                // Converting counter to an integer and incrementing it +1 
	                Integer counter;
	                counter = (Integer.valueOf(currentCountAsString.substring(0, 4))) + 1;
	                System.out.println("Incremented counter: " + counter);
	                
	                // Converting new counter to a string using decimal formatter
	                String newCountAsString;
	                DecimalFormat myFormatter = new DecimalFormat("0000");
	                newCountAsString = myFormatter.format(counter);
	                
	                // Writing new counter value to file
	                BufferedWriter writer = Files.newBufferedWriter(path);
	                writer.write("count=" + newCountAsString + "\n");
	                writer.close();
	                
	                // Printing and returning a new email value
	                String email = emailPrefix + newCountAsString + emailSuffix;
	                System.out.printf("New generated and saved phone number is: %s%n", email);
	                return email;
	            }
}
