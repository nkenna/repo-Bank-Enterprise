/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emailSend;

/**
 *
 * @author STEINACOZ-PC
 */
public interface EmailSenderInterface {
    
  void setIds (String recieverId, String senderId);
  void setLoginCredentials (String username, String password);
  void setHost (String host);
  void setProps (String auth, String startls, String smtpHost, String smtpPort, String smtpPortNumber);
  void useDefaultProps (); //default properties are for yahoo
  void setSubject (String subject);
  void setMessage (String message);
    
}
