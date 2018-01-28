package model;


import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import integration.DbHandler;

public class ReturnCar {
	private ResultSet resultSet;
	private DbHandler connection;
	private int numberOfRentalDays;
	private ReturnInformation returnInformation;
	private double totalCost;
	private int mileage;

	public ReturnCar(DbHandler connection) {
		this.connection = connection;

	}

	
	public double startReturnBooking(ReturnInformation returnInformation) throws SQLException {
		this.returnInformation = returnInformation;
		resultSet = connection.startReturnBooking(this.returnInformation.getBookingNumber());

		if (resultSet.next()) {
			calculateRentalTime(resultSet.getString("startDate"));
			
			String regNr = resultSet.getString("regNr");
			calculateRentalCost(numberOfRentalDays, regNr);

			connection.updateBooking(returnInformation, this.totalCost, regNr, this.mileage);

			return this.totalCost;
		}
		return -1;//om något gått fel. 
	}

	// Method to calculate the rentalcost
	private void calculateRentalCost(int numberOfRentalDays, String regNr) throws SQLException {
		resultSet = connection.getReturnCarInfo(regNr);
		resultSet.next();
		double addPerKm = resultSet.getDouble("tillPrisPerKm");
		double addPerDay = resultSet.getDouble("tillaggBasDygnPris");
		this.mileage = (returnInformation.getReturnMileage() - resultSet.getInt("matarstallning"));
		this.totalCost = (this.returnInformation.getPricePerDay() * numberOfRentalDays * addPerDay)
				+ (this.returnInformation.getPricePerKm() * this.mileage * addPerKm);
	}

	public void calculateRentalTime(String startDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
		Date returnTime = new Date();
		Date startDateLocal;
		String returnDate = dateFormat.format(returnTime);
		returnInformation.setReturnDate(returnDate);

		try {
			startDateLocal = dateFormat.parse(startDate);
			long diff = returnTime.getTime() - startDateLocal.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);

			// Minimum one day of renting
			this.numberOfRentalDays = 1;
			while (diffDays > 1) {
				this.numberOfRentalDays++;
				diffDays--;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
