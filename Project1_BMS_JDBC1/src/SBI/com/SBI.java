package SBI.com;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import Exceptions.com.InvalidAccountNumberException;
import Exceptions.com.InvalidAdharNumberException;
import Exceptions.com.InvalidContactException;
import Exceptions.com.NotEligibleForAccountCreationException;
import Module.com.Account;
import RBI.com.RBI;


public class SBI implements RBI
{
	
	double amount;
	Account i;
    Scanner sc=new Scanner(System.in);
    
	@Override
	public void createAccount() throws InvalidContactException, InvalidAdharNumberException, NotEligibleForAccountCreationException, InvalidAccountNumberException {
	
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");

			i=new Account();

			System.out.println("Account Creation Process Start Here");
			System.out.println("Enter Initial Balance In An Account");
		    i.setBalance(0);

			
			System.out.println("Enter Name Of Account Holder");
			
			i.setName(sc.next()+sc.nextLine());

			System.out.println("Enter The Account Number Of Account Holder");
			
			
			int accNo=sc.nextInt();
			int y=accNo;
			int countDigit=0;
			while(y!=0) {
				y=y/10;
				countDigit++;
			}
			if(countDigit==5) {
				i.setAccNo(accNo);
			}
			else
			{
				throw new InvalidAccountNumberException("Account Number Is an invalid");
			}

			
			System.out.println("Enter Account Holder Mobile Number");
			long mobNo=sc.nextLong();
			long k=mobNo;
			long count =0;
		    while(k!=0) {
		    	k=k/10;
				count++;
			}
			if(count==10) {
			i.setMobNo(mobNo);
			}
			else
			{
				throw new InvalidContactException("Invalid Contact Number");
			}
			
			
			System.out.println("Enter Account Holder's Age Here");
			int age=sc.nextInt();
			if(age>18 && age<60) {
				i.setAge(age);
			}
			else
			{
				throw new NotEligibleForAccountCreationException("User Not Eligible For Creating an account");
			}

			System.out.println("Enter Account Holder Address Here");
			i.setAddress(sc.next());
			i.setAddress(sc.nextLine());
			
			
			System.out.println("Enter Date Of Birth of Account Holder ");
		    i.setDob(sc.next());
		    
		    
		    System.out.println("Enter Account Holder Adhar Number ");
		    long  adharNo=sc.nextLong();
		    long temp=adharNo;
		    long count1=0;
		    while(temp!=0) {
		    	temp=temp/10;
		    	count1++;
		    }
		if(count1==12) {
			i.setAdharNo(adharNo);
			}
		else
			
		{
			throw new InvalidAdharNumberException("Invalid Adhar Number");
		}
		
		    System.out.println("Upload The Photo of Account Holder");
		    System.out.println("Account Created Successfully...!");
			//String sql="create table Account( accNo int, name varchar(20), mobNo long, adharNo long, address varchar(40), balance double, age int primary key, pincode int, dob varchar(99) )";

			//PreparedStatement ps=con.prepareStatement(sql);
			
			//ps.execute();
			
	     	//System.out.println("Table Created Successfully...!");
	     	
	     	System.out.println("Insert The Values In Table Properly");
	     	
		    String insert="insert into account values(?,?,?,?,?,?,?,?,?)";
		     
		    
		 PreparedStatement ps1=con.prepareStatement(insert); 
		 
		 
		 ps1.setInt(1, i.getAccNo());
		 ps1.setString(2, i.getName());
		 ps1.setLong(3,i.getMobNo());
		 ps1.setLong(4, i.getAdharNo());
		 ps1.setString(5, i.getAddress());
		 ps1.setDouble(6, i.getBalance());
	     ps1.setInt(7, i.getAge());
	     ps1.setInt(8, i.getPincode());
	     ps1.setString(9, i.getDob());
		 ps1.execute();
		 System.out.println( "Data Inserted Successfully...!!!");
		} 
		catch (Exception e) {
			System.out.println("Invalid name"+e.getMessage());
		}
		}
		
		
	@Override
	public void showdetails() throws Exception {	
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
		String sk="select*from account";
		PreparedStatement ps = c.prepareStatement(sk);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getLong(3));
			System.out.println(rs.getLong(4));
			System.out.println(rs.getString(5));
			System.out.println(rs.getDouble(6));
			System.out.println(rs.getInt(7));
			System.out.println(rs.getInt(8));
			System.out.println(rs.getString(9));
		}
		}
	
	
	
	@Override
	public void checkBalance() throws ClassNotFoundException, SQLException {
		
		
		System.out.println("Enter account number to assign");
		int ac=sc.nextInt();
		Class.forName("com.mysql.cj.jdbc.Driver");	

		Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");

		 String jk="select*from account where accNo=?";
		 PreparedStatement st=co.prepareStatement(jk);
		 st.setInt(1, ac);
		 ResultSet rs=st.executeQuery();
	     if(rs.next()) {
	     double bal=rs.getDouble("balance");
	     System.out.println("Balance:" +bal);
	    	 
	}

 }
	
	
	
	@Override
	public void withdrawAmount() throws ClassNotFoundException, SQLException{
		
	System.out.println("Enter account number for withdrawl process");
	int ac=sc.nextInt();
	Class.forName("com.mysql.cj.jdbc.Driver");	
	Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
	String jk="select*from account where accNo=?";
	PreparedStatement st=co.prepareStatement(jk);
	st.setInt(1, ac);
	ResultSet rs=st.executeQuery();
	if(rs.next()) {
		System.out.println("Enter your amount for withdraw");
		amount=sc.nextDouble();
		double balance=rs.getDouble("balance");
		if(balance>=amount) {
		String ghi="Update Account Set Balance=Balance-?  where accNo=?";
		PreparedStatement psw=co.prepareStatement(ghi);
		psw.setDouble(1, amount);
		psw.setInt(2, ac);
		psw.executeUpdate();
		System.out.println("Debit Balance:" +amount+ "Available Balance" +(balance-amount));
	}	
	else
	{
		System.out.println("No Sufficient Money In Account");
	}
	}
	else
	{
		System.out.println("Account Not Found");
	}
	
	}

	@Override
	public void depositAmount() throws ClassNotFoundException, SQLException {
		System.out.println("Enter acc no for deposit process");
		int ac=sc.nextInt();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection co=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","root");
		String jk="select*from account where accNo=?";
		PreparedStatement st=co.prepareStatement(jk);
		st.setInt(1, ac);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			System.out.println("Enter Your Amount For Deposit");
			amount=sc.nextDouble();
          String sql="Update Account Set Balance=Balance+?  where accNo=?";
          PreparedStatement s=co.prepareStatement(sql);
          s.setDouble(1, amount);
          s.setInt(2, ac);
          s.executeUpdate();
		
		//Retrieve The Updated Balance
          rs=st.executeQuery();
          if(rs.next()) {
        	  double updatedBalance=rs.getDouble("Balance");
          
		System.out.println("Deposited Successfully...! New Balance:" + updatedBalance);

}
          else
          {
        	  System.out.println("No Suffient Money In Account");
          }
	}
}
}



