/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emailSend;


import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

/**
 *
 * @author STEINACOZ-PC
 */
public class EmailSender implements EmailSenderInterface{
    
        private String username;
        private String password;
        private String host;
        private String to, from;
        private String sub, msg;

   
    
     String getUsername(){
        return username;
    }
    
     String getPassword(){
        return password;
    }
     
       
    String getHost(){
        return host;
    }
    
     String getSubject(){
        return sub;
    }
     
     String getMessage(){
        return msg;
    }
        
    @Override
    public void setIds(String recieverId, String senderId) {
         to = recieverId;
        
        //sender's email ID
        from = senderId;
    }
    
    
     String getSenderId(){
        return from;
    }
    
    String getRecieverId(){
        return to;
    } 
    
   

    @Override
    public void setLoginCredentials(String user, String pass) {
        //login credentials
        username = user;
        password = pass;
    }
    
   

    @Override
    public void setHost(String ihost) {
            //sending through yahoo mail
        host = ihost;
    }
  

    @Override
    public void setProps(String auth, String startls, String smtpHost, String smtpPort, String smtpPortNumber) {
       Properties props = new Properties();
        props.put(auth, "true");
        props.put(startls, "true");
        props.put(smtpHost, getHost());
        props.put(smtpPort, smtpPortNumber);
        
        //get session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

protected PasswordAuthentication getPasswordAuthentication(){
    return new PasswordAuthentication(getUsername(), getPassword());
}
        });
        
         try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(this.getSenderId()));
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getRecieverId()));


message.setSubject(getSubject());

message.setText(getMessage());
Transport.send(message);

} catch (MessagingException e) {
throw new RuntimeException(e);
}
        
    }
    
    
//using this method will send the email through yahoo
    @Override
    public void useDefaultProps() {
         
//sending through yahoo mail
        String hostDefault = "smtp.mail.yahoo.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", hostDefault);
        props.put("mail.smtp.port", "587");
        
           
        //get session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

protected PasswordAuthentication getPasswordAuthentication(){
    return new PasswordAuthentication(username, password);
}
        });
        
         try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(this.getSenderId()));
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getRecieverId()));


message.setSubject(getSubject());

message.setText(getMessage());
Transport.send(message);

} catch (MessagingException e) {
throw new RuntimeException(e);
}
        
    }

    @Override
    public void setSubject(String subject) {
      sub = subject;
    }
    
   

    @Override
    public void setMessage(String message) {
        msg = message;
    }
    
    
    
}
