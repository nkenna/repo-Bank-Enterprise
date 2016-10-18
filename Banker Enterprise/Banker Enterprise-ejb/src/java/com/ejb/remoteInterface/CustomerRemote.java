/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.remoteInterface;

import com.ejb.entity.Customer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;
import javafx.scene.image.Image;

import javax.ejb.Remote;

/**
 *
 * @author STEINACOZ-PC
 */
@Remote
public interface CustomerRemote {
    
    //Customer Care Unit
    void CreateNewAcct(Customer customer, String fi, String s, String emailAdr);
    void deleteAcct(Customer customer, String acctNnmber);
    void updateAcct(Customer customer, String acctnum, String fName, String mName, String lName, String acctType, String gender, String Nationality,
            String address, String email, String phone, Date dod, String filename);
    Customer acctDetails (Customer customer, String acctNumber);
    
    //Teller unit
    void depositMoney (String accountNumber, double amount);
    void withdrawMoney (String accountNumber, double amount);
    void transferMoney (String senderAcct, String recieverAcct, double amount);
    
    //ATM unit
    void withdrawMoney (Customer customer, String pin, double amount);
    void transferMoney (Customer cu, String pin, String recieverAcct, double amount);
    void retrievePin(String email);
    void changePin(String oldPin, String newPin, String verifyPin);
    
    //other methods
   
   
    void sendEmail (Customer customer, String emailAd); //new account, delete account, update account and acctDetails
  
    
    List <Customer> getAllCustomers();
    
    
  
    double getBal();
    String getFirstN();
    String getMiddleN();
    String getLastN();
    String getTY();
    String getGender();
    String getNA();
    String getAD();
    String getEM();
    String getPH();
    Date getD();
    String getStringIM();
    String getMsg();
    double getNewBalance();
    String getStatusMsg();
    
    
    void searchByAcct(Customer customer, String acct);
    void searchByAcct(String pin);
    
    
}
