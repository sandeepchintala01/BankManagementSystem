package org.jsp.bank.DAO;

import org.jsp.bank.model.Bank;

public interface BankDAO {
	void userRegistrtion(Bank bank);
	void credit(String accountNumber,String password);
	void debit(String accountNumber,String password);
	void changeThePassword(String account, String oldpass);
	void mobileToMobileTransaction(String sendersMobileNumber,String reveiversMobileNumber);
	void checkBalance(String accountno, String pass);
}
