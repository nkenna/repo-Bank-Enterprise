
package com.ejb.session;

import com.ejb.entity.Customer;
import com.ejb.remoteInterface.CustomerRemote;
import com.emailSend.EmailSender;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
@DeclareRoles({"tellerUnit","customerCareUnit","atmUnit"})
//@DenyAll
public class CustomerSessionBean implements CustomerRemote {

    public CustomerSessionBean() {
    }
    
    @PersistenceContext(unitName="Banker_Enterprise-ejbPU")
    private EntityManager em;
    Random rad = new Random();
    double balance;
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
    
    String Msg = null;
    String statusMsg=null;
    EmailSender es = new EmailSender();
   
   String myPicture;
   
   
  
    
    
    /**
       public BufferedImage resizeImage(BufferedImage originalImage, int type){
       
           BufferedImage resizedImage = new BufferedImage(150, 150, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, 150, 150, null);
        g.dispose();
        
        return resizedImage;
    }**/
 
/**       @Override
        public String imageToString(String f){
        byte[] b = null;
        byte[] initB = null;
        
        try{
        BufferedImage image = ImageIO.read(f);
        int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
        
        BufferedImage resizeImagePng = resizeImage(image, type);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       
        ImageIO.write(resizeImagePng, "png", baos);
        baos.flush();
       
        }catch(IOException e){
            e.getMessage();
        }
        return b;
    }
    **/
    
   
    

    @Override
    @RolesAllowed("customerCareUnit")
    public void CreateNewAcct(Customer customer, String fi, String gender, String emailAdr) {
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
        customer.setBalance(0.0);
        customer.setImage(fi);
     customer.setAcctnum(generatedAcct);
        em.persist(customer);
        em.flush();
        statusMsg = "Account Creation Successful...";
        setStatusMsg(statusMsg);
        Thread newAcctEmailThread = new Thread(new Runnable(){
            @Override
            public void run() {
                  sendEmail(customer, emailAdr);
            }
            
        }, "new Account Email Thread");
        newAcctEmailThread.start();
    
    }

    @Override
    @RolesAllowed("customerCareUnit")
    public void deleteAcct(Customer customer, String acctNum) {
        
    Query q = em.createNamedQuery("find customer by acctnum");
    q.setParameter("acctnum", acctNum);
    Customer cu = (Customer) q.getSingleResult();
    
    em.remove(cu);
    
    em.flush();
     
    }

