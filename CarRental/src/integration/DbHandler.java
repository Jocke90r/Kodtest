package integration;

import java.sql.*;

import model.Booking;
import model.ReturnInformation;

public class DbHandler {
	Connection connection;
	Statement myStat;
	PreparedStatement getFreeCars;
	PreparedStatement getCarInformationForBooking;
	PreparedStatement setBookingInformation;
	PreparedStatement setCarToRented;
	PreparedStatement getBooking;
	PreparedStatement getReturnCarInfo;
	PreparedStatement setUpdateBooking;
	PreparedStatement setCarUnrented;
	ResultSet resultSet;

	// Establish dataBase connection
	public DbHandler() {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost/biluthyrning", "root", "");
			this.myStat = connection.createStatement();
			prepareStatements(this.connection);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Create all preparedStatements.
	private void prepareStatements(Connection connection) throws SQLException {
		getFreeCars = connection.prepareStatement("SELECT regnr, modell FROM bilinformation WHERE uthyrd = 0");
		getCarInformationForBooking = connection.prepareStatement("SELECT * FROM bilinformation WHERE regnr = ?");
		setBookingInformation = connection.prepareStatement(" INSERT INTO booking" + "(bookingNumber, startDate, regNr,modell, personalNumber, "
						+ "meterIndication, bookingFinished) VALUES (?,?,?,?,?,?,?)");
		setCarToRented = connection.prepareStatement("UPDATE bilinformation set uthyrd = 1 where regNr = ?");
		getBooking = connection.prepareStatement("SELECT * FROM booking WHERE bookingNumber = ?");
		getReturnCarInfo = connection.prepareStatement(
				"SELECT tillaggBasDygnPris, tillPrisPerKm, matarstallning FROM bilinformation WHERE regnr = ?");
		setUpdateBooking = connection.prepareStatement(
				"UPDATE booking SET bookingFinished = ?, returnDate = ?, cost = ?, korLangd = ? WHERE bookingNumber = ?");
		setCarUnrented = connection.prepareStatement(
				"UPDATE bilinformation SET uthyrd = 0, matarstallning = ? WHERE regnr = ?");
	}

	public void updateBooking(ReturnInformation returnInformation, double totalCost, String regNr, int mileage) throws SQLException {

		
			setUpdateBooking.setInt(1, 1);
			setUpdateBooking.setString(2, returnInformation.getReturnDate());
			setUpdateBooking.setDouble(3, totalCost);
			setUpdateBooking.setInt(4, mileage);
			setUpdateBooking.setInt(5, returnInformation.getBookingNumber());
			setUpdateBooking.executeUpdate();
			setCarUnrented.setInt(1, returnInformation.getReturnMileage());
			setCarUnrented.setString(2, regNr);
			setCarUnrented.executeUpdate();
		

		
	}

	// Get all free cars
	public ResultSet getFreeCars() throws SQLException {
		resultSet = getFreeCars.executeQuery();
		return resultSet;

	}

	// Sets everything for a booking
	public void setBooking(Booking newBooking) throws SQLException {
		getCarInformationForBooking.setString(1, newBooking.getRegNr());
		resultSet = getCarInformationForBooking.executeQuery();

		setBookingInformation.setInt(1, newBooking.getBookingNumber());
		setBookingInformation.setString(2, newBooking.getStartdate());
		setBookingInformation.setString(3, newBooking.getRegNr());
		resultSet.first();
		setBookingInformation.setString(4, resultSet.getString(3));
		setBookingInformation.setString(5, newBooking.getPersonalNumber());
		setBookingInformation.setInt(6, resultSet.getInt(2));
		setBookingInformation.setInt(7, 0); 
		setBookingInformation.executeUpdate();

		setCarToRented.setString(1, newBooking.getRegNr());
		setCarToRented.executeUpdate();

	}

	public ResultSet startReturnBooking(int bookingNumber) throws SQLException {
		getBooking.setInt(1, bookingNumber);
		resultSet = getBooking.executeQuery();
		return resultSet;
	}

	public ResultSet getReturnCarInfo(String regNr) throws SQLException {
		getReturnCarInfo.setString(1, regNr);
		resultSet = getReturnCarInfo.executeQuery();
		return resultSet;
	}

}
