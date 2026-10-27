package banking;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
public class bankManagement {
 private static final int NULL=0;
 static Connection con=DBconnection.getConnection();
  public static boolean createaccount(String name,int passcode) {
	  if(name.isEmpty()||passcode==NULL) {
		  System.out.println("All fields are required");
		  return false;
	  }
	  try {
		  String sql="INSERT INTO customer(cname,balance,passcode)VALUES(?,1000,?)";
		  PreparedStatement ps=con.prepareStatement(sql);
		  ps.setString(1, name);
		  ps.setInt(2,passcode);
		  
		  int rows=ps.executeUpdate();
		  if(rows==1) {
			  System.out.println("Account created successfully.You can now login!");
			  return true;
		  }
	  }catch(SQLIntegrityConstraintViolationException e) {
		  System.out.println("Username already exists!Try another one");
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	  return false;
  }
  public static boolean loginaccount(String name,int passcode) {
	  if(name.isEmpty()||passcode==NULL) {
		  System.out.println("All fieldsare required");
		  return false;
	  }
	  try {
		  String sql="SELECT * FROM customer WHERE cname=? AND passcode=?";
		  PreparedStatement ps=con.prepareStatement(sql);
		  ps.setString(1, name);
		  ps.setInt(2, passcode);
		  ResultSet rs=ps.executeQuery();
		  BufferedReader sc=new BufferedReader(new InputStreamReader(System.in));
		  if(rs.next()) {
			  int senderAc=rs.getInt("ac_no");
			  int ch;
			  while(true) {
				  System.out.println("\n Hello, "+rs.getString("cname")+"! What would you like to do?");
				  System.out.println("1) Transfer Money");
				  System.out.println("2) View Balance");
				  System.out.println("3) Logout");
				  System.out.print("Enter your choice: ");
				  ch=Integer.parseInt(sc.readLine());
				  
				  if(ch==1) {
					  System.out.print("Enter Reciever A/c no: ");
					  int receiverAc=Integer.parseInt(sc.readLine());
					  System.out.println("Enter Amount: ");
					  int amt=Integer.parseInt(sc.readLine());
					  
					  if(transfermoney(senderAc,receiverAc,amt)) {
						  System.out.println("Transaction Successfull!!!");
					  }else {
						  System.out.println("Transaction failed! Please try again.");
					  }
				  }
				  else if(ch==2) {
					  getbalance(senderAc);
				  }
				  else if(ch==3) {
					  System.out.println("Logged out successully.Returning to Main Menu...");
					  break;
				  }
				  else {
					  System.out.print("Invalid choice! Try again.");
				  }
			  }	
			  return true;
			  
		  }
		  else {
			  System.out.println("Invalid username or password!");
			  return false;
		  }
		  
	  } catch(Exception e) {
		  e.printStackTrace();
	  }
	  return false;
  }
  public static void getbalance(int acNo) {
	  try {
		  String sql="SELECT * FROM customer WHERE ac_no=?";
		  PreparedStatement ps=con.prepareStatement(sql);
		  ps.setInt(1,acNo);
		  ResultSet rs=ps.executeQuery();
		  System.out.println("\n-------------------------------------------------");
		  System.out.printf("%12s %15s %10s\n", "Account No","Customer Name","Balance");
		  while(rs.next()) {
			  System.out.printf("%12d %15s %10d.00\n",rs.getInt("ac_no"),rs.getString("cname"),rs.getInt("balance"));
		  }
		  System.out.println("-------------------------------------------------");
      }catch(Exception e) {
		  e.printStackTrace();
	  }	
}
  public static boolean transfermoney(int senderAc, int receiverAc, int amt) {
	 if(receiverAc==NULL || amt==NULL) {
		 System.out.println("All fields are required!");
		 return false;
		 }
	 try {
		 con.setAutoCommit(false);
		 String checkbalance="SELECT balance FROM customer WHERE ac_no=?";
		 PreparedStatement ps=con.prepareStatement(checkbalance);
		 ps.setInt(1,senderAc);
		 ResultSet rs=ps.executeQuery();
		 
		 if(rs.next() && rs.getInt("balance")<amt) {
			 System.out.println("Insufficient balance!");
			 return false;
		 }
		 String debit="UPDATE customer SET balance=balance- ? WHERE ac_no=?";
		 PreparedStatement psdebit=con.prepareStatement(debit);
		 psdebit.setInt(1, amt);
		 psdebit.setInt(2, senderAc);
		 psdebit.executeUpdate();
		 
		 String credit="UPDATE customer SET balance=balance+ ? WHERE ac_no=?";
		 PreparedStatement pscredit=con.prepareStatement(credit);
		 pscredit.setInt(1, amt);
		 pscredit.setInt(2, receiverAc);
		 pscredit.executeUpdate();
		 
		 con.commit();
		 return true;
		}catch(Exception e) {
		   try {
			   con.rollback();
		   }catch(SQLException ex) {
			   ex.printStackTrace();
		   }
		   e.printStackTrace();
		}
	 return false;
   }
}
 
