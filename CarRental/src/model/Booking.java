package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Booking {
	private int bookingNumber;
	private String regNr;
	private String personalNumber;
	private String startDate;
	private DateFormat dateFormat;
	/*
	 * private int antalKm; private int antalDygn; private double prisTillagg;
	 * private double basKmPris; private double basDygnsHyra; private double
	 * slutPris; private String personnummer; private int matarStallning;
	 */

	public Booking(String regNr, String personalNumber) {
		Random rn = new Random();
		this.bookingNumber = (1000 + rn.nextInt(9999));
		this.regNr = regNr;
		this.personalNumber = personalNumber;
		this.dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
		Date startDate = new Date();
		this.startDate = dateFormat.format(startDate);

	}

	public int getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public String getRegNr() {
		return regNr;
	}

	public void setRegNr(String regNr) {
		this.regNr = regNr;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getStartdate() {

		return this.startDate;
	}

}
