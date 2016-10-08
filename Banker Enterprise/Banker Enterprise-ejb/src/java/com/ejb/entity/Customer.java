/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author STEINACOZ-PC
 */
@Entity
@Table(name = "data")
@NamedQueries({@NamedQuery(query = "Select c from Customer c where c.acctnum = :acctnum", name = "find customer by acctnum"),
})

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String firstname;
  
    private String middlename;
   
    private String lastname;
   
   
    private String sex;
   
    private String citizen;
   // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
   
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
 
    private String phone;

    private String address;
 
    private String type;
    
    private String bvn;
   
    private String acctnum;
    
   
    private String epin;
    
  
    private double balance;
    
    private Date dod;
    
    private byte[] image;
    
    

    public Customer() {
    }

   public Customer(int id, String firstname) {
       this.id = id;
       this.firstname = firstname;      
   }

@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
@Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizen() {
        return citizen;
    }

    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getAcctnum() {
        return acctnum;
    }

    public void setAcctnum(String acctnum) {
        this.acctnum = acctnum;
    }

    public String getEpin() {
        return epin;
    }

    public void setEpin(String epin) {
        this.epin = epin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
     public Date getDod() {
        return dod;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }
    
      public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
   

   

   

 /**   @Override
    public String toString() {
        return "com.ejb.entity.Customer[ id=" + id + " ]";
    }**/
    
}
