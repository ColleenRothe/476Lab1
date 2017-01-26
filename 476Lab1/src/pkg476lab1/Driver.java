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
    private static int dataPieces=0;
    
    private static String regex = ".*(%B\\d{13,19}\\^[a-zA-Z]{2,26}/[a-zA-Z]{2,26}\\^\\d{7}[a-zA-Z0-9]*\\?).*";
    private static String regex2=".*(;\\d{13,19}=\\d{14}[a-zA-Z0-9]*\\?).*";
    private static ArrayList <String> track1 = new ArrayList<>();
    private static ArrayList <String> track2 = new ArrayList<>();


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("memorydump.dmp"));
            String line;
            while((line=reader.readLine())!=null){
                //System.out.println(line);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                boolean isMatched = matcher.matches();
                if(isMatched == true){
                    track1.add(matcher.group(1));  
                }
                
                Pattern pattern2 = Pattern.compile(regex2);
                Matcher matcher2 = pattern2.matcher(line);
                boolean isMatched2 = matcher2.matches();
                if(isMatched2 == true){
                    track2.add(matcher2.group(1));  

                }
                
                
          
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(reader!=null){
                    reader.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        findData();
    }
    
    public static void findData(){
        //print to test.....
        for(int i=0;i<track1.size();i++){
            System.out.println(track1.get(i));
        }
        for(int j=0;j<track2.size();j++){
            System.out.println(track2.get(j));
        }
        //find the PAN for each and check for a match
        for(int k=0;k<track1.size();k++){
            String pan = "";
        }
        
        
        printData();        
    }
    
    
    public static void printData(){
        System.out.println("There is "+dataPieces+" piece(s) of credit card information in the memory data!");
    }
    
}
