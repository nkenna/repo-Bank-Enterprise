/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author STEINACOZ-PC
 */
public class ImageHelpers {
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(150, 150, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, 150, 150, null);
        g.dispose();
        
        return resizedImage;
    }
    
    public byte[] imageToByte(String path){
        byte[] b = null;
        try{
        BufferedImage image = ImageIO.read(new File(path));
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        
        BufferedImage resizeImagePng = resizeImage(image, type);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        ImageIO.write(resizeImagePng, "png", baos);
        baos.flush();
        b = baos.toByteArray();
        }catch(IOException e){
            e.getMessage();
        }
        return b;
    }
    
    
    public Image byteToImage(byte[] b){
        BufferedImage bImage = null;
        try{
            InputStream in = new ByteArrayInputStream(b);
            
            bImage = ImageIO.read(in);
        }catch(IOException e){
            e.getMessage();
        }
        
        Image im = SwingFXUtils.toFXImage(bImage, null);
        return im;
    }
    
}
