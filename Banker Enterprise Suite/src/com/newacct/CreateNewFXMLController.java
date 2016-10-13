/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newacct;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class CreateNewFXMLController implements Initializable {

    @FXML
    private TextField firstname;
    @FXML
    private TextField middlename;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private TextField country;
    @FXML
    private TextField image;
    @FXML
    private DatePicker dod;
    @FXML
    private CheckBox malecheck;
    @FXML
    private CheckBox femalecheck;
    @FXML
    private TextArea address;
    @FXML
    private CheckBox savingcheck;
    @FXML
    private CheckBox currentcheck;
    @FXML
    private CheckBox fixeddepositcheck;
    @FXML
    private CheckBox termscheck;
    @FXML
    private Button saveBtn;
    @FXML
    private Button browsebtn;
    
    private String gender = null;
    private String AcctType = null;
    private File file;
    private String fname;
    private String path;
    
     Properties props;
    InitialContext ctx = null;
     CustomerRemote customerBean;
    @FXML
    private TextField phone;
    @FXML
    private Label statusLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void malecheckAct(ActionEvent event) {
        this.femalecheck.setSelected(false);
    }

    @FXML
    private void femalecheckAct(ActionEvent event) {
        this.malecheck.setSelected(false);
    }

    @FXML
    private void savingcheckAct(ActionEvent event) {
        this.currentcheck.setSelected(false);
        this.fixeddepositcheck.setSelected(false);
    }

    @FXML
    private void currentcheckAct(ActionEvent event) {
         this.savingcheck.setSelected(false);
        this.fixeddepositcheck.setSelected(false);
    }

    @FXML
    private void fixeddepositCheckAct(ActionEvent event) {
         this.currentcheck.setSelected(false);
        this.savingcheck.setSelected(false);
    }

    @FXML
    private void termscheckAct(ActionEvent event) {
    }

    @FXML
    private void saveBtnAct(ActionEvent event) {
           try{
            props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:8050");
            props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            props.put(Context.SECURITY_PRINCIPAL, "steinacoz");
            props.put(Context.SECURITY_CREDENTIALS, "nkenna007");
            props.put("jboss.naming.client.ejb.context", true);
           
            ctx = new InitialContext(props);
            
             
 customerBean = (CustomerRemote) ctx.lookup("Banker_Enterprise-ejb/CustomerSessionBean!com.ejb.remoteInterface.CustomerRemote");
     
        }catch(NamingException ex){
            ex.printStackTrace();
          
        }
           
           if (email.getText().isEmpty()){
               this.statusLabel.setText("Email field is required");
               this.statusLabel.setStyle("-fx-text-fill: #ff0000");
           }else if(!termscheck.isSelected()){
             this.statusLabel.setText("Accept terms and Conditions");
             this.statusLabel.setStyle("-fx-text-fill: #ff0000");
           }else{
           
            Customer customer = new Customer();
        LocalDate ld = this.dod.getValue();
       String dd= ld.toString();
               Date d ;//= new Date(Date.valueOf(ld));
    d = Date.valueOf(dd);
    
      String firstName = this.firstname.getText();
                String middleName = this.middlename.getText();
                String lastName = this.lastname.getText();
                String citi = this.country.getText();
                String emailAdd = this.email.getText();
                String fone = this.phone.getText();
                String addr = this.address.getText();
                
        
        //controls the gender radio buttons
        if (this.malecheck.isSelected()){
            gender = "male";
            
        }else if(this.femalecheck.isSelected()){
            gender = "female";
        }
               
        //controls account type radio buttons
        if (this.savingcheck.isSelected()){
            AcctType = "saving";
            
        }else if(this.currentcheck.isSelected()){
            AcctType = "current";
        }else if(this.fixeddepositcheck.isSelected()){
            AcctType = "fixed deposit";
        }
                  
        //sets 
              
                    customer.setFirstname(firstName);
                    customer.setMiddlename(middleName);
                    customer.setLastname(lastName);
                    customer.setDod(d);
                    customer.setSex(gender);
                    customer.setCitizen(citi);
                    customer.setEmail(emailAdd);
                    customer.setPhone(fone);
                    customer.setAddress(addr);
                    customer.setType(AcctType);
                    
                    
                    customerBean.CreateNewAcct(customer, getFile(), gender, emailAdd);
                  
                    this.statusLabel.setText(customerBean.getStatusMsg());
           }    
    }

                  
                 
           
    

    @FXML
    private void browseBtnAct(ActionEvent event) {
        //gets an Image file
        JFileChooser jfc = new JFileChooser();
        jfc.createImage(250, 250);
        jfc.setCurrentDirectory(new java.io.File("."));
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setAcceptAllFileFilterUsed(false);
        
        if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File f = jfc.getSelectedFile();
            fname = f.getName();
            path = f.getAbsolutePath();
           this.image.setText(path);
            setFile(path);
            
        }
    }
    
     private void setFile (String p){
        this.path = p;
    } 
    
    private String getFile (){
       
        return path;
    }
    
}
