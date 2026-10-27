package banking;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class bank {
  public static void main(String args[]){
	  BufferedReader sc=new BufferedReader(new InputStreamReader(System.in));
	  String name;
	  int pass_code;
	  int ch;
	  while(true) {
		  System.out.println("\n===============================");
		  System.out.println("Welcome to Siva Bank!!!");
		  System.out.println("===============================");
		  System.out.println("1) Create Account");
		  System.out.println("2)Login Account");
		  System.out.println("3)Exit");
		  
		  try {
			  System.out.print("\nEnter your choice..");
			  ch=Integer.parseInt(sc.readLine());
			  
			  switch(ch) {
			  case 1->{
				  System.out.println("Enter unique Username!");
				  name=sc.readLine();
				  System.out.println("Enter Password: ");
				  pass_code=Integer.parseInt(sc.readLine());
				  
				  if(bankManagement.createaccount(name, pass_code)) {
					  System.out.println("You can now Login from Main Menu!");
				  }
			  }
			  case 2->{
				  System.out.println("Enter your Username: ");
				  name=sc.readLine();
				  System.out.print("Enter your password: ");
				  pass_code=Integer.parseInt(sc.readLine());
				  
				  if(!bankManagement.loginaccount(name, pass_code)) {
					  System.out.println("Login Failed. Try Again");
				  }
				  
			  }
			  case 3->{
				  System.out.println("You now successfully Logged out.Thank You for using Siva Bank");
				  System.exit(0);
			  }
			  default->System.out.println("Invalid Input! Please Try Again...");
			  }
		  }catch(Exception e) {
			  System.out.println("Please enter a valid input");
		  }
		  
	  }
  }
}
