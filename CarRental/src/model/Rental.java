package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import integration.DbHandler;

public class Rental {
	DbHandler connection;

	public Rental(DbHandler connection) {
		this.connection = connection;
	}

	public void startRent() {
		try {
			ResultSet resultSet = connection.getFreeCars();
			while (resultSet.next()) {
				System.out.println(resultSet.getString("regnr") + " " + resultSet.getString("modell"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void finishBookingInfo(Booking newBooking) throws SQLException {
		connection.setBooking(newBooking);
	}

}
