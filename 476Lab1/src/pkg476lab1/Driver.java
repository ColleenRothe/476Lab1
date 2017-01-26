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

    private static int dataPieces = 0;

    private static String regex = ".*(%B\\d{13,19}\\^[a-zA-Z]{2,26}/[a-zA-Z]{2,26}\\^\\d{7}[a-zA-Z0-9]*\\?).*";
    private static String regex2 = ".*(;\\d{13,19}=\\d{14}[a-zA-Z0-9]*\\?).*";
    private static ArrayList<String> track1 = new ArrayList<>();
    private static ArrayList<String> track2 = new ArrayList<>();
    private static ArrayList<CardInfo> finalInfo = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("test.rtf"));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                boolean isMatched = matcher.matches();
                if (isMatched == true) {
                    track1.add(matcher.group(1));
                }

                Pattern pattern2 = Pattern.compile(regex2);
                Matcher matcher2 = pattern2.matcher(line);
                boolean isMatched2 = matcher2.matches();
                if (isMatched2 == true) {
                    track2.add(matcher2.group(1));

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

    public static void findData() {
        //print to test.....
        for (int i = 0; i < track1.size(); i++) {
            System.out.println(track1.get(i));
        }
        for (int j = 0; j < track2.size(); j++) {
            System.out.println(track2.get(j));
        }
        //find the PAN for each and check for a match
        String panRegex = ".*B(\\d*)\\^.*";
        String panRegex2=".*;(\\d*)=.*";
        
        for (int k = 0; k < track1.size(); k++) {
            String pan = "";
            Pattern pattern = Pattern.compile(panRegex);
            Matcher matcher = pattern.matcher(track1.get(k));
            boolean isMatched = matcher.matches();
            if (isMatched == true) {
                System.out.println("PAN");
                System.out.println(matcher.group(1));
                pan = matcher.group(1);
            }
            for (int l = 0; l < track2.size(); l++) {
                String pan2 = "";
                Pattern pattern2 = Pattern.compile(panRegex2);
                Matcher matcher2 = pattern2.matcher(track2.get(k));
                boolean isMatched2 = matcher2.matches();
                if (isMatched2 == true) {
                    System.out.println("PAN2");
                    System.out.println(matcher2.group(1));
                    pan2 = matcher2.group(1);
                    
                    if(pan.equals(pan2)){
                        System.out.println("MATCH");
                        dataPieces ++;
                        match(track1.get(k),track2.get(l));
                        
                    }
                    else{
                        System.out.println("NO MATCH");
                    }
                }
            }
        }

        printData();
    }
    
    public static void match(String track1, String track2){
        String name="";
        String cardNum="";
        String date="";
        String pin="";
        String cvvNum="";
        
        String nameRegex = ".*\\^([a-zA-Z].*/[a-zA-z].*)\\^.*";
        name=matchHelper(track1,nameRegex);
        name = name.replace("/", " ");
        
        String cardNumRegex=".*B(\\d*)\\^.*";
        cardNum=matchHelper(track1,cardNumRegex);
        System.out.println("CARD NUMBER IS: "+cardNum);
        
        String cardRegex = ".*=(\\d{14}).*";
        String info = matchHelper(track2, cardRegex);
        System.out.println("INFO: "+info);
        //date
        String tempDate = info.substring(0,4);
        System.out.println("DATE: "+tempDate);
        
        date=tempDate.substring(2,4)+"/20"+tempDate.substring(0,2);
        System.out.println("MONTH: "+date);
        
        
        pin=info.substring(7,11);
        cvvNum=info.substring(11,14);
        System.out.println("CVVNUM: "+cvvNum);
        CardInfo cardInfo = new CardInfo(name,cardNum,date,pin,cvvNum);
        finalInfo.add(cardInfo);
        
        
    }
    
    public static String matchHelper(String track, String regex){
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(track);
                boolean isMatched = matcher.matches();
                if (isMatched == true) {
                    return matcher.group(1);
                }
                else{
                    return "";
                }
    }

    public static void printData() {
        System.out.println("There is " + dataPieces + " piece(s) of credit card information in the memory data!");
        for(int i = 0; i<finalInfo.size();i++){
            System.out.println("<Information of the "+(i+1)+" credit card>:");
            System.out.println("Cardholder's Name: "+finalInfo.get(i).getName());
            System.out.println("Card Number: "+finalInfo.get(i).getCardNum());
            System.out.println("Expiration Date: "+finalInfo.get(i).getDate());
            System.out.println("Encrypted PIN: "+finalInfo.get(i).getPin());
            System.out.println("CVV Number: "+finalInfo.get(i).getCvvNum());
            System.out.println("");
        }
    }

}
