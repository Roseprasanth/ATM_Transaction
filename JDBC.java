import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class JDBC {
	public static int m_balance,balance;
	String Password,m_Password,m_name;
	public static String name;
	public boolean ValidatePass(String Name,String Pass)
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Rosy");
			Statement st=con.createStatement();
			String  query="select name,pass,balance from bank where name=Name";
			ResultSet rs=st.executeQuery(query);
			int balance1 = 0;
			while(rs.next())
			{
				name=rs.getString(1);
				Password=rs.getString(2);
				balance1=rs.getInt(3);
				if(name.contentEquals(Name))
				{
					m_name=name;
					m_Password=Password;
					m_balance=balance1;
				}
			}
			con.close();
			
			if(m_Password.contentEquals(Pass))
			{
				return true;
			}
			else    
			{
				return false;
			}
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		return false;

	}
	public static int m_statement()
	{
		return m_balance;
	}
	public boolean ValidateBalance(int Amount)
	{
		if(Amount<=(m_balance-500))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Name,Pass;
		int op,balance,Amount,i;
		JDBC atm=new JDBC();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Username");
		Name=sc.nextLine();
		System.out.println("Enter Password");
		Pass=sc.nextLine();
		if(atm.ValidatePass(Name,Pass)) {
			System.out.println("You're successfully Logged in");
			for(i=0;i<10;i++)
			{
			System.out.println("Enter your Choices:\n1:Minimal Statement\n2:Withdraw\n3:Credit\n4:Exit\n");
			op=sc.nextInt();
			switch(op)
			{
			case 1:
				balance=JDBC.m_statement();
				System.out.println("You Balnce="+balance);
				break;
			case 2:
				System.out.println("Enter the Amount");
				Amount=sc.nextInt();
				if(atm.ValidateBalance(Amount))
				{
					try {
					JDBC.m_balance-=Amount;
					System.out.println("You're Available Balance"+JDBC.m_balance);
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Rosy");
						Statement stmt =con.createStatement();
						String query = "UPDATE bank set balance = '" + m_balance +  
			                    "' WHERE name= '" +Name+ "'"; 
					      PreparedStatement preparedStmt = con.prepareStatement(query);
						preparedStmt.executeUpdate(query);
						}
						catch(Exception e)
						{
							System.out.println(e);
						}

					break;
				}
				else
				{
					System.out.println("Not sufficient money");
					break;
				}
			case 3:
				System.out.println("Enter the Amount");
				Amount=sc.nextInt();
					try {
					JDBC.m_balance+=Amount;
					System.out.println("You're Available Balance"+JDBC.m_balance);
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Rosy");
						Statement stmt =con.createStatement();
						String query = "UPDATE bank set balance = '" + m_balance +  
			                    "' WHERE name= '" +Name+ "'"; 
					      PreparedStatement preparedStmt = con.prepareStatement(query);
						preparedStmt.executeUpdate(query);
						}
						catch(Exception e)
						{
							System.out.println(e);
						}
					break;
			case 4:
				i=10;
				break;
			default:
				i=10;
				break;
			}
			}
			System.out.println("Timed out");
			}
		else
		{
			System.out.println("Invalid Credetials");
		}
	}	
}