    @Override
    @RolesAllowed({"atmUnit","tellerUnit","customerCareUnit"})
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
      setBalance(cu.getBalance());
      setStringIM(cu.getImage());
    }
    
    public void setStringIM (String im){
        this.myPicture = im;
    }
    public void setBalance(double bal){
        balance = bal;
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
    @PermitAll
    public double getBal (){
        return balance;
    }

    
    @Override
    @PermitAll
    public String getFirstN (){
        return firstN;
    }

    @Override
    @PermitAll
    public Customer acctDetails(Customer customer, String acctNumber) {
     return customer;
    }

    @Override
    @RolesAllowed("tellerUnit")
    public void depositMoney(String accountNumber, double amount) {
     int status = 0;
     
     Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", accountNumber);
     
    
     
    Customer customer = (Customer) q.getSingleResult();
    double initBalance = customer.getBalance();
    double  newBalance = initBalance + amount;
    
    customer.setBalance(newBalance);
     
    em.merge(customer);
    
    status = 1;
    setMessage(status);
    setNewBalance(customer.getBalance());
     em.flush(); 
    }
    
    public void setNewBalance(double balanc){
        balance = balanc;
    }
    
    @Override
    @PermitAll
    public double getNewBalance(){
        return balance;
    }
    
    public void setMessage(int status){
        if (status == 1){
            Msg = "success";
        }else{
            Msg = "failure";
        }
    }
    
    @Override
    @PermitAll
    public String getMsg(){
        return Msg;
    }

    @Override
    @RolesAllowed("tellerUnit")
    public void withdrawMoney(String accountNumber, double amount) {
       int status;
     
     Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", accountNumber);
     
    
     
    Customer customer = (Customer) q.getSingleResult();
    double initBalance = customer.getBalance();
    String emailAddress = customer.getEmail();
    String accountName = customer.getFirstname() +" "+ customer.getMiddlename() +" "+ customer.getLastname();
   // String acctNumber = customer.getAcctnum();
    
      if (initBalance >= amount){
    double  newBalance = initBalance - amount;
  Date d = new Date();
   
    customer.setBalance(newBalance);
     
    em.merge(customer);
    
    status = 1;
    setMessage(status);
    setNewBalance(customer.getBalance());
     em.flush(); 
   
     Thread t = new Thread (new Runnable(){
        @Override
        public void run() {
             es.setIds(emailAddress, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("Debit Alert From We-Eat Bank Ltd");
         es.setMessage("debit Notification which occurred on: " + d
                         + "\n" + "Account Number: " + accountName
                          + "\n" + "Debited Amount: N" + amount
                         + "\n" + "Account Balance: N" + newBalance
                           + "\n" + "Account Number: " + accountNumber);
         es.useDefaultProps();
        }
         
     }, "Withdraw Money Email Send");
     t.start();
        
    }else if (initBalance < amount){
          status = 0;
          setMessage(status);
        
    }
    }

    @Override
    @RolesAllowed("tellerUnit")
    public void transferMoney(String senderAcct, String recieverAcct, double transferAmount) {
     int status;
     
     Query q = em.createNamedQuery("find customer by acctnum");
     q.setParameter("acctnum", senderAcct);
     
    
     
    Customer senderCustomer = (Customer) q.getSingleResult();
    double senderInitBalance = senderCustomer.getBalance();
    String senderEmailAddress = senderCustomer.getEmail();
    String senderAccountName = senderCustomer.getFirstname() +" "+ senderCustomer.getMiddlename() +" "+ senderCustomer.getLastname();
   // String acctNumber = customer.getAcctnum();
   
    Query q1 = em.createNamedQuery("find customer by acctnum");
     q1.setParameter("acctnum", recieverAcct);
     
     Customer recieverCustomer = (Customer) q1.getSingleResult();
    double recieverInitBalance = recieverCustomer.getBalance();
    String recieverEmailAddress = recieverCustomer.getEmail();
    String receiverAccountName = recieverCustomer.getFirstname() +" "+ recieverCustomer.getMiddlename() +" "+ recieverCustomer.getLastname();
    
      if (senderInitBalance >= transferAmount){
    double  senderNewBalance = senderInitBalance - transferAmount;
  Date d = new Date();
  senderCustomer.setBalance(senderNewBalance);
  em.merge(senderCustomer);
  status = 1;
  setMessage(status);
  setNewBalance(senderCustomer.getBalance());
  em.flush(); 
  
  double recieverNewBalance = recieverInitBalance + transferAmount;
  recieverCustomer.setBalance(recieverNewBalance);
  em.merge(recieverCustomer);
  em.flush();
   
  Thread transferThread = new Thread (new Runnable() {
        @Override
        public void run() {
        //sending notification to sender
         es.setIds(senderEmailAddress, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("debit Alert From We-Eat Bank Ltd");
         es.setMessage("debit Notification which occurred on: " + d
                         + "\n" + "Account Name: " + senderAccountName
                          + "\n" + "Debited Amount: N" + transferAmount
                         + "\n" + "Account Balance: N" + senderNewBalance
                           + "\n" + "Account Number: " + senderCustomer.getAcctnum()
                             + "\n" + "Transfered to Account Name: " + receiverAccountName
                         + "\n" + "with Account Number: " + recieverCustomer.getAcctnum());
         es.useDefaultProps();
         
       sendRecieverEmail(recieverEmailAddress, receiverAccountName, transferAmount, recieverNewBalance, recieverCustomer.getAcctnum());    
        }
      
  }, "transfer Email send");
  transferThread.start();
  
    }else if (senderInitBalance < transferAmount){
          status = 0;
          setMessage(status);
        
    }
    }
    
    //this method sends email alert to the reciever
    
    public void sendRecieverEmail(String emailID, String receiverAccountName, Double transferAmount, Double recieverNewBalance, String recieverAccountNumber){
     //sender notification to reciever
         es.setIds(emailID, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("Credit Alert From We-Eat Bank Ltd");
         es.setMessage("Credit Notification which occurred on: " + d
                         + "\n" + "Account Name: " + receiverAccountName
                          + "\n" + "Credited Amount: N" + transferAmount
                         + "\n" + "Account Balance: N" + recieverNewBalance
                           + "\n" + "Account Number: " + recieverAccountNumber);
         es.useDefaultProps();     
    }

    @Override
    @RolesAllowed("atmUnit")
    public void withdrawMoney(Customer customer, String pin, double amount) {
       
     Query q = em.createNamedQuery("find customer by epin");
     q.setParameter("epin", pin);
    
     Customer cu = (Customer) q.getSingleResult();
 
    
    String name = cu.getFirstname() + " " + cu.getMiddlename() +" " + cu.getLastname();
    String emaili = cu.getEmail();
    Double oldBalance = cu.getBalance();
    
    if (oldBalance <= amount){
        statusMsg = "Invalid Amount, try Again";
    }else{
    Double newBalance = oldBalance - amount;
    setBalance(oldBalance);
    
    em.merge(cu);
    em.flush();
    setNewBalance(cu.getBalance());
       statusMsg = "Transcation Successful...";
    setStatusMsg(statusMsg);
    Date dd = new Date();
    
    Thread EmailSenderWithdrawAtm = new Thread(new Runnable(){
        @Override
        public void run() {
          //send email
         es.setIds(emaili, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject(" ATM Withdrawal debit Alert From We-Eat Bank Ltd");
         es.setMessage("debit Notification which occurred on: " + dd
                         + "\n" + "Account Name: " + name
                          + "\n" + "Debited Amount: N" + amount
                         + "\n" + "Account Balance: N" + newBalance);
         es.useDefaultProps(); 
        }
        
    }, "Thread for sending Email");
    
    EmailSenderWithdrawAtm.start();
   
    
    
    }
    }
    
    public void setStatusMsg (String mssg){
     statusMsg = mssg;   
    }
    
    @Override
    @PermitAll
    public String getStatusMsg(){
        return statusMsg;
    }

    @Override
    @RolesAllowed("atmUnit")
    public void transferMoney(Customer cus,String pin, String recieverAcct, double amount) {
   
        
     Query q = em.createNamedQuery("find customer by epin");
     q.setParameter("epin", pin);
     Customer senderCustomer = (Customer) q.getSingleResult();
     double senderInitBalance = senderCustomer.getBalance();
     String senderEmailAddress = senderCustomer.getEmail();
     String senderAccountName = senderCustomer.getFirstname() +" "+ senderCustomer.getMiddlename() +" "+ senderCustomer.getLastname();
     String acctNumber = senderCustomer.getAcctnum();
    
    Query q1 = em.createNamedQuery("find customer by acctnum");
    q1.setParameter("acctnum", recieverAcct); 
    Customer recieverCustomer = (Customer) q1.getSingleResult();
    double recieverInitBalance = recieverCustomer.getBalance();
    String recieverEmailAddress = recieverCustomer.getEmail();
    String receiverAccountName = recieverCustomer.getFirstname() +" "+ recieverCustomer.getMiddlename() +" "+ recieverCustomer.getLastname();
    
    
if (senderInitBalance >= amount){

  //deduct amount from sender's balance and merge  
  double  senderNewBalance = senderInitBalance - amount;
  Date d = new Date();
  senderCustomer.setBalance(senderNewBalance);
  em.merge(senderCustomer);
  statusMsg = "Transfer Successfully...";
  
  setNewBalance(senderCustomer.getBalance());
  em.flush(); 
  
  //add amount to reciever's balnce and merge
  double recieverNewBalance = recieverInitBalance + amount;
  recieverCustomer.setBalance(recieverNewBalance);
  em.merge(recieverCustomer);
  em.flush();
 setStatusMsg(statusMsg);
  
//sending notification to sender

Thread transferEmailThread = new Thread(new Runnable() {
      @Override
      public void run() {
         es.setIds(senderEmailAddress, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("debit Alert From We-Eat Bank Ltd");
         es.setMessage("debit Notification which occurred on: " + d
                         + "\n" + "Account Name: " + senderAccountName
                          + "\n" + "Debited Amount: N" + amount
                         + "\n" + "Account Balance: N" + senderNewBalance
                           + "\n" + "Account Number: " + senderCustomer.getAcctnum()
                             + "\n" + "Transfered to Account Name: " + receiverAccountName
                         + "\n" + "with Account Number: " + recieverCustomer.getAcctnum());
         es.useDefaultProps();
         
       sendRecieverEmail(recieverEmailAddress, receiverAccountName, amount, recieverNewBalance, recieverCustomer.getAcctnum());
      }
    
}, "Transfer Email Thread");
transferEmailThread.start();
     
    }else if (senderInitBalance < amount){
         statusMsg = "Insufficent balance, try Again"; 
         setStatusMsg(statusMsg);
        
        
    }
    }

    

  

    @Override
    @RolesAllowed("customerCareUnit")
    public void sendEmail(Customer customer, String emailAd) {
         
        
        String to = emailAd;//"nkennannadi@gmail.com";
        
        //sender's email ID
        String from = "nnadiug@rocketmail.com";
        
        //login credentials
        final String username = "nnadiug@rocketmail.com";
        final String password = "123456789";
        
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
    @PermitAll
    public List<Customer> getAllCustomers() {
       return em.createQuery("From Customer").getResultList();
    }

    @Override
    @PermitAll
    public String getMiddleN() {
        return this.middleN;
    }

    @Override
    @PermitAll
    public String getLastN() {
        return this.lastN;
    }

    @Override
    @PermitAll
    public String getTY() {
        return this.type;
    }

    @Override
    @PermitAll
    public String getGender() {
       return this.sex;
    }

    @Override
    @PermitAll
    public String getNA() {
        return this.ciizen;
    }

    @Override
    @PermitAll
    public String getAD() {
        return this.addr;
    }

    @Override
    @PermitAll
    public String getEM() {
        return this.email;
    }

    @Override
    @PermitAll
    public String getPH() {
        return this.phone;
    }

    @Override
    @PermitAll
    public Date getD() {
        return this.d;
    }

  


    @Override
    @PermitAll
    public String getStringIM() {
       return myPicture;
    }

    @Override
    @RolesAllowed("customerCareUnit")
    public void updateAcct(Customer customer, String acctnum, String fName, String mName, String lName, 
            String acctType, String gender, String nationality, String address, String email, String phone, Date dod, String filename) {
        
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
      customer.setImage(filename);
     
     
     
     em.merge(customer);
    
     em.flush();
     statusMsg = "Update is Successful";
      this.setStatusMsg(statusMsg);
    }

    @Override
    @RolesAllowed("atmUnit")
    public void searchByAcct(String pin) {
        Query q = em.createNamedQuery("find customer by epin");
     q.setParameter("epin", pin);
    
     Customer cu = (Customer) q.getSingleResult();
     setBalance(cu.getBalance());
    }

    @Override
    @RolesAllowed("atmUnit")
    public void retrievePin(String email) {
        
     Query q = em.createNamedQuery("find customer by email");
     q.setParameter("email", email);
    
     Customer cu = (Customer) q.getSingleResult();
     String firstname = cu.getFirstname();
     String pin = cu.getEpin();
     statusMsg = "Check Email for your pin";
     setStatusMsg(statusMsg);
     
     Thread emailRetrievePin = new Thread (new Runnable() {
             @Override
             public void run() {
                   es.setIds(email, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("We-Eat Bank - Pin retrieve Request");
         es.setMessage(firstname + " You requested for email retrivable on " + d + " . Below is your pin details: " +
                  "\n" + " Please endeavour to change this pin for security reasons."  +     
                 "\n" + "Pin: " + pin);
         es.useDefaultProps();
             }
         
     }, " Email Retrieve Pin");
     emailRetrievePin.start();
       
    }

    @Override
    @RolesAllowed("atmUnit")
    public void changePin(String oldPin, String newPin, String verifyPin) {
     Query q = em.createNamedQuery("find customer by epin");
     q.setParameter("epin", oldPin);
    
     Customer cu = (Customer) q.getSingleResult();
 
    
    String name = cu.getFirstname();
    String emaili = cu.getEmail();
    String pin = cu.getEpin();
    if (pin.equalsIgnoreCase(newPin)){
        statusMsg = "Pin not avaliable for security reasons";
        setStatusMsg(statusMsg);
    }else{
    if (newPin.equals(verifyPin)){
     //   Customer customer = new Customer();
        cu.setEpin(newPin);
        em.merge(cu);
        statusMsg = "Pin Change is Successful";
        setStatusMsg(statusMsg);
        em.flush();
        
        Thread emailChangePin = new Thread (new Runnable() {
             @Override
             public void run() {
                 Date ds = new Date();
                   es.setIds(emaili, "nnadiug@rocketmail.com");
         es.setLoginCredentials("nnadiug@rocketmail.com", "123456789");
         es.setSubject("We-Eat Bank - Pin retrieve Request");
         es.setMessage(name + " You requested for email change on " + ds + " . Below is your pin details: " +
                  "\n" + " Please endeavour to change this pin for security reasons."  +     
                 "\n" + "Pin: " + cu.getEpin());
         es.useDefaultProps();
             }
         
     }, " Email Retrieve Pin");
     emailChangePin.start();
    }else{
        statusMsg = "Pins are not matching";
        setStatusMsg(statusMsg);
    }
    
    }
    }

   
   
    
    
}
