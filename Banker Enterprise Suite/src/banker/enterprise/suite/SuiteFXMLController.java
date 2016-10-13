/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.enterprise.suite;

import com.ejb.remoteInterface.CustomerRemote;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * FXML Controller class
 *
 * @author STEINACOZ-PC
 */
public class SuiteFXMLController implements Initializable {

    @FXML
    private Button customerCare;
    @FXML
    private Button teller;
    @FXML
    private ImageView imgView;
    @FXML
    private Label numLabel;
    
     private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try{
            File f = new File("img\\car1.jpg");
            String file = f.toURI().toURL().toString();
     
        Image im = new Image(file, true);
        imgView.setImage(im);
        
        }catch(Exception e){
            
        }
        
        //create a new Thread to load the number of customers in the bank,
   //this is to encourage fluidity and responsiveness
        Thread numberThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
        
        List allCustomers = customerBean.getAllCustomers();
        numLabel.setText(String.valueOf(allCustomers.size()));
       // setnumlabel (num);
            }
        }, "number thread");
        numberThread.run();
    }    

    @FXML
    private void ccAction(ActionEvent event) throws IOException{
        Parent root1 = FXMLLoader.load(getClass().getResource("/com/cc/customerCareFXML.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }

    @FXML
    private void tellerAction(ActionEvent event) throws IOException {
          Parent root1 = FXMLLoader.load(getClass().getResource("/com/tel/tellerFXML.fxml"));
      
        Scene scene1 = new Scene(root1);
        Stage stage1 = new Stage();
      //  scene1.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        stage1.setScene(scene1);
        stage1.show();
    }
    
}
