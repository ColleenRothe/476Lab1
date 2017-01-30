/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg476lab1;

/**
 *
 * @author colleenrothe
 * This is a class to represent a cardholder's information in the memory dump
 */
public class CardInfo {
    private String name;
    private String cardNum;
    private String date;
    private String pin;
    private String cvvNum;
    
    public CardInfo(String name, String cardNum, String date, String pin, String cvvNum){
        this.name = name;
        this.cardNum = cardNum;
        this.date = date;
        this.pin = pin;
        this.cvvNum = cvvNum;
    }
    
    //get methods to access information
    
    public String getName(){
        return name;
    }
    public String getCardNum(){
        return cardNum;
    }public String getDate(){
        return date;
    }public String getPin(){
        return pin;
    }public String getCvvNum(){
        return cvvNum;
    }    
}
