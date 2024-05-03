package Reservationsystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;
import in.JdbcUtil.Task1JDBCutil;

public class task1 {
	
	  private static final int min = 1000;
	  private static final int max = 9999;
	  
	  public static class PnrRecord{
		  private int pnrNumber;
	      private String passengerName;
	      private String trainNumber;
	      private String classType;
	      private String journeyDate;
	      private String from;
	      private String to;
	      
	      Scanner sc = new Scanner(System.in);
	      
	      public int getPnrNumber() {
	    	  Random random = new Random();
	    	  pnrNumber = random.nextInt(max) +min;
	    	  return pnrNumber;
	      }
	      
	      public String getPassengerName() {
	    	  System.out.println("Enter Passenger Name : ");
	    	  passengerName =  sc.nextLine();
	    	  return passengerName;
	      }
	      
	      public String getTrainNumber() {
	    	  System.out.println("Enter the Train Number : ");
	    	  trainNumber = sc.nextLine();
	    	  return trainNumber;
	      }
	      public String getClassType() {
	    	  System.out.println("Enter the class Which you want : ");
	    	  classType = sc.nextLine();
	    	  return classType;
	    	  
	      }
	      public String getjourneyDate() {
	    	  System.out.println("Enter the JourneyDate : ");
	    	  journeyDate = sc.nextLine();
	    	  return journeyDate;
	      }
	      public String getFrom() {
	    	  System.out.println("Enter the Starting Place : ");
	    	  from = sc.nextLine();
	    	  return from;
	      }
	      public String getTo() {
	    	  System.out.println("Enter the Destination : ");
	    	  to = sc.nextLine();
	    	  return to;
	      }
	  }
	  
	  public static void reserveSeat( Connection cn) throws IOException, SQLException {
		  
		  PnrRecord pr = new PnrRecord();
		  
		  int pnrNumber = pr.getPnrNumber();
		  String passengerName = pr.getPassengerName();
		  String TrainNumber = pr.getTrainNumber();
		  String ClassType = pr.getClassType();
		  String JourneyDate = pr.getjourneyDate();
		  String From = pr.getFrom();
		  String Destination = pr.getTo();
		  
		  if(cn != null) {
			  String InsertQuesry   = "insert into reservations values (?, ?, ?, ?, ?, ?, ?)"; 
			  PreparedStatement pst = cn.prepareStatement(InsertQuesry);
			  
			  pst.setInt(1, pnrNumber);
			  pst.setString(2,passengerName);
			  pst.setString(3,TrainNumber);
			  pst.setString(4, ClassType);
			  pst.setString(5, JourneyDate);
			  pst.setString(6, From);
			  pst.setString(7, Destination);
			  
				int rowEffected = pst.executeUpdate();
				if(rowEffected ==1) {
					System.out.println("Recorder Insert Successfully");
				}else {
					System.out.println("Not inserted  data");
				}
		  }
	  }  
	  
	  
	  
	  public static void cancelreservation(Connection cn , Scanner sc)  {
		  
	  try {
		  System.out.println("Enter the PNR Number  for Ticket cancelletion");
		  int pnrNumber = sc.nextInt();
		
		  
		  String DeleteQuery = "DELETE FROM reservations WHERE pnr_number = ?";
		  
		  PreparedStatement pst = cn.prepareStatement(DeleteQuery);
		  pst.setInt(1, pnrNumber);
		  
		  int affectedRows = pst.executeUpdate();
		  
          if (affectedRows > 0) {
              System.out.println("Reservation deleted successfully!");
          } else {
              System.out.println("Reservation deletion failed Enter valid pnrNumber.");
          }
				  
		  
	    }
	     catch (SQLException e) {
          System.err.println("SQLException: " + e.getMessage());
         }
	  }
	  
	  
	  
	  // choice 3
	  public static void Showreservation(Connection cn) {
		  try {
			  String ShowQuery = "Select * from reservations";
			  PreparedStatement pst = cn.prepareStatement(ShowQuery);
			  ResultSet resultSet = pst.executeQuery();
			  
			  System.out.println("Current Reservations:");
			  
			  System.out.println("+----------------+------------------------+----------------+------------------+-------------------------+--------------------+--------------------------+");
		      System.out.println("| PNR Number     | passengerName          | Train Number   | Class Type       | Journey Date            |  From              | Destination              |");
		      System.out.println("+----------------+------------------------+------------- --+------------------+-------------------------+--------------------+--------------------------+");
			  
			  while(resultSet.next()) {
				   int pnrNumber = resultSet.getInt("pnr_number");
                  String passengerName = resultSet.getString("passenger_name");
                  String trainNumber = resultSet.getString("train_number");
                  String classType = resultSet.getString("class_type");
                  String journeyDate = resultSet.getString("journey_date");
                  String fromLocation = resultSet.getString("to_from");
                  String toLocation = resultSet.getString("to_location");
                  
//               
                  System.out.printf("| %-14d | %-22s | %-14s | %-16s | %-23s |  %-16s  |  %-22s  | \n",pnrNumber,passengerName,trainNumber,classType,journeyDate,fromLocation,toLocation);
                  System.out.println("+----------------+------------------------+------------- --+------------------+-------------------------+--------------------+--------------------------+");
			  }
		  }
		  catch (SQLException e) {
              System.err.println("SQLException: " + e.getMessage());
          }
	  }
	  
	  public static void exit() throws InterruptedException {
		  System.out.print("Exiting System");
	        int i = 5;
	        while(i!=0){
	            System.out.print(".");
	            Thread.sleep(1000);
	            i--;
	        }
	        System.out.println();
	        System.out.println("ThankYou For Using Online Reservation System!!!");
	  }
	  
	  


	public static void main(String[] args) throws IOException, InterruptedException {
		Connection cn = null;
		//Statement st = null;
		ResultSet result = null;
		Scanner sc = new Scanner(System.in);
		try {
			cn = Task1JDBCutil.getJdbcConnection();
			if(cn!=null) {
				while(true) {
					System.out.println();

                    System.out.println("Enter the choice : ");
                    System.out.println("1. Insert Record.\n");
                    System.out.println("2. Delete Record.\n");
                    System.out.println("3. Show All Records.\n");
                    System.out.println("0. Exit.\n");
					System.out.println("Choose an Option");
					int choice = sc.nextInt();
					switch(choice) {
					case 1:
					reserveSeat(cn);
					break;
					case 2:
				   cancelreservation(cn ,sc);
					break;
					case 3:
					Showreservation(cn);
					break;
					case 0:
					exit();
					
					default :
						System.out.println("Invalid choice, try again");
					}
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
