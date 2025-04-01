package HospitalManagementSystem1;

import java.sql.*;


public class Doctor {
		private Connection connection;
		public Doctor(Connection connection) {
			this.connection=connection;
		}
		public void viewDoctor() {
			String query="select * from doctor";
			try {
				PreparedStatement prepareStatement=connection.prepareStatement(query);
				ResultSet resultSet=prepareStatement.executeQuery();
				System.out.println("Doctors");
				while (resultSet.next()) {
					int id=resultSet.getInt("id");
					String name=resultSet.getString("name");
					String specialization=resultSet.getString("specialization");
					System.out.printf("%-10s %-16s %-20s",id,name,specialization);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		public boolean getDoctorById(int id) {
			String query="select * from doctor where id= ?";
			try {
			PreparedStatement prepareStatement=connection.prepareStatement(query);
			prepareStatement.setInt(1, id);
			ResultSet resultSet=prepareStatement.executeQuery();
			if(resultSet.next()) {
				return true ;
			}else {
				return false;
			}
			}
			catch (SQLException e) {
			e.printStackTrace();
			}return false;
		}
}
