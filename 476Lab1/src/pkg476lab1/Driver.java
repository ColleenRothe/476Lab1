/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg476lab1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author colleenrothe
 */
public class Driver {

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
    }
    
}
