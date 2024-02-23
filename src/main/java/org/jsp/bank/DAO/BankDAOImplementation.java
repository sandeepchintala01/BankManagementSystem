package org.jsp.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import org.jsp.bank.model.Bank;

import com.mysql.cj.xdevapi.Result;

public class BankDAOImplementation implements BankDAO {
	Scanner sc = new Scanner(System.in);
	String url = "jdbc:mysql://localhost:3306/teca_52?user=root&password=12345";

@Override 
	public void userRegistrtion(Bank bank) {
	String insert =
"insert into bank(first_name, last_name, mobile_number, email_id, password, address, amount,accountNumber)values(?,?,?,?,?,?,?,?)";
	try {
	Connection connection = DriverManager.getConnection(url);
	PreparedStatement ps  = connection.prepareStatement(insert);
	ps.setString(1,bank.getFirstName());  
	ps.setString(2,bank.getLastName());
	ps.setString(3,bank.getMobileNumber());
	String tempname = bank.getFirstName().toLowerCase();
	Random rd = new Random();
	int tempnum = rd.nextInt(1000);
	String bankemailid = tempname + tempnum+"@teca52.com";
	ps.setString(4,bankemailid);
	ps.setString(5,bank.getPassword());
	ps.setString(6,bank.getAddress());
	ps.setDouble(7,bank.getAmount());
	long ac = rd.nextLong(100000000000L);
	if(ac<10000000000L) {
		ac += 10000000000L;
	}
	ps.setString(8,""+ac);
	int result = ps.executeUpdate();
	if(result==1) {
		System.out.println("Account Creation Successful");
		try {
			Thread.sleep(2000);
			System.out.println(bankemailid);
			System.out.println(ac);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		else
		System.out.println("Invalid Credentials");
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
	}

@Override
	public void credit(String accountNumber,String password1) {
		try {
			 Connection connection= DriverManager.getConnection(url);
			 PreparedStatement ps1 = connection.prepareStatement("select * from bank where accountNumber=? and password=?");
			    ps1.setString(1, accountNumber);
				ps1.setString(2, password1);
				ResultSet rs = ps1.executeQuery();
				if(rs.next()) {
					double amount = rs.getDouble(8);
					System.out.println("Enter amount to deposit");
					double creditamount = sc.nextDouble();
					boolean validation = true;
					while(validation) {
					if(creditamount>0) {
						validation=false;
						double total = creditamount+amount;
						System.out.println("Your updated balance amount is:"+total);
						String update = "update bank set amount=? where accountNumber=? and password=?";
                    	PreparedStatement pst = connection.prepareStatement(update);
                    	pst.setDouble(1, total);
                    	pst.setString(2, accountNumber);
                    	pst.setString(3, password1);
                    	int result = pst.executeUpdate();
					}
					else
						System.out.println("Amount to be deposit must e greater than zero rupee");
				  }
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
  
	
@Override
	public void debit(String accountNumber,String password) { 
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement("select * from bank where accountNumber=? and password=?");
			ps.setString(1, accountNumber);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				double amount = rs.getDouble(8);
				System.out.println("Amount in your bank account is :"+amount);
			//	System.out.println("Executed");
				boolean amountvalidation = true;
				while(amountvalidation) {
					System.out.println("Enter Amount to debit");
					double debitamount = sc.nextDouble();
					if(debitamount>0) {
						//System.out.println("Entered");
						//amountvalidation=false;
                        if(debitamount<=amount) {
                        	amountvalidation=false;
                        	System.out.println("debited Amount is :"+debitamount);
                        	System.out.println("Amount Remaning is:"+(amount-debitamount));
                        	String update = "update bank set amount=? where accountNumber=? and password=?";
                        	PreparedStatement pst = connection.prepareStatement(update);
                        	pst.setDouble(1, amount-debitamount);
                        	pst.setString(2, accountNumber);
                        	pst.setString(3, password);
                        	int result = pst.executeUpdate();
;                        }
                        else {
                        	System.out.println("Insufficient funds");
                        //amountvalidation=true;
					}
				}
					else {
						System.out.println("Amount to be debited must be more than zero");
						//amountvalidation=true;
					}
				}
			}
				else
				System.out.println("Invalid");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void changeThePassword(String account, String oldpass) {
	            try {
					 Connection connection = DriverManager.getConnection(url);
					 PreparedStatement ps = connection.prepareStatement("select password from bank where accountNumber=?");
				     ps.setString(1, account);
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
					String pass = rs.getString(1);
					boolean valid = true;
					while(valid) {
					 if(oldpass.equals(pass)) {
	        			System.out.println("Enter new password");
	        			String newpass= sc.next();
	        			System.out.println("Confirm the entered password");
	        			String confpass = sc.next();
	        			if(newpass.equals(confpass)) {
	        				System.out.println("Passowrd Updated Successfully");
	        				valid = false;
	        				String update = "update bank set password=? where accountNumber=?";
	        				PreparedStatement pst = connection.prepareStatement(update);
                        	pst.setString(1, newpass);
                        	pst.setString(2, account);
                        	int result = pst.executeUpdate();
	        			}
	        			else
	        				System.out.println("Entered password is mismatched");
	        		 }
					 else
						 System.out.println("Re-Enter the password correctly");
					}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		
	}

	@Override
	public void mobileToMobileTransaction(String sendersMobileNumber,String reveiversMobileNumber) {
		
		  try {
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps1 = connection.prepareStatement("select * from bank");
		ResultSet rs =ps1.executeQuery();
	    int count = 0;
	    boolean mobilevalidation = true;
		while(rs.next()) {
			if(rs.getString(4).equals(sendersMobileNumber))
				count++;
			if(rs.getString(4).equals(reveiversMobileNumber))
				count++;
		}
		if(count==2) {
			PreparedStatement ps2 = connection.prepareStatement("select * from bank where mobile_number=?");
			ps2.setString(1, sendersMobileNumber);
			ResultSet rst1 =ps2.executeQuery();
			if(rst1.next()) {
			double amount1 = rst1.getDouble(8);
//			amo = amount1;
			}
			System.out.println("present");
			System.out.println("Enter the Amount to transfer");
			double transfer = sc.nextDouble();
			while(mobilevalidation) {
			if(transfer>0) {
				double amo = 0;
				
				
				PreparedStatement ps5 = connection.prepareStatement("update bank set amount=? where mobile_number=?");
				ps5.setDouble(1, amo-transfer);
				ps5.setString(2, sendersMobileNumber);
				int  rst4 = ps5.executeUpdate();
				if(rst4==1)
					System.out.println("Updated");
				
				PreparedStatement ps3 = connection.prepareStatement("select * from bank where mobile_number=?");
				ps3.setString(1, reveiversMobileNumber);
				ResultSet rst2 =ps3.executeQuery();
				if(rst2.next()) {
					double amount2 = rst2.getDouble(8);
					amo = amount2;
					System.out.println("Receiver's name :"+rst2.getString(2)+""+rst2.getString(3));
					System.out.println("Receiver's Account Number is :"+rst2.getString(9));
				}
				
				PreparedStatement ps4 = connection.prepareStatement("update bank set amount=? where mobile_number=?");
				ps4.setDouble(1, transfer+amo);
				ps4.setString(2, reveiversMobileNumber);
				int  rst3 = ps4.executeUpdate();
				if(rst3==1)
					System.out.println("Updated");
				
				
				System.out.println("✔✔Transfer Successful✔✔"); 
				mobilevalidation = false;
			}
			else
				System.out.println("Enter valid amount");
		}
		}
		else {
			System.out.println("Not present");
	   }
	} 
		  catch (SQLException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public void checkBalance(String accountno, String pass) {
		try {
		Connection connection = DriverManager.getConnection(url);
		PreparedStatement ps = connection.prepareStatement("select * from bank where accountNumber=? and password=?");
		ps.setString(1, accountno);
		ps.setString(2, pass);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
		double balance  = rs.getDouble(8);
		System.out.println("Account Balance :"+balance);
		}
		else
			System.out.println("Invalid Details");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
}
