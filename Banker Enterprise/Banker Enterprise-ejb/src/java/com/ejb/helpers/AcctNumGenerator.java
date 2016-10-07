/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.helpers;

import java.util.Random;

/**
 *
 * @author STEINACOZ-PC
 */
public class AcctNumGenerator {
    

       Random rad = new Random();
       String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       
       
     public String generate(String ma){
        
        char letter = alpha.charAt(rad.nextInt(alpha.length()));
    //    String lastCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String i = null;
     
        String a = String.valueOf(rad.nextInt(9));
        String b = String.valueOf(rad.nextInt(9));
        String c = String.valueOf(rad.nextInt(9));
        String d = String.valueOf(rad.nextInt(9));
        String e = String.valueOf(rad.nextInt(9));
        String f = String.valueOf(rad.nextInt(9));
        String g = String.valueOf(letter);
        String h = String.valueOf(letter);
       
        
        if(ma.equals("male")){
            i = "MA";
        }else if (ma.equals("female")){
            i = "FE";
        }
        
        
        String generatedBVN = a+b+c+d+e+f+g+h+i;
        
        return generatedBVN;
    }
    
    
}

    

