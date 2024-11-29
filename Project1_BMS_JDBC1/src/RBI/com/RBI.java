package RBI.com;

import java.sql.SQLException;

import Exceptions.com.InvalidAccountNumberException;
import Exceptions.com.InvalidAdharNumberException;
import Exceptions.com.InvalidContactException;
import Exceptions.com.NotEligibleForAccountCreationException;

public interface RBI {
	
public void createAccount() throws InvalidContactException, InvalidAdharNumberException, NotEligibleForAccountCreationException, InvalidAccountNumberException, Exception;
public void checkBalance() throws ClassNotFoundException, SQLException;
public void showdetails() throws Exception;
public void withdrawAmount() throws ClassNotFoundException, SQLException;
public void depositAmount() throws ClassNotFoundException, SQLException;



}

