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

/**
 *
 * @author colleenrothe
 */
public class Driver {
    private static int dataPieces=0;
    
    private static String regex = ".*%B\\d{13,19}\\^[a-zA-Z]{2,26}/[a-zA-Z]{2,26}\\^\\d{7}[a-zA-Z0-9]*\\?.*";
    private static String regex2=".*;\\d{13,19}=\\d{14}[a-zA-Z0-9]*\\?.*";

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
                System.out.println(line);
                Pattern pattern = Pattern.compile(regex2);
                Matcher matcher = pattern.matcher(line);
                boolean isMatched = matcher.matches();
                if(isMatched == true){
                    dataPieces++;
                   
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
        System.out.println("There is "+dataPieces+" piece of credit card information in the memory data!");
    }
    
}
