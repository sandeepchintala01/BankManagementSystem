package org.jsp.bank;

import java.util.Scanner;

import org.jsp.bank.DAO.BankCustomerDesk;
import org.jsp.bank.DAO.BankDAO;
import org.jsp.bank.model.Bank;

public class App 
{
    public static void main( String[] args )
    {
       BankDAO bankDAO = BankCustomerDesk.customerHelpDesk();
     // bankDAO.userRegistrtion();
       System.out.println("Enter 1.Registration \n      2.Credit \n      3.Debit \n      4.Change Password \n      5.Mobile-Mobile Transaction  \n      6.Check Balance \n      7.Exit");
       Scanner sc = new Scanner(System.in);
       int response = sc.nextInt();
       
       switch(response) {
       
       case 1:  //Registration
    	   System.out.println("Enter your first name");
    	   String fname = sc.next();
    	   System.out.println("Enter your last name");
    	   String lname = sc.next();
    	   System.out.println("Enter your mobile number");
    	   String phno = sc.next(); 
    	   System.out.println("Enter your email-id");
    	   String emailid = sc.next();
    	   System.out.println("Enter your password");
    	   String password = sc.next();
    	   System.out.println("Enter your Address");
    	   String address = sc.next();
    	   System.out.println("Enter your Amount");
    	   Double amount = sc.nextDouble();
    	   System.out.println("Enter your Account Number");
    	   String accno = sc.next();
    	   //Firstly we already stored the values in the back class object
    	   //so we have chosen the constructor with argument
    	   //because it is mandatory to give all the details of the user
           Bank bank = new Bank(6,fname,lname,phno,emailid,password,address,amount,accno);
    	   bankDAO.userRegistrtion(bank);
    	   break;
    	 
       case 2:  //Credit Amount
    	   System.out.println("Enter the Account Number");
   	       boolean validation2 = true;
    	   while(validation2) { 
    		   String accountNumber = sc.next();
    		   if(accountNumber.length()==11) {
    			   validation2 = false;
    			   System.out.println("Enter your password");
    			   boolean passwordvalidation = true;
    			   while(passwordvalidation) {
    				   String password2 = sc.next();
    				   if(password2.length()>1) {
    					  // System.out.println("OK");
    					   bankDAO.credit(accountNumber,password2);
    					   passwordvalidation=false;
    				   }
    				   else {
    					   System.out.println("invalid password");  					   
    				   }
    			   }
    		   }
    		   else {  
    			   System.out.println("Invalid Account Number");
    			   System.out.println("Create an Account by switching-1 \n Enter existing Account Number");
    		   }
    	   }
    	   break;
    	   
	   
       case 3:  //Debit amount
    	   System.out.println("Enter your Account Number");
    	   boolean validation = true;
    	   while(validation) {
    		   String accountNumber = sc.next();
    		   if(accountNumber.length()==11) {
    			   validation = false;
    			   System.out.println("Enter your password");
    			   boolean passwordvalidation = true;
    			   while(passwordvalidation) {
    				   String password1 = sc.next();
    				   if(password1.length()>1) {
    					  // System.out.println("OK");
    					   bankDAO.debit(accountNumber, password1);
    					   passwordvalidation=false;
    				   }
    				   else {
    					   System.out.println("invalid password");  					   
    				   }
    			   }
    		   }
    		   else {  
    			   System.out.println("Invalid Account Number");
    			   System.out.println("Create an Account by switching-1 \n Enter existing Account Number");
    		   }
    	   }
    	   break;
    	   
       case 4:    //Changing password
    	          System.out.println("Enter your Account Number");
    	          boolean accountvalid = true;
    	          while(accountvalid) {
    	        	  String account = sc.next();
    	        	  if(account.length()==11) {
    	        		  System.out.println("Enter your old password");
    	        		  String oldpass = sc.next();
    	        		  bankDAO.changeThePassword(account, oldpass);
    	        	  }
    	        	  else
    	        		  System.out.println("Enter registered account number");  
    	          }
    	          break;
    	          
       case 5: //Mobile-Mobile Transaction
    	      System.out.println("Enter the mobile number of sender linked to Bank Account");
    	      String sendmob = sc.next();
    	      System.out.println("Enter the mobile number of reveiver linked to Bank Account");
    	      String revmob = sc.next();
    	      bankDAO.mobileToMobileTransaction(sendmob,revmob);
    	   break;
    	             
    	          
       case 6:  //check balance
    	   System.out.println("Enter your account number :");
    	   boolean val = true;
    	   while(val) {
    		   String accountno = sc.next();
    		   if(accountno.length()==11) {
    			   System.out.println("Enter your password");
    			   String pass = sc.next();
    			   bankDAO.checkBalance(accountno, pass);
    		   }
    		   else
    			   System.out.println("Enter regisitered account number");
    	   }
    	   break;
    	
    	   
       }
    }
}
