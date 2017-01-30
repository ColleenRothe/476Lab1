/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg476lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.*;
import java.util.ArrayList;

/**
 *
 * @author colleenrothe
 */
public class Driver {

    private static int dataPieces = 0;                                                                          //how many credit cards have been found?
    private static String regex = "(%B\\d{13,19}\\^[a-zA-Z]{2,26}/[a-zA-Z]{2,26}\\^\\d{7}[a-zA-Z0-9]*\\?)";     //regex to find track1
    private static String regex2 = "(;\\d{13,19}=\\d{14}\\d*\\?)";                                              //regex to find track2
    private static ArrayList<String> track1 = new ArrayList<>();                                                //to hold onto found track1's
    private static ArrayList<String> track2 = new ArrayList<>();                                                //to hold onto found track2's
    private static ArrayList<CardInfo> finalInfo = new ArrayList<>();                                           //to hold onto final cc info

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("memorydump.dmp"));
            String line;
            while ((line = reader.readLine()) != null) {
                //match track 1
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                //match track 2
                Pattern pattern2 = Pattern.compile(regex2);
                Matcher matcher2 = pattern2.matcher(line);
                //find track 1 (no repeats)
                while (matcher.find()) {
                    if (!track1.contains(matcher.group(1))) {
                        track1.add(matcher.group(1));
                    }
                }
                //find track 2 (no repeats)
                while (matcher2.find()) {
                    if (!track2.contains(matcher2.group(1))) {
                        track2.add(matcher2.group(1));
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        findData();
    }

    //find the PAN for each and check for a match
    public static void findData() {
        String panRegex = ".*B(\\d*)\\^.*";
        String panRegex2 = ".*;(\\d*)=.*";

        //for each of the track 1 strings, get the PAN 
        for (int k = 0; k < track1.size(); k++) {
            String pan = "";
            Pattern pattern = Pattern.compile(panRegex);
            Matcher matcher = pattern.matcher(track1.get(k));
            boolean isMatched = matcher.matches();
            if (isMatched == true) {
                pan = matcher.group(1);
            }
            //for each of the track2 strings, get the PAN
            for (String track : track2) {
                String pan2 = "";
                Pattern pattern2 = Pattern.compile(panRegex2);
                Matcher matcher2 = pattern2.matcher(track);
                while (matcher2.find()) {
                    pan2 = matcher2.group(1);
                    //see if the 2 PANs match
                    if (pan.equals(pan2)) {
                        //there is a match! increment the # of cc info found
                        dataPieces++;
                        match(track1.get(k), track);
                    }
                }
            }

        }

        printData();
    }

    //method to pull out all of the credit card info
    public static void match(String track1, String track2) {
        String name = "";
        String cardNum = "";
        String date = "";
        String pin = "";
        String cvvNum = "";

        //name
        String nameRegex = ".*\\^([a-zA-Z].*/[a-zA-z].*)\\^.*";
        name = matchHelper(track1, nameRegex);
        name = name.replace("/", " ");
        //card number
        String cardNumRegex = ".*B(\\d*)\\^.*";
        cardNum = matchHelper(track1, cardNumRegex);
        //
        String cardRegex = ".*=(\\d{14}).*";
        String info = matchHelper(track2, cardRegex);
        //date
        String tempDate = info.substring(0, 4);
        date = tempDate.substring(2, 4) + "/20" + tempDate.substring(0, 2);
        //pin
        pin = info.substring(7, 11);
        //cvv
        cvvNum = info.substring(11, 14);
        //create a new card 
        CardInfo cardInfo = new CardInfo(name, cardNum, date, pin, cvvNum);
        //add it to an arraylist of all the cards
        finalInfo.add(cardInfo);

    }

    //helper method to reduce typing this many times
    public static String matchHelper(String track, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(track);
        boolean isMatched = matcher.matches();
        if (isMatched == true) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    //method to print out the final card information
    public static void printData() {
        System.out.println("There is " + dataPieces + " piece(s) of credit card information in the memory data!");
        for (int i = 0; i < finalInfo.size(); i++) {
            System.out.println("<Information of the " + (i + 1) + " credit card>:");
            System.out.println("Cardholder's Name: " + finalInfo.get(i).getName());
            System.out.println("Card Number: " + finalInfo.get(i).getCardNum());
            System.out.println("Expiration Date: " + finalInfo.get(i).getDate());
            System.out.println("Encrypted PIN: " + finalInfo.get(i).getPin());
            System.out.println("CVV Number: " + finalInfo.get(i).getCvvNum());
            System.out.println("");
        }
    }

}
