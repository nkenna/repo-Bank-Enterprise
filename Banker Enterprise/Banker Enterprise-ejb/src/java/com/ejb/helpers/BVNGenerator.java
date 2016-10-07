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
public class BVNGenerator {
    
    public String generate(){
        Random rad = new Random();
        String a = String.valueOf(rad.nextInt(9));
        String b = String.valueOf(rad.nextInt(9));
        String c = String.valueOf(rad.nextInt(9));
        String d = String.valueOf(rad.nextInt(9));
        String e = String.valueOf(rad.nextInt(9));
        String f = String.valueOf(rad.nextInt(9));
        String g = String.valueOf(rad.nextInt(9));
        String h = String.valueOf(rad.nextInt(9));
        String i = String.valueOf(rad.nextInt(9));
        String j = String.valueOf(rad.nextInt(9));
        
        String generatedBVN = a+b+c+d+e+f+g+h+i+j;
        
        return generatedBVN;
    }
    
}
