package HospitalManagementSystem1;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
private static final String url="jdbc:mysql://localhost:3306/hospital";
private static final String username="root";
private static final String password="root";
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("\n\nHospital Management System");
				System.out.println("1. Add Patient ");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctor");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice !!");
				int choice=scanner.nextInt();
				switch(choice) {
				case 1: patient.addPatient();
						System.out.println();
						break;
				case 2: patient.viewPatient();
						System.out.println();
						break;
				case 3: doctor.viewDoctor();
						System.out.println();
						break;
				case 4: bookappointment(patient, doctor, scanner, connection);
						System.out.println();
						break;
				case 5: System.out.println("Thankyou fro using hospital management system");
						break;
				default: System.out.println("Enter a Valid Choice");
				
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void bookappointment(Patient patient,Doctor doctor,Scanner scanner,Connection connection) {
		System.out.println("Enter Patient ID ");
		int patient_id=scanner.nextInt();
		System.out.println("Enter Doctor Id");
		int doctor_id=scanner.nextInt();
		System.out.println("Enter appointment date (yyyy-mm-dd)");
	    String appointment_date=scanner.next();
	    if(patient.getPatientById(patient_id)&&doctor.getDoctorById(doctor_id)) {
	    	if(checkDoctorAvailability(doctor_id,appointment_date,connection)) {
	    		String appointmentquerry="insert into appointment(patient_id,doctor_id,appointment_date) value(?,?,?)";
	    		try {
	    			PreparedStatement prepareStatement=connection.prepareStatement(appointmentquerry);
	    			prepareStatement.setInt(1, patient_id);
	    			prepareStatement.setInt(2, doctor_id);
	    			prepareStatement.setString(3, appointment_date);
	    			int rowsAffected =prepareStatement.executeUpdate();
	    			if(rowsAffected>0) {
	    				System.out.println("AppointmentBook");
	    			}else {
	    				System.out.println("Failed To book");
	    			}
	    		}
	    		catch (SQLException e) {
					e.printStackTrace();
				}
	    	}else {
	    		System.out.println("Doctor Not available on this date");
	    	}
	    }else {
	    	System.out.println("Either doctor or patient dosen't exist!!");
	    }
	}
	public static boolean checkDoctorAvailability(int doctor_id, String appointment_date,Connection connection) {
		String query="select count(*) from appointment where doctor_id=? and appointment_date=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, appointment_date);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
	}

}
