
package com.ejb.session;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author STEINACOZ-PC
 */
@Stateless
public class CustomerSessionBean implements CustomerRemote {

    public CustomerSessionBean() {
    }
    
    @PersistenceContext(unitName="Banker_Enterprise-ejbPU")
    private EntityManager em;
    Random rad = new Random();
    String firstN = null;
    String middleN = null;
    String lastN = null;
    String sex = null;
    String phone = null;
    String email = null;
    String addr = null;
    Date d = null;
   
    BufferedImage im = null;
    String s = null;
    String type = null;
    String ciizen = null;
    
   
   
       private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(150, 150, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, 150, 150, null);
        g.dispose();
        
        return resizedImage;
    }
 
       @Override
        public byte[] imageToByte(File f){
        byte[] b = null;
        byte[] initB = null;
        
        try{
        BufferedImage image = ImageIO.read(f);
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        
        BufferedImage resizeImagePng = resizeImage(image, type);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       
        ImageIO.write(resizeImagePng, "png", baos);
        baos.flush();
        initB = baos.toByteArray();
        
        String  initBS = javax.xml.bind.DatatypeConverter.printHexBinary(initB);
        b = javax.xml.bind.DatatypeConverter.parseHexBinary(initBS);
        }catch(IOException e){
            e.getMessage();
        }
        return b;
    }
    
   
    

    @Override
    public void CreateNewAcct(Customer customer, File fi, String gender, String emailAdr) {
        String ge = null;
        
        //account number
        if (gender.equals("male")){
            ge = "MA";
        }else if (gender.equals("female")){
            ge = "FE";
        }
        
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char letter1 = alpha.charAt(rad.nextInt(alpha.length()));
        char letter2 = alpha.charAt(rad.nextInt(alpha.length()));
        
        String a = String.valueOf(rad.nextInt(9));
        String b = String.valueOf(rad.nextInt(9));
        String c = String.valueOf(rad.nextInt(9));
        String d = String.valueOf(rad.nextInt(9));
        String e = String.valueOf(rad.nextInt(9));
        String f = String.valueOf(rad.nextInt(9));
        String y = String.valueOf(letter1);
        String z = String.valueOf(letter2);
         
        String generatedAcct = a + b + c + d + e + f + y + z + ge;
         
         //bvn
         String g = String.valueOf(rad.nextInt(9));
        String h = String.valueOf(rad.nextInt(9));
        String i = String.valueOf(rad.nextInt(9));
        String j = String.valueOf(rad.nextInt(9));
        String k = String.valueOf(rad.nextInt(9));
        String l = String.valueOf(rad.nextInt(9));
        String m = String.valueOf(rad.nextInt(9));
        String n = String.valueOf(rad.nextInt(9));
        String o = String.valueOf(rad.nextInt(9));
        String p = String.valueOf(rad.nextInt(9));
        
        String generatedBVN = g+h+i+j+k+l+m+n+o+p;
        
        //pin
        String q = String.valueOf(rad.nextInt(9));
        String r = String.valueOf(rad.nextInt(9));
        String s = String.valueOf(rad.nextInt(9));
        String t = String.valueOf(rad.nextInt(9));
        String u = String.valueOf(rad.nextInt(9));
     
   
        String generatedPIN = q+r+s+t+u;
        
        customer.setBvn(generatedBVN);
        customer.setEpin(generatedPIN);
        customer.setBalance(0);
        customer.setImage(imageToByte(fi));
     customer.setAcctnum(generatedAcct);
        em.persist(customer);
        em.flush();
      sendEmail(customer, emailAdr);
    }

    @Override
    public void deleteAcct(Customer customer, String acctNum) {
            Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", acctNum);
     customer = (Customer) q.getSingleResult();
     
      em.remove(customer);
    
     em.flush();
     
     
    }

    @Override
    public void searchByAcct(Customer cu,String acctNumber) {
     
     Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", acctNumber);
    
     cu = (Customer) q.getSingleResult();
 
      setFirstN(cu.getFirstname());
      setMiddleN(cu.getMiddlename());
      setLastN(cu.getLastname());
      setSex(cu.getSex());
      setDod(cu.getDod());
      setEmail(cu.getEmail());
      setAddr(cu.getAddress());
      setNationality(cu.getCitizen());
      setAcctType(cu.getType());
      setPhone(cu.getPhone());
      byteToImage(cu.getImage());
      
    }
    
    public void setFirstN(String fname){
        firstN = fname;
    }
    public void setMiddleN(String Mname){
        this.middleN = Mname;
    }
    public void setLastN(String lname){
        this.lastN = lname;
    }
    public void setSex(String gender){
        this.sex = gender;
    }
    public void setDod(Date dod){
        this.d = dod;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddr(String addr){
        this.addr = addr;
    }
    public void  setNationality(String nat){
        this.ciizen = nat;
    }
    public void setAcctType(String acctype){
        this.type = acctype;
    }
    public void  setPhone(String ph){
        this.phone = ph;
    }
   
    
    @Override
    public String getFirstN (){
        return firstN;
    }

    @Override
    public Customer acctDetails(Customer customer, String acctNumber) {
     return customer;
    }

    @Override
    public void depositMoney(String accountNumber, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void withdrawMoney(String accountNumber, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transferMoney(String senderAcct, String recieverAcct, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void withdrawMoney(Customer customer, String pin, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transferMoney(String pin, String senderAcct, String recieverAcct, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String generateBVN() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String generateAcctNumber(String sex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendEmail(Customer customer, String emailAd) {
        String to = emailAd;//"nkennannadi@gmail.com";
        
        //sender's email ID
        String from = "nnadiug@rocketmail.com";
        
        //login credentials
        final String username = "nnadiug@rocketmail.com";
        final String password = "adaeze";
        
        //sending through yahoo mail
        String host = "smtp.mail.yahoo.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        
        
        //get session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

protected PasswordAuthentication getPasswordAuthentication(){
    return new PasswordAuthentication(username, password);
}
        });
        
        try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress(from));
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));


message.setSubject("Confirmation of new Account opened with We-Eat Bank Ltd");

message.setText("\n" + "This is the details of the account you opened with We-Eat Bank: " 
                + "\n" + "Account Name: " + customer.getFirstname() +" "+ customer.getMiddlename() + " "+ customer.getLastname()
                + "\n" + "Account Number: " + customer.getAcctnum()
                + "\n" + "Bank Verification Number: " + customer.getBvn()
                + "\n" + "Account Balance: " + customer.getBalance()
                );
Transport.send(message);

} catch (MessagingException e) {
throw new RuntimeException(e);
}

    }
    
    

    @Override
    public void sendEmail(String accountNumber, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendEmail(String senderAcct, String recieverAcct, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Customer> getAllCustomers() {
       return em.createQuery("From customer").getResultList();
    }

    @Override
    public String getMiddleN() {
        return this.middleN;
    }

    @Override
    public String getLastN() {
        return this.lastN;
    }

    @Override
    public String getTY() {
        return this.type;
    }

    @Override
    public String getGender() {
       return this.sex;
    }

    @Override
    public String getNA() {
        return this.ciizen;
    }

    @Override
    public String getAD() {
        return this.addr;
    }

    @Override
    public String getEM() {
        return this.email;
    }

    @Override
    public String getPH() {
        return this.phone;
    }

    @Override
    public Date getD() {
        return this.d;
    }

    @Override
    public BufferedImage getIM() {
       return this.im;
    }

   

    @Override
    public void byteToImage(byte[] b) {
       BufferedImage bi = null;
            s = javax.xml.bind.DatatypeConverter.printHexBinary(b);
           
    }

    @Override
    public String getStringIM() {
       return s;
    }

    @Override
    public void updateAcct(Customer customer, String acctnum, String fName, String mName, String lName, 
            String acctType, String gender, String nationality, String address, String email, String phone, Date dod, File file) {
        
         Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", acctnum);
     
    
     
     customer = (Customer) q.getSingleResult();
     customer.setFirstname(fName);
     customer.setMiddlename(mName);
     customer.setLastname(lName);
     customer.setType(acctType);
     customer.setSex(gender);
     customer.setCitizen(nationality);
     customer.setAddress(address);
     customer.setEmail(email);
     customer.setPhone(phone);
     customer.setDod(dod);
        customer.setImage(imageToByte(file));
     
     
     
     em.merge(customer);
    
     em.flush();
      
    }
    
    
    
    
}
