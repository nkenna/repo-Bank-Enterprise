/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delete;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class DeleteAcctFxmlController implements Initializable {

    @FXML
    private Button deleteButton;
    @FXML
    private ImageView imgView;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Label deleteFirstName;
    @FXML
    private Label deleteMiddle;
    @FXML
    private Label deleteLast;
    @FXML
    private Label deleteSex;
    @FXML
    private Label deleteEmail;
    @FXML
    private Label deletePhone;
    @FXML
    private Label deleteCountry;
    @FXML
    private Label deleteType;
    @FXML
    private Label deleteDod;
    @FXML
    private TextArea deleteAddress;
    
     Properties props;
    InitialContext ctx = null;
     CustomerRemote customerBean;
     
    @FXML
    private Label alertLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void deleteAcctButton(ActionEvent event) {
        String user = usernameDialog();
        String pass = passwordDialog(); 
        
        try{
            props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8050");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
          props.put(Context.SECURITY_PRINCIPAL, user);
          props.put(Context.SECURITY_CREDENTIALS, pass);
            props.put("jboss.naming.client.ejb.context", true);
           
            ctx = new InitialContext(props);
            
             
 customerBean = (CustomerRemote) ctx.lookup("Banker_Enterprise-ejb/CustomerSessionBean!com.ejb.remoteInterface.CustomerRemote");
     
        }catch(NamingException ex){
            ex.printStackTrace();
          
        } 
        
        
Customer customer = new Customer();
      
customerBean.deleteAcct(customer, searchTextField.getText());
System.out.println(customer);
alertLabel.setText("Customer with Account Number " + searchTextField.getText() + " have been deleted");
    }

    @FXML
    private void searchAcctField(ActionEvent event) {
         
    }
    
    private String usernameDialog(){
      String username = null;
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Enter Username");
        d.setHeaderText("Enter your username");
        Optional<String> result = d.showAndWait();
        if (result.isPresent()){
            username = result.get();
        }
        return username;
    }
    
     private String passwordDialog(){
      String password = null;
        TextInputDialog d = new TextInputDialog();
        d.setTitle("Enter Username");
        d.setHeaderText("Enter your password");
        Optional<String> result = d.showAndWait();
        if (result.isPresent()){
            password = result.get();
        }
        return password;
    }

    @FXML
    private void searchAcctButton(ActionEvent event) {
        String user = usernameDialog();
        String pass = passwordDialog();
        
        try{
            props = new Properties();
             props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8050");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
          props.put(Context.SECURITY_PRINCIPAL, user);
          props.put(Context.SECURITY_CREDENTIALS, pass);
            props.put("jboss.naming.client.ejb.context", true);
           
            ctx = new InitialContext(props);
            
             
 customerBean = (CustomerRemote) ctx.lookup("Banker_Enterprise-ejb/CustomerSessionBean!com.ejb.remoteInterface.CustomerRemote");
     
        }catch(NamingException ex){
            ex.printStackTrace();
          
        }
      
        
        String getAccNum = searchTextField.getText();
        
        
        Customer customer = new Customer();
        
        customerBean.searchByAcct(customer, getAccNum);
     
        
        
         this.deleteFirstName.setText(customerBean.getFirstN());
         this.deleteMiddle.setText(customerBean.getMiddleN());
         this.deleteLast.setText(customerBean.getLastN());
         this.deleteAddress.setText(customerBean.getAD());
         this.deleteCountry.setText(customerBean.getNA());
         this.deleteDod.setText(customerBean.getD().toString());
         this.deleteEmail.setText(customerBean.getEM());
         this.deleteType.setText(customerBean.getTY());
         this.deletePhone.setText(customerBean.getPH());
         this.deleteSex.setText(customerBean.getGender());
            int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage bi = new BufferedImage(150,150, type);
         // BufferedImage resizedImage = new BufferedImage(150, 150, type);
        Graphics2D g = bi.createGraphics();
        g.drawImage(bi, 0, 0, 150, 150, null);
        g.dispose();
      
      try{
        
       bi = ImageIO.read(new File(customerBean.getStringIM()));
      }catch(IOException e){
           e.getMessage();
       }
         Image im = SwingFXUtils.toFXImage(bi, null);
        // imv.setImage(im);
        imgView.setImage(im);
    }
    
}
