package HospitalManagementSystem1;

import java.sql.*;
import java.util.*;
	public class Patient 
	{
				private Connection connection;
				private Scanner scanner;

				public Patient(Connection connection, Scanner scanner) 
				{
					this.connection=connection;
					this.scanner=scanner;
				}
				
				public void addPatient()
				{
					System.out.println("Enter Patient Name: ");
					String name=scanner.next();
					System.out.println("Enter Patient Age: ");
					int age=scanner.nextInt();
				    System.out.println("Enter Patient Gender: ");
				    String gender=scanner.next();
				    try {
				    	String query="insert into patient(name,age,gender) value(?,?,?)";
				    	PreparedStatement preparestatement=connection.prepareStatement(query);
				    	preparestatement.setString(1, name);
				    	preparestatement.setInt(2, age);
				    	preparestatement.setString(3, gender);
				    	int affectedRows=preparestatement.executeUpdate();
				    	if(affectedRows>0) {
				    		System.out.println("Patient Added Succesfully");
				    	}
				    	else {
				    		System.out.println("Failed to add !!!");
				    	}
				    }catch(SQLException e) {
				    	e.printStackTrace();
				    }
	
				}
				public void viewPatient() {
					String query="select * from patient";
				    try {
				    	PreparedStatement prepareStatement=connection.prepareStatement(query);
				    	ResultSet resultSet=prepareStatement.executeQuery();
				    	System.out.println("Ptients:------");
				    	while(resultSet.next()) {
				    		
				    			int id=resultSet.getInt("id");
				    			String name=resultSet.getString("name");
				    			int age=resultSet.getInt("age");
				    			String gender=resultSet.getString("gender");
				    			System.out.printf("%-10s %-18s %-8s %-10s",id,name,age,gender);
				    		}
				    	}
				    	catch(SQLException e) {
				    		e.printStackTrace();
				    	}
				    }
				public boolean getPatientById(int id) {
					String query="select * from Patient where id=?";
					try {
						PreparedStatement prepareStatement=connection.prepareStatement(query);
						prepareStatement.setInt(1, id);
						ResultSet resultSet=prepareStatement.executeQuery();
						if(resultSet.next()) {
							return true;
						}else {
							return false;
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
					return false;
				}
		}
				

