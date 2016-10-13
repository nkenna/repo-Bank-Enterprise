/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banker.enterprise.client.atm;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author STEINACOZ-PC
 */
public class DashboardFXMLController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button withdrawButton;
    @FXML
    private ImageView img;
    
        private  Properties props;
 private   InitialContext ctx = null;
  private   CustomerRemote customerBean;
    @FXML
    private Label numLabel;
    @FXML
    private Button pinButton;
    @FXML
    private Button transferButton;
    
    BankerEnterpriseClientATM beca = new BankerEnterpriseClientATM();
    
    private void handleButtonAction(ActionEvent event) {
       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
            File f = new File("images\\car11.jpg");
            String file = f.toURI().toURL().toString();
     
        Image im = new Image(file, true);
        img.setImage(im);
        
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
    private void mouseEntered(MouseDragEvent event) {
        
       
    }

    @FXML
    private void withdrawBtnAct(ActionEvent event) throws IOException{
    //navigates to the Withdraw UI
      
    
    Parent root = beca.getPa();
    root = FXMLLoader.load(getClass().getResource("/com/withdraw/withdrawFXML.fxml"));
      Scene s = beca.getSc();
       s  = new Scene(root);
       
       Stage st = beca.getSt();
       st = new Stage();
        s.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        st.setScene(s);
        st.show();
    
    }

    @FXML
    private void pinBtnAct(ActionEvent event)throws IOException {
        
        //navigates to Change Pin UI
       Parent root = beca.getPa();
    root = FXMLLoader.load(getClass().getResource("/com/changepin/changePinFXML.fxml"));
      Scene s = beca.getSc();
       s  = new Scene(root);
       
       Stage st = beca.getSt();
       st = new Stage();
        s.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        st.setScene(s);
        st.show();
    }

    @FXML
    private void transferBtnAct(ActionEvent event) throws IOException{
            //navigates to transfer UI
       Parent root = beca.getPa();
    root = FXMLLoader.load(getClass().getResource("/com/transfer/transferFXML.fxml"));
      Scene s = beca.getSc();
       s  = new Scene(root);
       
       Stage st = beca.getSt();
       st = new Stage();
        s.getStylesheets().add(this.getClass().getResource("cool_skin.css").toExternalForm());
        st.setScene(s);
        st.show();
    }
    
    /**class NewThread implements Runnable{
        
        String num;

        @Override
        public void run() {
             
        }
        
        void setnumlabel (String n){
            num = n;
        }
        
        String getnumlabel(){
            return num;
        }
    }**/
    
 
    
    

    
}
